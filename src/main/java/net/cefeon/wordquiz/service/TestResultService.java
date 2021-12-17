package net.cefeon.wordquiz.service;

import net.cefeon.wordquiz.model.TestResult;
import net.cefeon.wordquiz.model.TranslationPlEng;
import net.cefeon.wordquiz.model.WordQuizUser;
import net.cefeon.wordquiz.nonmodel.ResultJSONHelper;
import net.cefeon.wordquiz.repository.TestResultRepository;
import net.cefeon.wordquiz.repository.WordQuizUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestResultService {
    final TestResultRepository testResultRepository;
    final WordQuizUserRepository wordQuizUserRepository;

    public TestResultService(TestResultRepository testResultRepository, WordQuizUserRepository wordQuizUserRepository) {
        this.testResultRepository = testResultRepository;
        this.wordQuizUserRepository = wordQuizUserRepository;
    }

    public List<TestResult> getWordStatsForCurrentUser(String englishWord) {
        return testResultRepository.findDistinctByReview_WordQuizUser_UserNameAndTranslationPlEng_En_Word(getAuthenticatedUser().getUserName(), englishWord);
    }

    public List<ResultJSONHelper> getResultsForCurrentUser() {
        List<TestResult> testResults = testResultRepository.findDistinctByReview_WordQuizUser_UserName(getAuthenticatedUser().getUserName());
        Map<TranslationPlEng, Double> results = testResults.stream()
                .collect(Collectors.groupingBy(TestResult::getTranslationPlEng, Collectors.summingDouble(x -> x.getResult().equals(Boolean.TRUE) ? 1 : -0.5)));
        return results.entrySet().stream()
                .map(m->new ResultJSONHelper(m.getKey(),m.getValue(), LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    private WordQuizUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<WordQuizUser> optionalWordQuizUser = wordQuizUserRepository.findByUserName(authentication.getName());
        return optionalWordQuizUser.orElseThrow(() -> new UsernameNotFoundException("Not found: " + authentication.getName()));
    }
}
