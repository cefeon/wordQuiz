package net.cefeon.wordquiz.nonmodel;

import lombok.Getter;
import lombok.Setter;
import net.cefeon.wordquiz.model.TranslationPlEng;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResultJSONHelper {

    TranslationPlEng translationPlEng;
    Double score;
    LocalDateTime nextReviewDate;

    public ResultJSONHelper(TranslationPlEng translationPlEng, Double score, LocalDateTime lastReviewDate) {
        this.translationPlEng = translationPlEng;
        this.score = score <= 0 ? 0 : score;

        /*Calculates repetition system time for particular score level*/
        double hoursToAdd = (4 + 4 * ((this.score + 2) % 2)) * Math.pow(6, (0.5 * (this.score + 1) - (1 + (this.score + 2) % 2) / 2));

        this.nextReviewDate = lastReviewDate.plusHours((long) hoursToAdd);
    }
}
