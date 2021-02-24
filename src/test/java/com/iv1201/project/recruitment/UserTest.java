package com.iv1201.project.recruitment;


import com.iv1201.project.recruitment.application.CompetenceService;
import com.iv1201.project.recruitment.domain.*;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class UserTest {
    private final String nonexistentUserEmail = "test@nonexistent.com";

    @Autowired
    CompetenceService compService;

    @Bean
    private PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
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
