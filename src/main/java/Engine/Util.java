/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static parsers.WikiMarkupParser.REGEX_WORD;

/**
 *
 * @author Arif
 */
public class Util {
    public static List<String> toWords(String s) {
        Pattern pattern = Pattern.compile(REGEX_WORD);
        Matcher matcher = pattern.matcher(s);

        List<String> found = new ArrayList<String>();
        while (matcher.find()) {
            String temp = matcher.group();
            temp = temp.toLowerCase();

            found.add(temp);
        }
        return found;
    }
}
