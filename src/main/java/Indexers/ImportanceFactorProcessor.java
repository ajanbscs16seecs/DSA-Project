/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Indexers;

import DataIndexed.XPage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import static parsers.WikiMarkupParser.REGEX_WORD;

/**
 *
 * @author Arif
 */
public class ImportanceFactorProcessor {

    ObjectRepository pageMapRepository;
    ObjectRepository invertedIndexRepository;

    ImportanceFactorProcessorCallbackReciever callbackReciever;

    public ImportanceFactorProcessor(ObjectRepository pageMapRepository, ObjectRepository invertedIndexRepository, ImportanceFactorProcessorCallbackReciever callbackReciever) {
        this.pageMapRepository = pageMapRepository;
        this.invertedIndexRepository = invertedIndexRepository;
        this.callbackReciever = callbackReciever;
    }

    public void init() {
        Cursor<XPage> results = pageMapRepository.find();

        for (XPage xpage : results) {
            xpage.setImportanceFactor(findRank(xpage));
            this.pageMapRepository.update(xpage);
            this.callbackReciever.onProgess(0);
        }

    }

    private int findRank(XPage xpage) {

        Pattern pattern = Pattern.compile(REGEX_WORD);
        Matcher matcher = pattern.matcher(xpage.getTitle());

        while (matcher.find()) {

            String temp = matcher.group();
            temp = temp.toLowerCase();
            
            
            
            
        }
    }

    public interface ImportanceFactorProcessorCallbackReciever {

        void onProgess(int process);
    }

}
