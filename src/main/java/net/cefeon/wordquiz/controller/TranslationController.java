package net.cefeon.wordquiz.controller;

import net.cefeon.wordquiz.model.TranslationPlEng;
import net.cefeon.wordquiz.model.WordEn;
import net.cefeon.wordquiz.model.WordPl;
import net.cefeon.wordquiz.service.TranslationPlEngService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TranslationController {

    private final TranslationPlEngService translationPlEngService;

    public TranslationController(TranslationPlEngService translationPlEngService) {
        this.translationPlEngService = translationPlEngService;
    }

    @GetMapping("/translations/{pageNumber}")
    public List<TranslationPlEng> getTranslationsPaged(@PathVariable Integer pageNumber) {
        return translationPlEngService.getTranslationsPaged(pageNumber);
    }

    @GetMapping("en/pl/{word}")
    public List<WordPl> englishToPolish(@PathVariable String word) {
        return translationPlEngService.englishToPolish(word);
    }

    @GetMapping("pl/en/{word}")
    public List<WordEn> polishToEnglish(@PathVariable String word) {
        return translationPlEngService.polishToEnglish(word);
    }
}
