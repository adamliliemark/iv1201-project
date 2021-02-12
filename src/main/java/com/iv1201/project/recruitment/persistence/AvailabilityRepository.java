package com.iv1201.project.recruitment.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvailabilityRepository extends CrudRepository<Availability, String> {
    Optional<Availability> findByUserAndFromDateAndToDate(User user, LocalDate fromDate, LocalDate toDate);


    @Query("select distinct new com.iv1201.project.recruitment.persistence.ApplicationDTO(u.firstName, u.lastName) from Availability a join a.user u join u.competences c where " +
            "a.fromDate <= :fromDate and " +
            "a.toDate >= :toDate and " +
            "(:firstName is null or u.firstName = :firstName) and " +
            "(:lastName is null or u.lastName = :lastName) and " +
            "(:competence < 1 or c.competence.id = :competence)")
    List<ApplicationDTO> getAvailabilityApplications(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate,@Param("firstName")String firstName, @Param("lastName")String lastName, @Param("competence") int competence);
}
