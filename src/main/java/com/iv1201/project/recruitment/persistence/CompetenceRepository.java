package com.iv1201.project.recruitment.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Serves as an interface for handling competence related database calls.
 */
@Repository
public interface CompetenceRepository extends CrudRepository<Competence, String> {
    Optional<Competence> findById(Long id);
}