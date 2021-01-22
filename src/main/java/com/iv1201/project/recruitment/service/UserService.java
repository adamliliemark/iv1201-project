package com.iv1201.project.recruitment.service;

import com.iv1201.project.recruitment.persistence.Authority;
import com.iv1201.project.recruitment.persistence.AuthorityRepository;
import com.iv1201.project.recruitment.persistence.User;
import com.iv1201.project.recruitment.persistence.UserRepository;
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
    public void addNewUser(String email, String firstName, String lastName, String clearTextPassword, Role role, Long ssn) {
       boolean isValid = true;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(isValid) {
            User user = new User(email, firstName, lastName, ssn, encoder.encode(clearTextPassword));
            Authority userAuth = new Authority(role.toString(), user);
            userRepo.save(user);
            authorityRepo.save(userAuth);
        }
    }

    @PostConstruct
    public void addDefaultUsers() {
        if(!userRepo.findByEmail("testuser@example.com").isPresent()) {
            System.err.println("Saving test user!");
            addNewUser("testuser@example.com",
                    "userFirstName",
                    "userLastName",
                    "pass",
                    Role.ROLE_USER,
                    1000L);
        }
        if(!userRepo.findByEmail("testadmin@example.com").isPresent()) {
            System.err.println("Saving test admin!");
            addNewUser("testadmin@example.com",
                    "adminFirstName",
                    "adminLastName",
                    "pass",
                    Role.ROLE_ADMIN,
                    1000L);
        }
    }
}
