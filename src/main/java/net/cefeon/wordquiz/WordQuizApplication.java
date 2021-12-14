package net.cefeon.wordquiz;

import net.cefeon.wordquiz.Model.TranslationPlEng;
import net.cefeon.wordquiz.Model.WordEn;
import net.cefeon.wordquiz.Model.WordPl;
import net.cefeon.wordquiz.NonModel.CompareResult;
import net.cefeon.wordquiz.Repository.TranslationPlEngRepository;
import net.cefeon.wordquiz.Services.CompareService;
import net.cefeon.wordquiz.Services.LoadCSVService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@RestController
public class WordQuizApplication {

    private final TranslationPlEngRepository translationPlEngRepository;
    private final LoadCSVService loadCSVService;
    private final CompareService compareService;

    public WordQuizApplication(TranslationPlEngRepository translationPlEngRepository, LoadCSVService loadCSVService, CompareService compareService) {
        this.translationPlEngRepository = translationPlEngRepository;
        this.loadCSVService = loadCSVService;
        this.compareService = compareService;
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(WordQuizApplication.class, args);
    }

    @GetMapping(value = "/loadcsv")
    public @ResponseBody ResponseEntity<String> get() throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        loadCSVService.saveListToDatabase();
        stopWatch.stop();
        return new ResponseEntity<>("CSV Loaded in "+stopWatch.getLastTaskTimeMillis()/1000+ " seconds", HttpStatus.OK);
    }

    @GetMapping("/translations/{pageNumber}")
    public List<TranslationPlEng> all(@PathVariable Integer pageNumber) {
        return translationPlEngRepository.findAll(PageRequest.of(pageNumber,20)).stream().collect(Collectors.toList());
    }

    @GetMapping("en/pl/{word}")
    public List<WordPl> englishToPolish(@PathVariable String word) {
        return translationPlEngRepository.findDistinctByEn_Word(word).stream()
                .map(TranslationPlEng::getPl).collect(Collectors.toList());
    }

    @GetMapping("pl/en/{word}")
    public List<WordEn> polishToEnglish(@PathVariable String word) {
        return translationPlEngRepository.findDistinctByPl_Word(word).stream()
                .map(TranslationPlEng::getEn).collect(Collectors.toList());
    }

    @GetMapping("compare/en/pl/{enWord}/{wordToTest}")
    public List<List<CompareResult>> compareEnglishToPolish(@PathVariable String enWord, @PathVariable String wordToTest) {
        return Stream.of(compareService.englishToPolish(enWord, wordToTest)).collect(Collectors.toList());
    }

    @GetMapping("compare/pl/en/{plWord}/{wordToTest}")
    public List<List<CompareResult>> comparePolishToEnglish(@PathVariable String plWord, @PathVariable String wordToTest) {
        return Stream.of(compareService.polishToEnglish(plWord, wordToTest)).collect(Collectors.toList());
    }
}
