package net.cefeon.wordquiz.nonmodel;

import lombok.Getter;
import lombok.Setter;
import net.cefeon.wordquiz.model.TranslationPlEng;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResultJSONHelper {

    TranslationPlEng translationPlEng;
    Double wordLevel;
    LocalDateTime nextReviewDate;

    public ResultJSONHelper(TranslationPlEng translationPlEng, Double wordLevel, LocalDateTime lastReviewDate) {
        this.translationPlEng = translationPlEng;
        this.wordLevel = wordLevel <= 0 ? 0 : wordLevel;
        /*Calculates repetition system time for particular score level*/
        double hoursToAdd = (4 + 4 * ((this.wordLevel + 2) % 2)) * Math.pow(6, (0.5 * (this.wordLevel + 1) - (1 + (this.wordLevel + 2) % 2) / 2));

        this.nextReviewDate = lastReviewDate.plusHours((long) hoursToAdd);
    }
}
