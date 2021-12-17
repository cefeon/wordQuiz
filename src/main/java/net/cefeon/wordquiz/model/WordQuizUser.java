package net.cefeon.wordquiz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Table(name = "user")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordQuizUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "User_ID_Generator")
    @Column(name="user_id")
    private int id;
    @Column(unique = true)
    private String userName;
    private String password;
    private boolean active;
    private String authority;
}
