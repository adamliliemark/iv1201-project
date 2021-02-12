package com.iv1201.project.recruitment.repository;

import com.iv1201.project.recruitment.domain.ApplicationDTO;
import com.iv1201.project.recruitment.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * An interface for handling <>User</> related database handling.
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {

    /**
     * Fetch a User from storage
     * @param email to find by
     * @return User with matching email
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks whether a user by this email exists
     * @param email to find by
     * @return User with matching email
     */
    boolean existsByEmail(String email);

    /**
     * Retrieves the first and last name of the users matching the parameters entered via the applications list view.
     * @param firstName is the first name of interest.
     * @param lastName is the last name of interest.
     * @param competence is the competence of interest.
     * @return is a list of type <>ApplicationDTO</> matching the search query made.
     */
    @Query("SELECT new com.iv1201.project.recruitment.domain.ApplicationDTO(u.firstName, u.lastName) FROM users u join u.competences c " +
            "WHERE (:firstName is null or u.firstName = :firstName) and " +
            "(:lastName is null or u.lastName = :lastName) and " +
            "(:competence < 1 or c.competence.id = :competence)")
    List<ApplicationDTO> getUserApplications(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("competence") int competence);
}