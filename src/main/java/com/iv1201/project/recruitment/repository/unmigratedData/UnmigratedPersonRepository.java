package com.iv1201.project.recruitment.repository.unmigratedData;

import com.iv1201.project.recruitment.domain.unmigratedData.UnmigratedPerson;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Serves as an interface for handling Authority related database calls.
 */
@Repository
public interface UnmigratedPersonRepository extends CrudRepository<UnmigratedPerson, String> {
    Optional<UnmigratedPerson> findByEmail(String email);
}
