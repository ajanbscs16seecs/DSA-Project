/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataIndexed;

import Data.Page;
import Data.Word;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Arif
 */
public class Index implements Serializable{
    
    
    public HashMap<Integer,XPage> pageMap;//pagesId->words
    public HashMap<String,List<Integer>> invertedIndex;//word_strings->pages
    
    
    
    public List<Integer> pageIdsSortedByImportanceFactor;
   
    
    
}
