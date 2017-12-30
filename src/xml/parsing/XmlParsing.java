/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.parsing;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Hassan Javed
 */
public class XmlParsing {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); //This is a static method which returns the document builder method
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
           Document doc  = builder.parse("books.xml");
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XmlParsing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
