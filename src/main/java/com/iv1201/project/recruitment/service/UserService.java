package com.iv1201.project.recruitment.service;

import com.iv1201.project.recruitment.persistence.*;
import com.iv1201.project.recruitment.service.UserServiceError.ERROR_CODE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.util.Optional;


@Component
@Service
public class UserService {

    public enum Role {
        ROLE_USER,
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

    /**
     * Adds a new user into the system, if it is valid.
     * This is example functionality that a service provides.
     * Validating business logic and interacting with the data store.
     */
    public void addNewUser(String email, String firstName, String lastName, String clearTextPassword, Role role, String ssn) throws UserServiceError {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(validateUser(email, firstName, lastName, clearTextPassword, ssn)) {
            if(userRepo.findByEmail(email).isPresent())
                throw new UserServiceError(ERROR_CODE.CONFLICTING_USER);

            User user = new User(email, firstName, lastName, ssn, encoder.encode(clearTextPassword));
            Authority userAuth = new Authority(role.toString(), user);
            userRepo.save(user);
            authorityRepo.save(userAuth);
        }
    }

    public User saveUser(User user) {
        return userRepo.save(user);
    }


    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

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

    @PostConstruct
    public void addDefaultUsers() {
        //Proxy for "is first run"
        if(userRepo.count() != 0)
            return;
        try {
            if (!userRepo.findByEmail("testuser@example.com").isPresent()) {
                System.err.println("Saving test user!");
                addNewUser("testuser@example.com",
                        "userFirstName",
                        "userLastName",
                        "pass",
                        Role.ROLE_USER,
                        "19880101");
            }

            if (!userRepo.findByEmail("testadmin@example.com").isPresent()) {
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
    private void addDefaultCompetences() {
        Language swedish = languageRepo.save(new Language("lang_se", "svenska"));
        Language english = languageRepo.save(new Language("lang_en", "english"));
        Competence dogging = new Competence();
        Competence fishing = new Competence();
        competenceRepo.save(dogging);
        competenceRepo.save(fishing);
        fishing = competenceRepo.findById(fishing.getId()).get();
        dogging = competenceRepo.findById(dogging.getId()).get();
        dogging.addTranslation(swedish, "hundning");
        dogging.addTranslation(english, "dogging");
        fishing.addTranslation(swedish, "fiske");
        fishing.addTranslation(english, "fishing");

        //Save them
        Competence competences[] = {
                dogging,
                fishing
        };

        for (Competence c : competences) {
            competenceRepo.save(c);
        }
    }
}
