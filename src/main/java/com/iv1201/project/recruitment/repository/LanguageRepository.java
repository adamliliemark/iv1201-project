package com.iv1201.project.recruitment.repository;

import com.iv1201.project.recruitment.domain.Language;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Serves as an interface for handling languages related database calls.
 */
@Repository
public interface LanguageRepository extends CrudRepository<Language, String> { }
