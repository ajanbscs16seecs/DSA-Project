/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.parsing;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Element;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
           NodeList book_list = doc.getElementsByTagName("book");
           for (int i=0;i<book_list.getLength();i++)
           {
               Node b = book_list.item(i);
               if (b.getNodeType()== Node.ELEMENT_NODE)
               {
                   Element books = (Element) b;
                   String id  = books.getAttribute("id");
                  NodeList child_names = books.getChildNodes();
                  for (int j=0;j<child_names.getLength();j++)
                  {
                      Node n = child_names.item(j);
                      if (n.getNodeType() == Node.ELEMENT_NODE)
                      {
                        Element name = (Element) n;
                        System.out.println("Book : " + id + ":" + name.getTagName()+ "=" + name.getTextContent());
                      }
                  }
                   
               }
           }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XmlParsing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
