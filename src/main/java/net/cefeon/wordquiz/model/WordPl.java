package net.cefeon.wordquiz.model;

import lombok.*;

import javax.persistence.*;

@Table(name = "word_pl")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordPl {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WordPl_ID_Generator")
    @Column(name = "word_pl_id")
    private int id;

    @Column(nullable = false, unique = true, length = 10000)
    private String word;
}