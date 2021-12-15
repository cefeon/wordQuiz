package net.cefeon.wordquiz.controller;

import net.cefeon.wordquiz.model.TranslationPlEng;
import net.cefeon.wordquiz.model.WordEn;
import net.cefeon.wordquiz.model.WordPl;
import net.cefeon.wordquiz.repository.TranslationPlEngRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TranslationController {

    private final TranslationPlEngRepository translationPlEngRepository;

    public TranslationController(TranslationPlEngRepository translationPlEngRepository) {
        this.translationPlEngRepository = translationPlEngRepository;
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
}
