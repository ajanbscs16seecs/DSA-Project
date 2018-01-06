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
import DataIndexed.XPage;
import DataIndexed.XWord;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.dizitart.no2.FindOptions;
import org.dizitart.no2.objects.Cursor;
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

    public Indexer(ObjectRepository pageMapRepository, ObjectRepository invertedIndexRepository, IndexingCallbacks callback) {
        this.callback = callback;
        this.pageMapRepository = pageMapRepository;
        this.invertedIndexRepository = invertedIndexRepository;
    }

    public void init() {

    }

    public void next(XPage xpage) {
        this.pageMapRepository.insert(xpage);

    }

    public void generateInvertedIndex() {
        Cursor<XPage> cursor = this.pageMapRepository.find();
        for (XPage xpage : cursor) {
            for (String word : xpage.getWords().keySet()) {//for all words the page add this page to the list present for this word in invertedindex
                XWord tempXWord = getXWord(word);
                if (tempXWord!=null) {
                    //update the value....
                    tempXWord.appendNextPage(xpage.getPageId());
                } else {
                    List<Long> pages = new ArrayList<Long>();
                    pages.add(xpage.getPageId());
                    tempXWord= new XWord(word,pages);
                    this.invertedIndexRepository.insert(tempXWord);
                }
            }
        }
    }

    public HashMap<String, List<LocationAndType>> generateHashMapOfWords(List<Word> words) {

        HashMap<String, List<LocationAndType>> wordInstances = new HashMap<String, List<LocationAndType>>();

        for (int i = 0; i < words.size(); i++) {
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
        XPage xpage = new XPage(page.getPageId(), page.getPageTitle(), 1, generateHashMapOfWords(wordsReport));
        
        this.next(xpage);
        
        this.callback.onProgress((int)xpage.getPageId());
    }

    public void onAllParsed() {
        callback.done();
    }

    private XWord getXWord(String word) {
        Cursor<XWord> cursor = this.invertedIndexRepository.find(ObjectFilters.gt("word", word), FindOptions.limit(0, 1));

        return cursor.firstOrDefault();
    }

    public interface IndexingCallbacks {

        void onProgress(int progress);
        void done();
    }

}
