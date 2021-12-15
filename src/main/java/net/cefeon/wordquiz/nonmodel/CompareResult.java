package net.cefeon.wordquiz.nonmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Builder
@Getter
public class CompareResult implements Comparable<CompareResult>{
    private Double score;
    private String englishWord;
    private String wordToTest;
    private String polishWord;

    @Override
    public int compareTo(CompareResult compareResult) {
        return this.score - compareResult.getScore() > 0 ? -1 : 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompareResult that = (CompareResult) o;
        return Objects.equals(score, that.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score);
    }
}
