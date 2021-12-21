package net.cefeon.wordquiz.service;

import net.cefeon.wordquiz.model.Review;
import net.cefeon.wordquiz.model.TestResult;
import net.cefeon.wordquiz.model.TranslationPlEng;
import net.cefeon.wordquiz.model.WordQuizUser;
import net.cefeon.wordquiz.nonmodel.ResultJSONHelper;
import net.cefeon.wordquiz.repository.TestResultRepository;
import net.cefeon.wordquiz.repository.WordQuizUserRepository;
import org.aspectj.weaver.ast.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.el.PropertyNotFoundException;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

@Service
public class TestResultService {
    final TestResultRepository testResultRepository;
    final WordQuizUserRepository wordQuizUserRepository;
    final TranslationPlEngService translationPlEngService;
    final ReviewService reviewService;

    public TestResultService(TestResultRepository testResultRepository, WordQuizUserRepository wordQuizUserRepository, TranslationPlEngService translationPlEngService, ReviewService reviewService) {
        this.testResultRepository = testResultRepository;
        this.wordQuizUserRepository = wordQuizUserRepository;
        this.translationPlEngService = translationPlEngService;
        this.reviewService = reviewService;
    }

    public List<TestResult> getWordStatsForCurrentUser(String englishWord) {
        return testResultRepository.findDistinctByReview_WordQuizUser_UserNameAndTranslationPlEng_En_Word(getAuthenticatedUser().getUserName(), englishWord);
    }

    public List<ResultJSONHelper> getResultsForCurrentUser() {
        List<TestResult> testResults = testResultRepository.findDistinctByReview_WordQuizUser_UserName(getAuthenticatedUser().getUserName());
        Map<TranslationPlEng, Double> results = testResults.stream()
                .collect(Collectors.groupingBy(TestResult::getTranslationPlEng, Collectors.summingDouble(x -> x.getResult().equals(Boolean.TRUE) ? 1 : -0.5)));
        return results.entrySet().stream()
                .map(m -> new ResultJSONHelper(m.getKey(), m.getValue(), getLastReviewDateForCurrentUser(m.getKey().getEn().getWord())))
                .collect(Collectors.toList());
    }

    private WordQuizUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<WordQuizUser> optionalWordQuizUser = wordQuizUserRepository.findByUserName(authentication.getName());
        return optionalWordQuizUser.orElseThrow(() -> new UsernameNotFoundException("Not found: " + authentication.getName()));
    }

    private LocalDateTime getLastReviewDateForCurrentUser(String englishWord) {
        return testResultRepository.findDistinctByReview_WordQuizUser_UserNameAndTranslationPlEng_En_WordOrderByReviewDateDesc(getAuthenticatedUser().getUserName(), englishWord).get(0).getReview().getDate();
    }

    public List<TestResult> getUnansweredForCurrentUser(String englishWord){
        List<TestResult> testResults = this.getWordStatsForCurrentUser(englishWord);
        return testResults.stream().filter(x-> !x.getAnswered()).collect(Collectors.toList());
    }

    public TestResult addWordToCurrentUserTest(String englishWord) {
        if (translationPlEngService.getTranslationByEnglishWord(englishWord).isEmpty())
            return null; //TODO: Should be throwed some error in JSON or statuscode
        if (!getUnansweredForCurrentUser(englishWord).isEmpty())
            return null; //TODO: Should be throwed some error in JSON or statuscode
        TranslationPlEng translationPlEng = translationPlEngService.getTranslationByEnglishWord(englishWord).get(0);

        Review review = reviewService.addOrUpdateForCurrentUser(LocalDateTime.now());

        TestResult testResult = TestResult.builder()
                .translationPlEng(translationPlEng)
                .review(review)
                .score(0d)
                .result(false)
                .answered(false)
                .build();

        testResultRepository.save(testResult);

        return testResult;
    }
}
