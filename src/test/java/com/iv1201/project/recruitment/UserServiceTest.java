package com.iv1201.project.recruitment;

import com.iv1201.project.recruitment.domain.User;
import com.iv1201.project.recruitment.domain.unmigratedData.UnmigratedAvailability;
import com.iv1201.project.recruitment.domain.unmigratedData.UnmigratedCompetenceProfile;
import com.iv1201.project.recruitment.domain.unmigratedData.UnmigratedPerson;
import com.iv1201.project.recruitment.repository.UserRepository;
import com.iv1201.project.recruitment.application.UserService;
import com.iv1201.project.recruitment.application.UserService.Role;
import com.iv1201.project.recruitment.application.exceptions.UserServiceError;
import com.iv1201.project.recruitment.repository.unmigratedData.UnmigratedAvailabilityRepository;
import com.iv1201.project.recruitment.repository.unmigratedData.UnmigratedCompetenceProfileRepository;
import com.iv1201.project.recruitment.repository.unmigratedData.UnmigratedPersonRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {
    private final String nonexistentUserEmail = "test@nonexistent.com";
    private final String existentUserEmail = "test@existent.com";

    private final String existentUnmigratedPersonEmail = "existent@unmigrated.com";
    private final String existentUnmigratedPersonNoCompetenceEmail = "existentnocompetence@unmigrated.com";
    private final String conflictingUnmigratedPersonEmail = existentUserEmail;

    @Bean
    private PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private UnmigratedAvailabilityRepository umavailabilityRepo;

    @Autowired
    private UnmigratedCompetenceProfileRepository umCompetenceProfileRepo;

    @Autowired
    private UnmigratedPersonRepository umPersonRepo;

    @BeforeAll
    public void beforeAll() {
        User existentUser = new User(existentUserEmail, "testName", "testLastName", "123", encoder().encode("pass"));
        userRepo.save(existentUser);
    }

    @BeforeEach
    public void each() {
        umPersonRepo.deleteAll();
        umavailabilityRepo.deleteAll();
        umCompetenceProfileRepo.deleteAll();

        UnmigratedPerson up = new UnmigratedPerson(0l,1l, "username1", existentUnmigratedPersonEmail, "firstname",
                "lastname", null, "1234");
        up = umPersonRepo.save(up);
        UnmigratedCompetenceProfile umComp = new UnmigratedCompetenceProfile(
                0L, 0.5, "Karuselldrift", up.getPersonId());
        umCompetenceProfileRepo.save(umComp);
        UnmigratedAvailability umAvail = new UnmigratedAvailability(
                0l, up.getPersonId(), LocalDate.of(2020,1,1), LocalDate.of(2022, 1,1));
        umavailabilityRepo.save(umAvail);

        UnmigratedPerson up2 = new UnmigratedPerson(1L,1L, "username2", existentUnmigratedPersonNoCompetenceEmail, "firstname",
                "lastname", null, "1235");
        up2 = umPersonRepo.save(up2);

        UnmigratedPerson up3 = new UnmigratedPerson(2L,1L, "username3", conflictingUnmigratedPersonEmail, "firstname",
                "lastname", null,"1236");
        up3 = umPersonRepo.save(up3);
    }

    @Test
    public void create_user_invalid_email_throws() {
        UserServiceError e = assertThrows(UserServiceError.class, () -> userService.addNewUser(null, "a", "b", "pass", Role.ROLE_USER, "123"));
        assertThat(e.errorCode).isEqualTo(UserServiceError.ERROR_CODE.INVALID_EMAIL);
        e = assertThrows(UserServiceError.class, () -> userService.addNewUser("", "a", "b", "pass", Role.ROLE_USER, "123"));
        assertThat(e.errorCode).isEqualTo(UserServiceError.ERROR_CODE.INVALID_EMAIL);
    }

    @Test
    public void create_user_invalid_password_throws() {
        UserServiceError e = assertThrows(UserServiceError.class, () -> userService.addNewUser(existentUserEmail, "a", "b", "", Role.ROLE_USER, "123"));
        assertThat(e.errorCode).isEqualTo(UserServiceError.ERROR_CODE.INVALID_PASSWORD);
        e = assertThrows(UserServiceError.class, () -> userService.addNewUser(existentUserEmail, "a", "b", null, Role.ROLE_USER, "123"));
        assertThat(e.errorCode).isEqualTo(UserServiceError.ERROR_CODE.INVALID_PASSWORD);
    }

    @Test
    public void create_user_null_ssn_throws() {
        UserServiceError e = assertThrows(UserServiceError.class, () -> userService.addNewUser(existentUserEmail, "a", "b", "pass", Role.ROLE_USER, null));
        assertThat(e.errorCode).isEqualTo(UserServiceError.ERROR_CODE.INVALID_SSN);
    }

    @Test
    public void create_user_invalid_names_throws() {
        UserServiceError e = assertThrows(UserServiceError.class, () -> userService.addNewUser(existentUserEmail, "", "b", "pass", Role.ROLE_USER, "123"));
        assertThat(e.errorCode).isEqualTo(UserServiceError.ERROR_CODE.INVALID_FIRST_NAME);
        e = assertThrows(UserServiceError.class, () -> userService.addNewUser(existentUserEmail, null, "b", "pass", Role.ROLE_USER, "123"));
        assertThat(e.errorCode).isEqualTo(UserServiceError.ERROR_CODE.INVALID_FIRST_NAME);
        e = assertThrows(UserServiceError.class, () -> userService.addNewUser(existentUserEmail, "a", "", "pass", Role.ROLE_USER, "123"));
        assertThat(e.errorCode).isEqualTo(UserServiceError.ERROR_CODE.INVALID_LAST_NAME);
        e = assertThrows(UserServiceError.class, () -> userService.addNewUser(existentUserEmail, "a", null, "pass", Role.ROLE_USER, "123L"));
        assertThat(e.errorCode).isEqualTo(UserServiceError.ERROR_CODE.INVALID_LAST_NAME);
    }

    @Test
    public void create_conflicting_user_throws() {
        UserServiceError e = assertThrows(UserServiceError.class, () -> userService.addNewUser(existentUserEmail, "a", "b", "pass", Role.ROLE_USER, "123"));
        assertThat(e.errorCode).isEqualTo(UserServiceError.ERROR_CODE.CONFLICTING_USER);
    }

    @Test
    public void create_nonexistent_valid_user() {
        assertDoesNotThrow(() ->userService.addNewUser(nonexistentUserEmail, "a", "b", "pass", Role.ROLE_USER, "123"));
    }

    @Test
    void migrate_nonexistent_unmigrated_person() {
        assertDoesNotThrow(() ->userService.restoreUnmigratedPerson(nonexistentUserEmail));
    }

    @Test
    void migrate_conflicting_unmigrated_person() {
        UserServiceError e = assertThrows(UserServiceError.class, () ->userService.restoreUnmigratedPerson(conflictingUnmigratedPersonEmail));
        assertThat(e.errorCode).isEqualTo(UserServiceError.ERROR_CODE.CONFLICTING_USER);
        assertThat(umPersonRepo.findByEmail(conflictingUnmigratedPersonEmail)).isPresent();
    }

    @Test
    void migrate_valid_existing_unmigrated_person() throws Exception{
        userService.restoreUnmigratedPerson(existentUnmigratedPersonEmail);
        assertThat(userService.existsByEmail(existentUnmigratedPersonEmail)).isEqualTo(true);
        User createdUser = userService.findByEmail(existentUnmigratedPersonEmail).get();
        assertThat(createdUser.getCompetences().size()).isEqualTo(1);
        assertThat(createdUser.getCompetences().stream().findFirst().get().getCompetence().getName("sv_SE")).isEqualTo("Karuselldrift");
        assertThat(createdUser.getAvailabilityList().size()).isEqualTo(1);
        assertThat(createdUser.getAvailabilityList().stream().findFirst().get().getFromDate()).isEqualTo(LocalDate.of(2020, 1,1));
        assertThat(createdUser.getAvailabilityList().stream().findFirst().get().getToDate()).isEqualTo(LocalDate.of(2022, 1,1));
        assertThat(umPersonRepo.findByEmail(existentUnmigratedPersonEmail)).isEmpty();
        assertThat(umavailabilityRepo.count()).isEqualTo(0);
        assertThat(umCompetenceProfileRepo.count()).isEqualTo(0);
    }

    @Test
    void migrate_valid_existing_unmigrated_person_with_no_competence() throws Exception{
        userService.restoreUnmigratedPerson(existentUnmigratedPersonNoCompetenceEmail);
        assertThat(userService.existsByEmail(existentUnmigratedPersonNoCompetenceEmail)).isEqualTo(true);
        User createdUser = userService.findByEmail(existentUnmigratedPersonNoCompetenceEmail).get();
        assertThat(createdUser.getCompetences().size()).isEqualTo(0);
        assertThat(createdUser.getAvailabilityList().size()).isEqualTo(0);
        assertThat(umPersonRepo.findByEmail(existentUnmigratedPersonNoCompetenceEmail)).isEmpty();
    }
}
