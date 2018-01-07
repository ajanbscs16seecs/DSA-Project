/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

import Data.Page;
import Data.Word;
import DataIndexed.Index;
import DataIndexed.LocationAndType;
import DataIndexed.PageAndFrequency;
import DataIndexed.XPage;
import DataIndexed.XWord;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.dizitart.no2.FindOptions;
import org.dizitart.no2.filters.Filters;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;
import parsers.WikiMarkupParser;
import parsers.WikiXMLParser;
import parsers.WikiXMLParser.WikiXMLParserCallbackReciever;

/**
 *
 * @author Arif
 */
public class Indexer implements WikiXMLParserCallbackReciever {

    IndexingCallbacks callback;
    ObjectRepository pageMapRepository;
    ObjectRepository invertedIndexRepository;
    HashMap<String, List<PageAndFrequency>> invertedIndexInMemory = new HashMap<String, List<PageAndFrequency>>();

    public Indexer(ObjectRepository pageMapRepository, ObjectRepository invertedIndexRepository, IndexingCallbacks callback) {
        this.callback = callback;
        this.pageMapRepository = pageMapRepository;
        this.invertedIndexRepository = invertedIndexRepository;
    }

    public void init() {

    }

    public void next(XPage xpage) {
        //System.out.println(xpage.toString());
        this.pageMapRepository.insert(xpage);
        this.addToInvertedIndex(xpage);
        this.callback.onProgress((int) xpage.getPageId());
    }

//    public void addToInvertedIndex(XPage xpage) {
//        //System.out.println(xpage.toString());
//        for (String word : xpage.getWords().keySet()) {//for all words the page add this page to the list present for this word in invertedindex
//            
//            XWord tempXWord = getXWord(word);
//            if (tempXWord != null) {
//                //update the value....
//                tempXWord.appendNextPage(xpage.getPageId(),xpage.getWordInstances().get(word).size());
//                //System.out.println("updating : "+ tempXWord.toString());
//                this.invertedIndexRepository.update(tempXWord);
//            } else {
//                List<PageAndFrequency> pages = new ArrayList<PageAndFrequency>();
//                
//                
//                pages.add(new PageAndFrequency(xpage.getPageId(),xpage.getWordInstances().get(word).size()));
//                tempXWord = new XWord(word, pages);
//                //System.out.println("inserting : "+ tempXWord.toString());
//
//                this.invertedIndexRepository.insert(tempXWord);
//            }
//        }
//    }
    public void addToInvertedIndex(XPage xpage) {
        //System.out.println(xpage.toString());
        for (String word : xpage.getWords().keySet()) {//for all words the page add this page to the list present for this word in invertedindex

            if (invertedIndexInMemory.containsKey(word)) {
                invertedIndexInMemory.get(word).add(new PageAndFrequency(xpage.getPageId(), xpage.getWordInstances().get(word).size()));
            } else {
                List<PageAndFrequency> temp = new ArrayList<PageAndFrequency>();
                temp.add(new PageAndFrequency(xpage.getPageId(), xpage.getWordInstances().get(word).size()));
                invertedIndexInMemory.put(word, temp);
            }
        }
    }

    public void generateInvertedIndex() {
        Cursor<XPage> cursor = this.pageMapRepository.find();
        for (XPage xpage : cursor) {

            this.addToInvertedIndex(xpage);
            this.callback.onProgress((int) xpage.getPageId());
        }
    }

    public HashMap<String, List<LocationAndType>> generateHashMapOfWords(List<Word> words) {

        HashMap<String, List<LocationAndType>> wordInstances = new HashMap<String, List<LocationAndType>>();

        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getWord().equals(new String(""))) {
                continue;
            }
            if (wordInstances.containsKey(words.get(i).getWord())) {
                wordInstances.get(words.get(i).getWord()).add(new LocationAndType(words.get(i).getLocation(), words.get(i).getType()));
            } else {
                List<LocationAndType> locationAndType_s = new ArrayList<LocationAndType>();
                locationAndType_s.add(new LocationAndType(words.get(i).getLocation(), words.get(i).getType()));
                wordInstances.put(words.get(i).getWord(), locationAndType_s);
            }
        }
        return wordInstances;
    }

    @Override
    public void onNewPageParsed(Page page) {
        List<Word> wordsReport = new WikiMarkupParser(page).process();
        //int pageId, String title, String importanceFactor, HashMap<String, List<LocationAndType>> wordInstances
        XPage xpage = new XPage(page.getPageId(), page.getPageTitle(), page.getPageId(), generateHashMapOfWords(wordsReport));

        this.next(xpage);

    }

    ///    HashMap<String, List<PageAndFrequency>> invertedIndexInMemory = new HashMap<String, List<PageAndFrequency>>();

    void saveInvertedToRepository() {
        int size=invertedIndexInMemory.size();
            System.out.println(size);
        int count=0;
        for(String key: invertedIndexInMemory.keySet()){
            XWord temp = new XWord(key,this.invertedIndexInMemory.get(key));
            this.invertedIndexRepository.insert(temp);
            System.out.println(count++);
        }
        
    }

    public void onAllParsed() {
        this.saveInvertedToRepository();
        callback.done(); 
    }

    private XWord getXWord(String word) {
        Cursor<XWord> cursor = this.invertedIndexRepository.find(ObjectFilters.eq("word", word));

        return cursor.firstOrDefault();
    }

    public interface IndexingCallbacks {

        void onProgress(int progress);
//        void onInvertedIndexProgress(int progress);

        void done();
    }

}
