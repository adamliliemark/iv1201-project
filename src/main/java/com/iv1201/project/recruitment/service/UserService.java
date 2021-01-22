package com.iv1201.project.recruitment.service;

import com.iv1201.project.recruitment.persistence.Authority;
import com.iv1201.project.recruitment.persistence.AuthorityRepository;
import com.iv1201.project.recruitment.persistence.User;
import com.iv1201.project.recruitment.persistence.UserRepository;
import com.iv1201.project.recruitment.service.UserServiceError.ERROR_CODE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;


@Component
@Service
public class UserService {
    public enum Role {
        ROLE_USER,
        ROLE_ADMIN
    }

    @Autowired
    UserRepository userRepo;
    @Autowired
    AuthorityRepository authorityRepo;

    /**
     * Adds a new user into the system, if it is valid.
     * This is example functionality that a service provides.
     * Validating business logic and interacting with the data store.
     */
    public void addNewUser(String email, String firstName, String lastName, String clearTextPassword, Role role, Long ssn) throws UserServiceError {

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

    private boolean validateUser(String email, String firstName, String lastName, String clearTextPassword, Long ssn) throws UserServiceError {
        if (email == null || email == "")
            throw new UserServiceError(ERROR_CODE.INVALID_EMAIL);
        if (firstName == null || firstName == "")
            throw new UserServiceError(ERROR_CODE.INVALID_FIRST_NAME);
        if (lastName == null || lastName == "")
            throw new UserServiceError(ERROR_CODE.INVALID_LAST_NAME);
        if (clearTextPassword == null || clearTextPassword.length() < 3)
            throw new UserServiceError(ERROR_CODE.INVALID_PASSWORD);
        if (ssn == null)
            throw new UserServiceError(ERROR_CODE.INVALID_SSN);
        return true;
    }

    @PostConstruct
    public void addDefaultUsers() {
        try {
            if (!userRepo.findByEmail("testuser@example.com").isPresent()) {
                System.err.println("Saving test user!");
                addNewUser("testuser@example.com",
                        "userFirstName",
                        "userLastName",
                        "pass",
                        Role.ROLE_USER,
                        1000L);
            }
            if (!userRepo.findByEmail("testadmin@example.com").isPresent()) {
                System.err.println("Saving test admin!");
                addNewUser("testadmin@example.com",
                        "adminFirstName",
                        "adminLastName",
                        "pass",
                        Role.ROLE_ADMIN,
                        1000L);
            }
        } catch(UserServiceError e) {
            System.err.println("Error creating test users " + e.errorCode);
        }
    }
}
