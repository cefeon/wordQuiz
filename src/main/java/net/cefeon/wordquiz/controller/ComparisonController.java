package net.cefeon.wordquiz.controller;

import net.cefeon.wordquiz.nonmodel.CompareResult;
import net.cefeon.wordquiz.service.CompareService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class ComparisonController {
    private final CompareService compareService;

    public ComparisonController(CompareService compareService) {
        this.compareService = compareService;
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
