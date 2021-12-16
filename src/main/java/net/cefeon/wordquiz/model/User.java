package net.cefeon.wordquiz.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "User_ID_Generator")
    private int id;
    private String userName;
    private String password;
    private boolean active;
    private String authority;
}
