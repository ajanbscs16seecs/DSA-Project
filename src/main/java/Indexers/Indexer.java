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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Arif
 */
public class Indexer {

    Index index;

    public Indexer(Index index) {
        this.index = index;
    }

    public void init() {

    }

    public void next(XPage xpage) throws Exception {
        if (index == null) {
            throw new Exception("Indexed not present");
        }
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

}
