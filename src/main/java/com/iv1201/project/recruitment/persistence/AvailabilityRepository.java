package com.iv1201.project.recruitment.persistence;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AvailabilityRepository extends CrudRepository<Availability, String> {
    Optional<Availability> findByUserAndFromDateAndToDate(User user, LocalDate fromDate, LocalDate toDate);
}
