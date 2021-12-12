package net.cefeon.wordquiz.Services;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import net.cefeon.wordquiz.Model.TranslationPlEng;
import net.cefeon.wordquiz.Repository.TranslationPlEngRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoadCSVService {
    private final TranslationPlEngRepository translationPlEngRepository;

    public LoadCSVService(TranslationPlEngRepository translationPlEngRepository) {
        this.translationPlEngRepository = translationPlEngRepository;
    }

    public List<TranslationPlEng> CSVToList(String filename) throws IOException {
        FileReader fileReader = new FileReader("./src/main/resources/"+filename);
        try (CSVReader reader = new CSVReader(fileReader)) {
            List<TranslationPlEng> translations = new ArrayList<>();
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (!(line[0].equals("") || line[1].equals(""))) {
                    TranslationPlEng translationPlEng = new TranslationPlEng(line[0],line[1]);
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
        //translations = translations.stream().limit(20).collect(Collectors.toList());
        translationPlEngRepository.saveAll(translations);
    }
}
