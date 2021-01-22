package com.iv1201.project.recruitment;

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
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepo;
    @Autowired
    AuthorityRepository authorityRepo;


    @PostConstruct
    public void addDefaultUsers() {
        System.err.println("Saving users!");
        if(!userRepo.findByEmail("testuser@example.com").isPresent()) {
            User user = new User(
                    "testuser@example.com",
                    "userFirstName",
                    "userLastName",
                    1000L,
                    new BCryptPasswordEncoder().encode("pass"));
            Authority userAuth = new Authority("ROLE_USER", user);
            userRepo.save(user);
            authorityRepo.save(userAuth);
        }
        if(!userRepo.findByEmail("testadmin@example.com").isPresent()) {
            User admin = new User(
                    "testadmin@example.com",
                    "adminFirstName",
                    "adminLastName",
                    1000L,
                    new BCryptPasswordEncoder().encode("pass"));
            Authority adminAuth = new Authority("ROLE_ADMIN", admin);
            userRepo.save(admin);
            authorityRepo.save(adminAuth);
        }
    }
}
