/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

import DataIndexed.XPage;
import DataIndexed.XWord;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.dizitart.no2.FindOptions;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;
import static parsers.WikiMarkupParser.REGEX_WORD;

/**
 *
 * @author Arif
 */
public class SearchHelper {

    ObjectRepository pageMapRepository;
    ObjectRepository invertedIndexRepository;

    public SearchHelper(ObjectRepository pageMapRepository, ObjectRepository invertedIndexRepository) {
        this.pageMapRepository = pageMapRepository;
        this.invertedIndexRepository = invertedIndexRepository;
    }

    public List<String> toWords(String s) {
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

    public List<XPage> search(String s) {
        List<String> queryWords = toWords(s);
        if (queryWords.size() ==1) {
            return searchSingle(queryWords.get(0));
        } 
        else if (queryWords.size() > 1) {
            return searchMultiple(queryWords);
        }
        return null;
    }

    public List<XPage> searchSingle(String queryWord) {
        XWord xword = getXWord(queryWord);
        
        
        List<XPage> pages = new ArrayList<XPage>();
        
        for(int i=0; i<xword.getPagesContainingThis().size() && i<10;i++){
            pages.add((XPage)this.pageMapRepository.getById(NitriteId.createId(xword.getPagesContainingThis().get(i).getPageId())));
        }
        
        return pages;

    }

    public List<XPage> searchMultiple(List<String> queryWords) {

        return null;
    }

    private XWord getXWord(String word) {
        Cursor<XWord> cursor = this.invertedIndexRepository.find(ObjectFilters.gt("word", word), FindOptions.limit(0, 1));

        return cursor.firstOrDefault();
    }

}
