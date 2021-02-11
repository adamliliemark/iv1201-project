package com.iv1201.project.recruitment.service;

import com.iv1201.project.recruitment.persistence.*;
import com.iv1201.project.recruitment.service.UserServiceError.ERROR_CODE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Optional;

@Component
@Service
public class UserService {


    /**
     * User role
     * the role of the user determines access levels
     */
    public enum Role {
        /** ROLE_USER denotes user level access*/
        ROLE_USER,
        /** ROLE_ADMIN denotes recruiter level access */
        ROLE_ADMIN
    }

    @Autowired
    CompetenceRepository competenceRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    AuthorityRepository authorityRepo;

    @Autowired
    LanguageRepository languageRepo;

    @Autowired
    AvailabilityRepository availabilityRepository;


    /**
     * Adds a new user to the system if valid
     * @param email a unique valid email
     * @param firstName users first name
     * @param lastName users last name
     * @param clearTextPassword users password as cleartext
     * @param role the role of the user
     * @see Role
     * @param ssn users social security number as String
     * @throws UserServiceError if user is not valid, or conflicting with an existing user.
     */
    @Transactional
    public void addNewUser(String email, String firstName, String lastName, String clearTextPassword, Role role, String ssn) throws UserServiceError {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(validateUser(email, firstName, lastName, clearTextPassword, ssn)) {
            if(userRepo.existsByEmail(email))
                throw new UserServiceError(ERROR_CODE.CONFLICTING_USER);
            User user = new User(email, firstName, lastName, ssn, encoder.encode(clearTextPassword));
            Authority userAuth = new Authority(role.toString(), user);
            userRepo.save(user);
            authorityRepo.save(userAuth);
        }
    }

    /**
     * Saves the user to the store
     * If the user has new competences, they will be stored as well
     * and the retrurned user will contain the saved competences,
     * including generated ID's
     * If the user already exists, it will be updated in the store
     * @param user the user to save
     * @return the saved user.
     */
    @Transactional
    public User saveUser(User user) {
        return userRepo.save(user);

    }

    /**
     * Gets a user from the data store.
     * This method is transactional because of the greedy
     *
     * @param email the email of the user to get.
     * @return the found user or empty
     */
    @Transactional
    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }


    /**
     * Returns true if a matching row exists, else false
     * @param email the email to check by.
     * @return
     */
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }


    /**
     * Validates a new user. A user with the same email may not exist
     * @param email a valid email
     * @param firstName a valid first name
     * @param lastName a valid last name
     * @param clearTextPassword a cleartext password
     * @param ssn social security number as String
     * @return True if user is valid, else false
     * @throws UserServiceError with the first error as ERROR_CODE
     * @see ERROR_CODE
     */
    private boolean validateUser(String email, String firstName, String lastName, String clearTextPassword, String ssn) throws UserServiceError {
        if (email == null || email.equals(""))
            throw new UserServiceError(ERROR_CODE.INVALID_EMAIL);
        if (firstName == null || firstName.equals(""))
            throw new UserServiceError(ERROR_CODE.INVALID_FIRST_NAME);
        if (lastName == null || lastName.equals(""))
            throw new UserServiceError(ERROR_CODE.INVALID_LAST_NAME);
        if (clearTextPassword == null || clearTextPassword.length() < 3)
            throw new UserServiceError(ERROR_CODE.INVALID_PASSWORD);
        if (ssn == null)
            throw new UserServiceError(ERROR_CODE.INVALID_SSN);
        return true;
    }


    //Adds some default users and competences for testing.
    @PostConstruct
    public void addDefaultData() {
        //Proxy for "is first run"
        if(userRepo.count() != 0)
            return;
        try {
            if (!userRepo.existsByEmail("testuser@example.com")) {
                System.err.println("Saving test user!");
                addNewUser("testuser@example.com",
                        "userFirstName",
                        "userLastName",
                        "pass",
                        Role.ROLE_USER,
                        "19880101");
                Optional<User> userMaybe = userRepo.findByEmail("testuser@example.com");
                User user = userMaybe.get();
                user.addAvailability(LocalDate.of(2022, 2, 2), LocalDate.of(2028, 2, 2));
                userRepo.save(user);
            }

            if (!userRepo.existsByEmail("testadmin@example.com")) {
                System.err.println("Saving test admin!");
                addNewUser("testadmin@example.com",
                        "adminFirstName",
                        "adminLastName",
                        "pass",
                        Role.ROLE_ADMIN,
                        "19890103");
            }

        } catch(UserServiceError e) {
            System.err.println("Error creating test users " + e.errorCode);
        }

        addDefaultCompetences();
    }

    /**
     * Adds default competences for testing
     * @exclude
     */
    private void addDefaultCompetences() {
        Language swedish = languageRepo.save(new Language("sv_SE", "svenska"));
        Language english = languageRepo.save(new Language("en_US", "english"));
        Competence grilling = competenceRepo.save(new Competence());
        Competence carousel = competenceRepo.save(new Competence());
        carousel = competenceRepo.findById(carousel.getId()).get();
        grilling = competenceRepo.findById(grilling.getId()).get();
        grilling.addTranslation(swedish, "Korvgrillning");
        grilling.addTranslation(english, "Grilling sausage");
        carousel.addTranslation(swedish, "Karuselldrift");
        carousel.addTranslation(english, "Carousel operation");

        //Save them
        Competence competences[] = {
                grilling,
                carousel
        };

        for (Competence c : competences) {
            competenceRepo.save(c);
        }
    }
}
