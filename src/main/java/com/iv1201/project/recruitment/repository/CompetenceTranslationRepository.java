package com.iv1201.project.recruitment.repository;

import com.iv1201.project.recruitment.domain.CompetenceTranslation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Serves as an interface for handling competence related database calls.
 */
@Repository
public interface CompetenceTranslationRepository extends CrudRepository<CompetenceTranslation, String> {

    /**
     * Finds a competence translation by its text
     * @param text the text of the CompetenceTranslation
     * @return Optional matching CompetenceTranslation
     */
    Optional<CompetenceTranslation> findByText(String text);
}