package net.cefeon.wordquiz.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "translation_pl_eng")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class TranslationPlEng {
    @Id
    @Column(name = "translation_pl_eng_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TranslationPlEng_ID_Generator")
    private Integer id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "word_pl_id", nullable = false)
    private WordPl pl;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "word_en_id", nullable = false)
    private WordEn en;

    private int level;

    private boolean mainTranslation;

    public TranslationPlEng(WordEn wordEn, WordPl wordPl) {
        this.pl = wordPl;
        this.en = wordEn;
    }
}