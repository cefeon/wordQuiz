package net.cefeon.wordquiz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "review")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Review_ID_Generator")
    @Column(name = "review_id")
    private Integer id;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private WordQuizUser wordQuizUser;
}