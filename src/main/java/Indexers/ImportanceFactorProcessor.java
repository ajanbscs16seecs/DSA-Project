/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Indexers;

import DataIndexed.LocationAndType;
import DataIndexed.XPage;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import static parsers.WikiMarkupParser.REGEX_WORD;

/**
 *
 * @author Hassan
 */
public class ImportanceFactorProcessor {

    ObjectRepository pageMapRepository;
    ObjectRepository invertedIndexRepository;
    double impotance_factor;
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

    private double findRank(XPage xpage) {
        int count = 0;
        for (List<LocationAndType> instances : xpage.getWordInstances().values()) {
            count = count + instances.size();
        }
        Pattern pattern = Pattern.compile(REGEX_WORD);
        Matcher matcher = pattern.matcher(xpage.getTitle());
        List<Float> title_word_ratio = new ArrayList<Float>();
        while (matcher.find()) {

            String temp = matcher.group();
            temp = temp.toLowerCase();
            float freq = xpage.getWordInstances().get(temp).size();
            title_word_ratio.add(freq / count);

        }
        double average = 0;
        float total = 0;
        for (int i = 0; i < title_word_ratio.size(); i++) {
            total += title_word_ratio.get(i);
        }
        average = total/ title_word_ratio.size();
        return average;
    }

    public interface ImportanceFactorProcessorCallbackReciever {

        void onProgess(int process);
    }

}
