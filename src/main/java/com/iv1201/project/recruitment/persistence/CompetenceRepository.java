package com.iv1201.project.recruitment.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Serves as an interface for handling competence related database calls.
 */
@Repository
public interface CompetenceRepository extends CrudRepository<Competence, String> {

    /**
     * Called when a <>Competence</> needs to be located in the database based on its id.
     * @param id is the id of the competence.
     * @return is an <>Optional</> object specifying if the competence is in the database or not.
     */
    Optional<Competence> findById(Long id);
}