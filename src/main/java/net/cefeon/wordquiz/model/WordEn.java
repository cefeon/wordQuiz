package net.cefeon.wordquiz.model;

import lombok.*;

import javax.persistence.*;

@Table(name = "word_en")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WordEn {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WordEn_ID_Generator")
    @Column(name = "word_en_id")
    private int id;

    @Column(nullable = false, length = 10000)
    private String word;
}