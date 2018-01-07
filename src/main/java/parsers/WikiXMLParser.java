/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsers;

import Data.Page;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Hassan
 */
public class WikiXMLParser {

    Page currentPage;
    WikiXMLParserCallbackReciever wikiXMLParserCallbackReciever;

    //the path will be given to you..... but for now just edit DSA.java main and edit the path...
    public WikiXMLParser(File xmlFileReference, WikiXMLParserCallbackReciever callback) {
        this.wikiXMLParserCallbackReciever = callback;
    }
    int count=0;
    public void init()
    {
         try {
            SAXParserFactory factory = SAXParserFactory.newInstance(); // obtain and configure SAX based parser
            SAXParser saxParser = null;
            try {
                saxParser = factory.newSAXParser(); //object for SAX parser
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
            }
            DefaultHandler handler = new DefaultHandler() // This is the default handler and all methods are written in it's body
            {
                boolean btitle = false;
                boolean bid = false;
                boolean bid_ig = false;
                boolean btext = false;
                String title="";
                String id="";
                String text="";
                int count = 0;

                //This method is called everytime the parser  gets an open tag '<'
                //Identifies which tag is being opened at the time by assigning an open flag
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("title")) {
                        count++;
                        btitle = true;
                        //System.out.println("\n\nTitle");
                    }
                    if (qName.equalsIgnoreCase("id") && !bid_ig) {
                        bid = true;
                        //System.out.println("\n\nID");
                        bid_ig = true;
                    }
                    if (qName.equalsIgnoreCase("text")) {
                        btext = true;
                        //System.out.println("\n\nText");
                    }
                }

                //For the closing tags >
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (qName.equalsIgnoreCase("title")) {
                        btitle = false;
                    }
                    if (qName.equalsIgnoreCase("id")) {
                        bid = false;
                    }
                    if (qName.equalsIgnoreCase("text")) {
                        btext = false;
                        
                       wikiXMLParserCallbackReciever.onNewPageParsed(new Page(Integer.parseInt(id),title,text));
                       bid_ig = false;
                        title = "";
                        id = "";
                        text = "";    
                        count++;
                        
//                        if(count>15){
//                            System.exit(0);
//                        }
                    }
                }

                //Now Printing data between the tags
                public void characters(char ch[], int start, int length) throws SAXException {
                    if (btitle) {
                        //System.out.print(new String(ch, start, length));
                        title = title + new String(ch, start, length);

                    }
                    if (bid) {
                        //System.out.print( new String(ch, start, length));
                        id = id + new String(ch, start, length);
                    }
                    if (btext) {
                        //System.out.print( new String(ch, start, length));
                        text = text + new String(ch, start, length);
                    }
                }

                @Override
                public void endDocument() throws SAXException {
                    //System.out.println(count);
                    super.endDocument(); //To change body of generated methods, choose Tools | Templates.
                  wikiXMLParserCallbackReciever.onAllParsed();
                }
                
                
            };
            saxParser.parse("wiki.xml", handler);
        } catch (SAXException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    ///when ever you finish reading a page... that is when element is ended..
    //you will create a new Page referece and call to callback...
    /// like : callback.onNewPageParsed(id,title,text);
    //when the file ends call callback.onAllParsed()
    public interface WikiXMLParserCallbackReciever {
        void onNewPageParsed(Page page);
        void onAllParsed();
    }
    
    
    

}
