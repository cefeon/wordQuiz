package net.cefeon.wordquiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication

public class WordQuizApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(WordQuizApplication.class, args);
    }

}
