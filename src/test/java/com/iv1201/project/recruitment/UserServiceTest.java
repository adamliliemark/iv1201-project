package com.iv1201.project.recruitment;

import com.iv1201.project.recruitment.persistence.AuthorityRepository;
import com.iv1201.project.recruitment.persistence.User;
import com.iv1201.project.recruitment.persistence.UserRepository;
import com.iv1201.project.recruitment.service.UserService;
import com.iv1201.project.recruitment.service.UserService.Role;
import com.iv1201.project.recruitment.service.UserServiceError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {
    private final String nonexistentUser = "test@nonexistent.com";
    private final String existentUser = "test@existent.com";

    @Bean
    private PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private AuthorityRepository authRepo;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void init() {
        User user = new User(existentUser, "testName", "testLastName", 123L, encoder().encode("pass"));
        assertThat(userRepo).isNotNull();
        when(userRepo.findByEmail(nonexistentUser)).thenReturn(Optional.empty());
        when(userRepo.findByEmail(existentUser)).thenReturn(Optional.of(user));
    }

    @Test
    public void create_user_invalid_email_throws() {
        UserServiceError e = assertThrows(UserServiceError.class, () -> userService.addNewUser(null, "a", "b", "pass", Role.ROLE_USER, 123L));
        assertThat(e.errorCode).isEqualTo(UserServiceError.ERROR_CODE.INVALID_EMAIL);
        e = assertThrows(UserServiceError.class, () -> userService.addNewUser("", "a", "b", "pass", Role.ROLE_USER, 123L));
        assertThat(e.errorCode).isEqualTo(UserServiceError.ERROR_CODE.INVALID_EMAIL);
    }

    @Test
    public void create_user_invalid_password_throws() {
        UserServiceError e = assertThrows(UserServiceError.class, () -> userService.addNewUser(existentUser, "a", "b", "", Role.ROLE_USER, 123L));
        assertThat(e.errorCode).isEqualTo(UserServiceError.ERROR_CODE.INVALID_PASSWORD);
        e = assertThrows(UserServiceError.class, () -> userService.addNewUser(existentUser, "a", "b", null, Role.ROLE_USER, 123L));
        assertThat(e.errorCode).isEqualTo(UserServiceError.ERROR_CODE.INVALID_PASSWORD);
    }

    @Test
    public void create_user_null_ssn_throws() {
        UserServiceError e = assertThrows(UserServiceError.class, () -> userService.addNewUser(existentUser, "a", "b", "pass", Role.ROLE_USER, null));
        assertThat(e.errorCode).isEqualTo(UserServiceError.ERROR_CODE.INVALID_SSN);
    }

    @Test
    public void create_user_invalid_names_throws() {
        UserServiceError e = assertThrows(UserServiceError.class, () -> userService.addNewUser(existentUser, "", "b", "pass", Role.ROLE_USER, 123L));
        assertThat(e.errorCode).isEqualTo(UserServiceError.ERROR_CODE.INVALID_FIRST_NAME);
        e = assertThrows(UserServiceError.class, () -> userService.addNewUser(existentUser, null, "b", "pass", Role.ROLE_USER, 123L));
        assertThat(e.errorCode).isEqualTo(UserServiceError.ERROR_CODE.INVALID_FIRST_NAME);
        e = assertThrows(UserServiceError.class, () -> userService.addNewUser(existentUser, "a", "", "pass", Role.ROLE_USER, 123L));
        assertThat(e.errorCode).isEqualTo(UserServiceError.ERROR_CODE.INVALID_LAST_NAME);
        e = assertThrows(UserServiceError.class, () -> userService.addNewUser(existentUser, "a", null, "pass", Role.ROLE_USER, 123L));
        assertThat(e.errorCode).isEqualTo(UserServiceError.ERROR_CODE.INVALID_LAST_NAME);
    }

    @Test
    public void create_conflicting_user_throws() {
        UserServiceError e = assertThrows(UserServiceError.class, () -> userService.addNewUser(existentUser, "a", "b", "pass", Role.ROLE_USER, 123L));
        assertThat(e.errorCode).isEqualTo(UserServiceError.ERROR_CODE.CONFLICTING_USER);
    }

    @Test
    public void create_nonexistent_valid_user() throws Exception {
        assertDoesNotThrow(() ->userService.addNewUser(nonexistentUser, "a", "b", "pass", Role.ROLE_USER, 123L));
    }
}
