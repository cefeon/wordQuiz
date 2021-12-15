package net.cefeon.wordquiz.model;

import lombok.*;

import javax.persistence.*;

@Table(name = "WORD_PL", uniqueConstraints = {
        @UniqueConstraint(name = "WORD_PL_id_uindex", columnNames = {"id"})
})
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordPl {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WordPl_ID_Generator")
    private int id;

    @Column(name = "word", nullable = false, unique = true, length = 10000)
    private String word;
}