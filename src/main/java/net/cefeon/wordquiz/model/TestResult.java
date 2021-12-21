package net.cefeon.wordquiz.model;

import lombok.*;

import javax.persistence.*;

@Table(name = "test_result")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Test_ResultID_Generator")
    @Column(name = "test_result_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "translation_pl_eng_id")
    private TranslationPlEng translationPlEng;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(name = "result")
    private Boolean result;

    @Column(name = "score")
    private Double score;

    @Column(name = "answered")
    private Boolean answered;
}