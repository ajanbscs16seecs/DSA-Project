/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

import DataIndexed.XPage;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;

/**
 *
 * @author Arif
 */
public class ByFrequencySorter {
    
    ObjectRepository pageMapRepository;
    ObjectRepository invertedIndexRepository;
    ByFrequencySorterCallBackReciever callBackReciever;
    
    
    int progress;

    public ByFrequencySorter(ObjectRepository pageMapRepository, ObjectRepository invertedIndexRepository, ByFrequencySorterCallBackReciever callBackReciever) {
        this.pageMapRepository = pageMapRepository;
        this.invertedIndexRepository = invertedIndexRepository;
        this.callBackReciever = callBackReciever;
        progress = 0;
    }
    
    
    
    
    public void init(){
        
        Cursor<XPage> cursor = this.invertedIndexRepository.find();
        for (XPage xpage : cursor) {
            
            
            //for(int i=0; i<)
            
            
            
            this.callBackReciever.onProgress((int) xpage.getPageId());
        }
        
    }
    
    
    
    
    public interface ByFrequencySorterCallBackReciever{
        void onProgress(int progress);
    }
    
    
    
}
