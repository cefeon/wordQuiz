package net.cefeon.wordquiz.repository;

import net.cefeon.wordquiz.model.TranslationPlEng;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranslationPlEngRepository extends JpaRepository<TranslationPlEng, Integer> {
    Page<TranslationPlEng> findAll(Pageable pageable);
    List<TranslationPlEng> findDistinctByEn_Word(String word);
    List<TranslationPlEng> findDistinctByPl_Word(String word);
}