package net.cefeon.wordquiz.repository;

import net.cefeon.wordquiz.model.WordQuizUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WordQuizUserRepository extends JpaRepository<WordQuizUser, Integer> {
    Optional<WordQuizUser> findByUserName(String userName);
}
