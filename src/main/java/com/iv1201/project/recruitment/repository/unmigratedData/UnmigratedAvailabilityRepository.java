package com.iv1201.project.recruitment.repository.unmigratedData;

import com.iv1201.project.recruitment.domain.unmigratedData.UnmigratedAvailability;
import com.iv1201.project.recruitment.domain.unmigratedData.UnmigratedCompetenceProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * Serves as an interface for handling Unmigrated data related database calls.
 */
@Repository
public interface UnmigratedAvailabilityRepository extends CrudRepository<UnmigratedAvailability, String> {
    Iterable<UnmigratedAvailability>findAllByPersonId(Long personId);
}
