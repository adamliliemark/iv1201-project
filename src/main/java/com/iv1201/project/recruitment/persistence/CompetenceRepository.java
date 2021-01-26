package com.iv1201.project.recruitment.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompetenceRepository extends CrudRepository<Competence, String> {
    boolean existsByName(String name);
    Optional<Competence> findByName(String name);
}