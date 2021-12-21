package net.cefeon.wordquiz.service;

import net.cefeon.wordquiz.model.TranslationPlEng;
import net.cefeon.wordquiz.model.WordEn;
import net.cefeon.wordquiz.model.WordPl;
import net.cefeon.wordquiz.repository.TranslationPlEngRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TranslationPlEngService {
    private final TranslationPlEngRepository translationPlEngRepository;

    public TranslationPlEngService(TranslationPlEngRepository translationPlEngRepository) {
        this.translationPlEngRepository = translationPlEngRepository;
    }

    public List<TranslationPlEng> getTranslationsPaged(Integer pageNumber) {
        return translationPlEngRepository.findAll(PageRequest.of(pageNumber,20)).stream().collect(Collectors.toList());
    }

    public List<WordPl> englishToPolish(String word) {
        return translationPlEngRepository.findDistinctByEn_Word(word).stream()
                .map(TranslationPlEng::getPl).collect(Collectors.toList());
    }

    public List<WordEn> polishToEnglish(String word) {
        return translationPlEngRepository.findDistinctByPl_Word(word).stream()
                .map(TranslationPlEng::getEn).collect(Collectors.toList());
    }

    public List<TranslationPlEng> getTranslationByEnglishWord(String englishWord){
        return translationPlEngRepository.findDistinctByEn_Word(englishWord);
    }
}
