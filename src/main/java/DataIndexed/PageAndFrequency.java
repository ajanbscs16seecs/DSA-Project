/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataIndexed;

import java.io.Serializable;

/**
 *
 * @author Arif
 */
public class PageAndFrequency implements Serializable{
    long pageId;
    int frequency;

    public PageAndFrequency() {
    }

    
    
    
    public PageAndFrequency(long pageId, int frequency) {
        this.pageId = pageId;
        this.frequency = frequency;
    }

    public long getPageId() {
        return pageId;
    }

    public int getFrequency() {
        return frequency;
    }

    @Override
    public String toString() {
        return "PageAndFrequency{" + "pageId=" + pageId + ", frequency=" + frequency + '}';
    }
    
    
    
     
}
