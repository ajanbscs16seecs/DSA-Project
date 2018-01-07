/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataIndexed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.dizitart.no2.objects.Id;

/**
 *
 * @author Arif
 */
public class XWord  implements Serializable  {
    
    @Id
    String word;

    public XWord() {
    }

   
    
    
    
    
    
    
    
    List<PageAndFrequency> pagesContainingThis ;

    public XWord(String word, List<PageAndFrequency> pagesContainingThis) {
        this.word = word;
        this.pagesContainingThis = pagesContainingThis;
        
    }
    
    
    
    public void appendNextPage(long pageId,int frequency){
        if(this.pagesContainingThis==null){
            this.pagesContainingThis = new ArrayList<PageAndFrequency>();
        }
        this.pagesContainingThis.add(new PageAndFrequency(pageId,frequency));
    }

    public String getWord() {
        return word;
    }

    public List<PageAndFrequency> getPagesContainingThis() {
        return pagesContainingThis;
    }

    @Override
    public String toString() {
        return "XWord{" + "word=" + word + ", pagesContainingThis=" + pagesContainingThis + '}';
    }
    
    
    
    
    
    
    
}
