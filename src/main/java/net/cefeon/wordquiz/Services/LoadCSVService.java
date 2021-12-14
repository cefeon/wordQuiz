package net.cefeon.wordquiz.Services;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import net.cefeon.wordquiz.Model.TranslationPlEng;
import net.cefeon.wordquiz.Model.WordEn;
import net.cefeon.wordquiz.Model.WordPl;
import net.cefeon.wordquiz.Repository.TranslationPlEngRepository;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LoadCSVService {
    private final TranslationPlEngRepository translationPlEngRepository;

    public LoadCSVService(TranslationPlEngRepository translationPlEngRepository) {
        this.translationPlEngRepository = translationPlEngRepository;
    }

    public List<TranslationPlEng> CSVToList(String filename) throws IOException {
        FileReader fileReader = new FileReader("./src/main/resources/" + filename);
        try (CSVReader reader = new CSVReader(fileReader)) {
            List<TranslationPlEng> translations = new ArrayList<>();
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (!(line[0].equals("") || line[1].equals(""))) {
                    WordEn wordEn = WordEn.builder().word(line[0]).build();
                    WordPl wordPl = WordPl.builder().word(line[1]).build();
                    TranslationPlEng translationPlEng = new TranslationPlEng(wordEn, wordPl);
                    translations.add(translationPlEng);
                }
            }
            return translations;
        } catch (CsvException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void saveListToDatabase() throws IOException {
        List<TranslationPlEng> translations = this.CSVToList("ang_pol.csv");
        //translations = translations.stream().limit(10000).collect(Collectors.toList());
        setEqualPLTranslationsToSameObjects(translations);
        translationPlEngRepository.saveAll(translations);
    }

    private void setEqualPLTranslationsToSameObjects(List<TranslationPlEng> translations) {
        Set<String> duplicatedWordsPl = getPLTranslationswithoutDuplicates(translations);
        List<WordPl> wordsPL = duplicatedWordsPl.stream().map(x -> WordPl.builder().word(x).build()).collect(Collectors.toList());
        translations.stream().filter(x -> duplicatedWordsPl.contains(x.getPl().getWord())).forEach(x ->
                wordsPL.stream().filter(y -> y.getWord().equals(x.getPl().getWord())).forEach(x::setPl));
    }

    public Set<String> getPLTranslationswithoutDuplicates(List<TranslationPlEng> translations) {
        List<String> wordsPL = translations.stream()
                .map(x -> x.getPl().getWord()).collect(Collectors.toList());
        Set<String> duplicatedWordsPlRemoveset = new HashSet<>();
        return wordsPL.stream().filter(word -> !duplicatedWordsPlRemoveset.add(word)).collect(Collectors.toSet());
    }
}
