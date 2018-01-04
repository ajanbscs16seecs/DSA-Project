/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsers;

import Data.Page;
import java.io.File;

/**
 *
 * @author Hassan
 */
public class WikiXMLParser {
    
    
    
    Page currentPage;
    WikiXMLParserCallbackReciever wikiXMLParserCallbackReciever;
    
    
    
    //the path will be given to you..... but for now just edit DSA.java main and edit the path...
    public WikiXMLParser(File xmlFileReference,WikiXMLParserCallbackReciever callback) {
        this.wikiXMLParserCallbackReciever = callback;
    }
    
    
    ///when ever you finish reading a page... that is when element is ended..
    //you will create a new Page referece and call to callback...
    /// like : callback.onNewPageParsed(id,title,text);
    
    
    
    //when the file ends call callback.onAllParsed()
    
    public interface WikiXMLParserCallbackReciever{
        void onNewPageParsed(Page page);
        void onAllParsed();
    }
    
    
    
    
}
