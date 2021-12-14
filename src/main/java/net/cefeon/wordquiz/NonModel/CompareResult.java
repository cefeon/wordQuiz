package net.cefeon.wordquiz.NonModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CompareResult implements Comparable<CompareResult>{
    private Double score;
    private String englishWord;
    private String wordToTest;
    private String polishWord;

    @Override
    public int compareTo(CompareResult o) {
        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
        return this.score - o.getScore() > 0 ? -1 : 1;
    }
}
