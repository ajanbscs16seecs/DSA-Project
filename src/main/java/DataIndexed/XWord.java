/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataIndexed;

import java.util.ArrayList;
import java.util.List;
import org.dizitart.no2.objects.Id;

/**
 *
 * @author Arif
 */
public class XWord {
    
    @Id
    String word;
    
    List<Long> pagesContainingThis;

    public XWord(String word, List<Long> pagesContainingThis) {
        this.word = word;
        this.pagesContainingThis = pagesContainingThis;
    }
    
    
    
    public void appendNextPage(long pageId){
        if(this.pagesContainingThis==null){
            this.pagesContainingThis = new ArrayList<Long>();
        }
        this.pagesContainingThis.add(pageId);
    }
    
    
    
    
}
