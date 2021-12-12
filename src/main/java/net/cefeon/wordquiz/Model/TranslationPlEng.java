package net.cefeon.wordquiz.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "TRANSLATION_PL_ENG")
@Entity
@NoArgsConstructor
public class TranslationPlEng {
    @Id
    @Getter
    @Setter
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TranslationPlEng_ID_Generator")
    private Integer id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @Getter
    @Setter
    @JoinColumn(name = "pl_id", nullable = false)
    private WordPl pl;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @Getter
    @Setter
    @JoinColumn(name = "en_id", nullable = false)
    private WordEn en;

    public TranslationPlEng(String wordEn, String wordPl) {
        this.pl = WordPl.builder().word(wordPl).build();
        this.en = WordEn.builder().word(wordEn).build();
    }
}