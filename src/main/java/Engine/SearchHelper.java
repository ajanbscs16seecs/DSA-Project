/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

import DataIndexed.PageAndFrequency;
import DataIndexed.XPage;
import DataIndexed.XWord;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
        s = s.toLowerCase();
        List<String> queryWords = toWords(s);
        if (queryWords.size() == 1) {
            return searchSingle(queryWords.get(0));
        } else if (queryWords.size() > 1) {
            return searchMultiple(queryWords);
        }
        return null;
    }

    public List<XPage> searchSingle(String queryWord) {

        XWord xword = getXWord(queryWord);

        List<XPage> pages = new ArrayList<XPage>();
        List<PageAndFrequency> unsortedList = xword.getPagesContainingThis();
        
        List<PageAndFrequency> sortedList = new ArrayList<PageAndFrequency>();
        
        while (!unsortedList.isEmpty()) {

            double max = unsortedList.get(0).getFrequency();
            int maxI = 0;
            for (int i = 0; i < unsortedList.size(); i++) {
                double thisOne = unsortedList.get(i).getFrequency();
                if (thisOne > max) {
                    max = thisOne;
                    maxI = i;

                }
            }
            sortedList.add(unsortedList.get(maxI));
            unsortedList.remove(maxI);
        }
        
        
        System.out.println(sortedList.toString());

        for (int i = 0; i < sortedList.size() && i < 500; i++) {
            long id = sortedList.get(i).getPageId();

            Cursor<XPage> cursor = this.pageMapRepository.find(ObjectFilters.eq("pageId", id), FindOptions.limit(0, 1));
            XPage xpage = cursor.firstOrDefault();
            //System.out.println(xpage.toString());
            pages.add(xpage);
        }
        

        return pages;

    }

    public List<XPage> searchMultiple(List<String> queryWords) {

        List<XPage> pages = new ArrayList<XPage>();

        List<List<PageAndFrequency>> allLists = new ArrayList<List<PageAndFrequency>>();
        for (String queryWord : queryWords) {
            XWord xword = getXWord(queryWord);
            List<PageAndFrequency> list = xword.getPagesContainingThis();
            allLists.add(list);
            System.out.println(list.toString());
        }
        
        
        List<PageAndFrequency> commonList = new ArrayList<PageAndFrequency>();
        for(List<PageAndFrequency> anotherList: allLists){
            commonList.addAll(anotherList);
            commonList.retainAll(anotherList);
        }
        
        
        System.out.println(commonList.toString());
        List<PageAndFrequency> sortedCommonList = new ArrayList<PageAndFrequency>();
        
        while (!commonList.isEmpty()) {

            double max = commonList.get(0).getFrequency();
            int maxI = 0;
            for (int i = 0; i < commonList.size(); i++) {
                double thisOne = commonList.get(i).getFrequency();
                if (thisOne > max) {
                    max = thisOne;
                    maxI = i;

                }
            }
            sortedCommonList.add(commonList.get(maxI));
            commonList.remove(maxI);
        }
        
        
        for (int i = 0; i < sortedCommonList.size() && i < 1000; i++) {
            long id = sortedCommonList.get(i).getPageId();

            Cursor<XPage> cursor = this.pageMapRepository.find(ObjectFilters.eq("pageId", id), FindOptions.limit(0, 1));
            XPage xpage = cursor.firstOrDefault();
            //System.out.println(xpage.toString());
            pages.add(xpage);
        }
        
        

        
        return pages;
    }
    public <PageAndFrequency> List<PageAndFrequency> common(List<PageAndFrequency> list1, List<PageAndFrequency> list2) {
        List<PageAndFrequency> commonList = new ArrayList<PageAndFrequency>();

        for (PageAndFrequency t : list1) {
            if(list2.contains(t)) {
                commonList.add(t);
            }
        }

        return commonList;
    }
    

//    <T> List<T> commonElements(Iterable<? extends List<? extends T>> lists) {
//        Iterator<? extends List<? extends T>> it = lists.iterator();
//        List<T> commonElements = new ArrayList<T>(it.next());
//        while (it.hasNext()) {
//            commonElements.retainAll(it.next());
//        }
//        return commonElements;
//    }

    private XWord getXWord(String word) {
        Cursor<XWord> cursor = this.invertedIndexRepository.find(ObjectFilters.eq("word", word), FindOptions.limit(0, 1));

        return cursor.firstOrDefault();
    }

}
