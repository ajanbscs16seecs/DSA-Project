/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Indexers;

import Data.Page;
import Data.Word;
import DataIndexed.Index;
import DataIndexed.LocationAndType;
import DataIndexed.XPage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import parsers.WikiMarkupParser;
import parsers.WikiXMLParser;
import parsers.WikiXMLParser.WikiXMLParserCallback;

/**
 *
 * @author Arif
 */
public class Indexer implements WikiXMLParserCallback {

    Index index;
    
    
    IndexingCallbacks callback;

    public Indexer(Index index,IndexingCallbacks callback) {
        this.index = index;
        this.callback =callback;
    }

    public Indexer(Index index) {
        this.index = index;
    }
    

    public void init() {

    }

    public void next(XPage xpage){
        index.pageMap.put(xpage.getPageId(), xpage);

    }

    public void generateInvertedIndex() {
        for (XPage xpage : index.pageMap.values()) {//for all pages 

            for (String word : xpage.getWords().keySet()) {//for all words the page add this page to the list present for this word in invertedindex
                if (index.invertedIndex.containsKey(word)) {
                    index.invertedIndex.get(word).add(xpage.getPageId());
                } else {
                    List<Integer> temp = new ArrayList<Integer>();
                    temp.add(xpage.getPageId());
                    index.invertedIndex.put(word, temp);
                }
            }
        }

    }

    
   
    
    public HashMap<String, List<LocationAndType>> generateHashMapOfWords(List<Word> words) {

        
        HashMap<String, List<LocationAndType>> wordInstances = new HashMap<String,List<LocationAndType>>();

        for (int i = 0; i < words.size(); i++) {
            if(wordInstances.containsKey(words.get(i).getWord())){
                wordInstances.get(words.get(i).getWord()).add(new LocationAndType(words.get(i).getLocation(),words.get(i).getType()));
            }
            else{
                List<LocationAndType> locationAndType_s  = new ArrayList<LocationAndType>();
                locationAndType_s.add(new LocationAndType(words.get(i).getLocation(),words.get(i).getType()));
                wordInstances.put(words.get(i).getWord(), locationAndType_s);
            }
        }
        return wordInstances;
    }

    public void onNewPageParsed(Page page) {
        List<Word> wordsReport = new WikiMarkupParser(page).process();
        //int pageId, String title, String importanceFactor, HashMap<String, List<LocationAndType>> wordInstances
        XPage xpage = new XPage(page.getPageId(),page.getPageTitle(),1,indexer.generateHashMapOfWords(wordsReport));
        this.next(xpage);
        this.callback.onProgress();
    }

    public void onAllParsed() {
        callback.done();
    }
    
    
        
    public interface IndexingCallbacks{
        void onProgress();
        void done();
    }
    
    
    
}
