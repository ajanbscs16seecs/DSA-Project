/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsers;

import DataRuntime.Word;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Arif
 */
public class WikiMarkupParser {
    

    public static String REGEX_LINK = "[\\[]{2}[a-zA-Z)0-9\\s]+|[a-zA-Z)0-9\\s]+[\\]]{2}";
    public static String REGEX_WORD = "\\w+";
    public static String REGEX_HEADING1 = "[=]{1}[\\w+\\s+][=]{1}";
    public static String REGEX_HEADING2 = "\\w+";
    public static String REGEX_HEADING3 = "\\w+";
    public static String REGEX_TITLE = "\\w+";
    
    //[=]{1}[\w+][=]{1}
    

    
    


     
    public static List<Word> getWords(String text){
        
        Pattern pattern = Pattern.compile(REGEX_HEADING1);
        List<Word> wordsFound = new ArrayList<Word>();
        // in case you would like to ignore case sensitivity,
        // you could use this statement:
        // Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        
        // check all occurance
        while (matcher.find()) {
//            System.out.println("[\\[]{2}[a-zA-Z)0-9\\s]+ :" +matcher.group("[\\[]{2}[a-zA-Z)0-9\\s]+"));
//            
//            System.out.println("[a-zA-Z)0-9\\s]+[\\]]{2} :" +matcher.group("[a-zA-Z)0-9\\s]+[\\]]{2}"));
//            Word temp = new Word();
//            wordsFound.add(matcher.group());
            System.out.println(matcher.group());
            
        }

        return wordsFound;
    }
       
    
}
