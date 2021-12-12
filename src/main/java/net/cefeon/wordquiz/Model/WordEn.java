package net.cefeon.wordquiz.Model;

import lombok.*;

import javax.persistence.*;

@Table(name = "WORD_EN")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WordEn {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WordEn_ID_Generator")
    private int id;

    @Column(name = "word", nullable = false, length = 10000)
    private String word;
}