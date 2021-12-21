package net.cefeon.wordquiz.controller;

import net.cefeon.wordquiz.model.TestResult;
import net.cefeon.wordquiz.nonmodel.ResultJSONHelper;
import net.cefeon.wordquiz.service.TestResultService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestResultController {
    private final TestResultService testResultService;

    public TestResultController(TestResultService testResultService) {
        this.testResultService = testResultService;
    }

    @GetMapping("/user/word/{englishWord}/stats")
    public List<TestResult> getUserWordStats(@PathVariable String englishWord) {
        return testResultService.getWordStatsForCurrentUser(englishWord);
    }

    @GetMapping("/user/wordlist/results")
    public List<ResultJSONHelper> getResults() {
        return testResultService.getResultsForCurrentUser();
    }

    @PostMapping("/user/wordlist/add")
    public String addReview(@RequestParam("englishWord") String englishWord) {
        testResultService.addWordToCurrentUserTest(englishWord);
        return "New world to test added with success";
    }
}
