package com.iv1201.project.recruitment.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Serves as an interface for handling Authority related database calls.
 */
@Repository
public interface AuthorityRepository extends CrudRepository<Authority, String> {
}