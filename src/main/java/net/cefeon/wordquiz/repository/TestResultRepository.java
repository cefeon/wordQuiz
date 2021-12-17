package net.cefeon.wordquiz.repository;

import net.cefeon.wordquiz.model.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Integer> {
    List<TestResult> findDistinctByReview_WordQuizUser_UserNameAndTranslationPlEng_En_Word (String userName, String en);
    List<TestResult> findDistinctByReview_WordQuizUser_UserName (String userName);
}
