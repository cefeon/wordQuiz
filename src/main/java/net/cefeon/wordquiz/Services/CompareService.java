package net.cefeon.wordquiz.Services;

import info.debatty.java.stringsimilarity.JaroWinkler;
import net.cefeon.wordquiz.Model.TranslationPlEng;
import net.cefeon.wordquiz.Model.WordEn;
import net.cefeon.wordquiz.Model.WordPl;
import net.cefeon.wordquiz.NonModel.CompareResult;
import net.cefeon.wordquiz.Repository.TranslationPlEngRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompareService {
    private final TranslationPlEngRepository translationPlEngRepository;

    public CompareService(TranslationPlEngRepository translationPlEngRepository) {
        this.translationPlEngRepository = translationPlEngRepository;
    }

    public List<CompareResult> englishToPolish(String enWord, String wordToTest) {

        JaroWinkler jw = new JaroWinkler();
        List<WordPl> translations = translationPlEngRepository
                .findDistinctByEn_Word(enWord).stream()
                .map(TranslationPlEng::getPl).collect(Collectors.toList());

        String databaseWord = translations.get(0).getWord();
        List<CompareResult> compareResults = new ArrayList<>();
        translations.forEach(x-> compareResults.add(CompareResult.builder()
                .englishWord(enWord)
                .wordToTest(wordToTest)
                .polishWord(databaseWord)
                .score(jw.similarity(x.getWord(),wordToTest))
                .build()));
        Collections.sort(compareResults);
        return compareResults;
    }

    public List<CompareResult> polishToEnglish(String plWord, String wordToTest) {

        JaroWinkler jw = new JaroWinkler();
        List<WordEn> translations = translationPlEngRepository
                .findDistinctByPl_Word(plWord).stream()
                .map(TranslationPlEng::getEn).collect(Collectors.toList());

        String databaseWord = translations.get(0).getWord();
        List<CompareResult> compareResults = new ArrayList<>();
        translations.forEach(x-> compareResults.add(CompareResult.builder()
                .englishWord(plWord)
                .wordToTest(wordToTest)
                .polishWord(databaseWord)
                .score(jw.similarity(x.getWord(),wordToTest))
                .build()));
        Collections.sort(compareResults);
        return compareResults;
    }
}
