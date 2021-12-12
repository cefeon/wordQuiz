package net.cefeon.wordquiz;

import net.cefeon.wordquiz.Model.TranslationPlEng;
import net.cefeon.wordquiz.Model.WordEn;
import net.cefeon.wordquiz.Model.WordPl;
import net.cefeon.wordquiz.Repository.TranslationPlEngRepository;
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

@SpringBootApplication
@RestController
public class WordQuizApplication {

    private final TranslationPlEngRepository translationPlEngRepository;
    private final LoadCSVService loadCSVService;

    public WordQuizApplication(TranslationPlEngRepository translationPlEngRepository, LoadCSVService loadCSVService) {
        this.translationPlEngRepository = translationPlEngRepository;
        this.loadCSVService = loadCSVService;
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

    @GetMapping("/pl/{word}")
    public List<WordPl> toPolish(@PathVariable String word) {
        return translationPlEngRepository.findDistinctByEn_Word(word).stream()
                .map(TranslationPlEng::getPl).collect(Collectors.toList());
    }

    @GetMapping("/en/{word}")
    public List<WordEn> toEnglish(@PathVariable String word) {
        return translationPlEngRepository.findDistinctByPl_Word(word).stream()
                .map(TranslationPlEng::getEn).collect(Collectors.toList());
    }

}
