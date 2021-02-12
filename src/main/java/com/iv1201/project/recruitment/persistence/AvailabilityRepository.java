package com.iv1201.project.recruitment.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Serves as an interface for handling availability related database calls.
 */
@Repository
public interface AvailabilityRepository extends CrudRepository<Availability, String> {

    /**
     *  Called when a availability is needed to be found based on a user a specified 'from' and 'to' date.
     * @param user is the user of interest.
     * @param fromDate is the available from date of interest.
     * @param toDate is the available to date of interest.
     * @return is an optional object specifying if the <>Availability</> is present or not in the database.
     */
    Optional<Availability> findByUserAndFromDateAndToDate(User user, LocalDate fromDate, LocalDate toDate);

    /**
     *  Is called when a search for a application has been initiated from a recruiter.
     * @param fromDate is the available from date of interest.
     * @param toDate is the available to date of interest.
     * @param firstName if the first name of interest.
     * @param lastName is the last name of interest.
     * @param competence is the competence of interest.
     * @return is a list of type <>ApplicationDTO</> containing the name of applicants matching the made query.
     */
    @Query("select distinct new com.iv1201.project.recruitment.persistence.ApplicationDTO(u.firstName, u.lastName) from Availability a join a.user u join u.competences c where " +
            "a.fromDate <= :fromDate and " +
            "a.toDate >= :toDate and " +
            "(:firstName is null or u.firstName = :firstName) and " +
            "(:lastName is null or u.lastName = :lastName) and " +
            "(:competence < 1 or c.competence.id = :competence)")
    List<ApplicationDTO> getAvailabilityApplications(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate,@Param("firstName")String firstName, @Param("lastName")String lastName, @Param("competence") int competence);
}
