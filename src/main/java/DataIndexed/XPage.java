/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataIndexed;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Arif
 */
public class XPage {
    int pageId;
    String title;
    int importanceFactor;
    HashMap<String,List<LocationAndType>> wordInstances;//for a word the page will have multiple instances represented by location and type

    public XPage(int pageId, String title, int importanceFactor, HashMap<String, List<LocationAndType>> wordInstances) {
        this.pageId = pageId;
        this.title = title;
        this.importanceFactor = importanceFactor;
        this.wordInstances = wordInstances;
    }
    
    
    
    public int getPageId() {
        return pageId;
    }

    public HashMap<String, List<LocationAndType>> getWords() {
        return wordInstances;
    }

    
    
    
}
