package com.iv1201.project.recruitment;


import com.iv1201.project.recruitment.application.CompetenceService;
import com.iv1201.project.recruitment.application.UserService;
import com.iv1201.project.recruitment.application.exceptions.UserServiceError;
import com.iv1201.project.recruitment.domain.*;
import com.iv1201.project.recruitment.repository.CompetenceRepository;
import com.iv1201.project.recruitment.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserTest {
    private final String nonexistentUserEmail = "test@nonexistent.com";
    private final String existentUserEmail = "test@existent.com";

    private final String existentUnmigratedPersonEmail = "existent@unmigrated.com";
    private final String existentUnmigratedPersonNoCompetenceEmail = "existentnocompetence@unmigrated.com";
    private final String conflictingUnmigratedPersonEmail = existentUserEmail;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    CompetenceService compService;

    @Bean
    private PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @BeforeAll
    public void beforeAll() {
        User existentUser = new User(existentUserEmail, "testName", "testLastName", "123", encoder().encode("pass"));
        userRepo.save(existentUser);
    }

    @Test
    public void user_should_be_instantiated_with_avail_and_competence() {
        User user = new User(nonexistentUserEmail, "first_name", "last_name", "111000", encoder().encode("pass"));
        assertThat(user.getAvailabilityList()).isNotNull();
        assertThat(user.getAvailabilityList()).isEmpty();
        assertThat(user.getCompetences()).isNotNull();
        assertThat(user.getCompetences()).isEmpty();
    }


    @Test
    public void adding_and_removing_from_sets_should_work() {
        var compMap = compService.getAllWithLocalNames("en_US");
        User user = new User(nonexistentUserEmail, "first_name", "last_name", "111000", encoder().encode("pass"));
        var comp = compMap.get("Grilling sausage");

        user.addCompetence(comp, 2);
        var compProf =  user.
                getCompetences()
                .stream()
                .filter(cp -> cp.getCompetence().getName("en_US").equals(comp.getName("en_US")))
                .findFirst().get();
        assertThat(compProf.getCompetence()).isEqualTo(comp);
        assertThat(compProf.getYearsOfExperience()).isEqualTo(2);


        user.addCompetence(comp, 3);
        assertThat(compProf.getYearsOfExperience()).isEqualTo(3);
        assertThat(user.getCompetences().size()).isEqualTo(1);

        user.removeCompetence(comp);
        assertThat(user.getCompetences()).isEmpty();

        //var avail = new Availability(LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1), user);
        assertThat(user.getAvailabilityList()).isEmpty();
        user.addAvailability(LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1));
        assertThat(user.getAvailabilityList().size()).isEqualTo(1);

        user.addAvailability(LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1));
        assertThat(user.getAvailabilityList().size()).isEqualTo(1);

        user.removeAvailability(new Availability(LocalDate.of(2021, 1, 1), LocalDate.of(2024, 1, 1), user));
        assertThat(user.getAvailabilityList().size()).isEqualTo(1);

        user.removeAvailability(new Availability(LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1), user));
        assertThat(user.getAvailabilityList()).isEmpty();

        assertDoesNotThrow(() -> user.removeAvailability(new Availability(LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1), user)));
    }
}
