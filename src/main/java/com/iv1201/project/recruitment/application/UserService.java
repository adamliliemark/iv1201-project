package com.iv1201.project.recruitment.application;

import com.iv1201.project.recruitment.application.exceptions.UserServiceError;
import com.iv1201.project.recruitment.config.MockBeanConfig;
import com.iv1201.project.recruitment.domain.*;
import com.iv1201.project.recruitment.domain.unmigratedData.UnmigratedPerson;
import com.iv1201.project.recruitment.repository.*;
import com.iv1201.project.recruitment.application.exceptions.UserServiceError.ERROR_CODE;
import com.iv1201.project.recruitment.repository.unmigratedData.UnmigratedAvailabilityRepository;
import com.iv1201.project.recruitment.repository.unmigratedData.UnmigratedCompetenceProfileRepository;
import com.iv1201.project.recruitment.repository.unmigratedData.UnmigratedPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

/**
 * A service class for handling database calls concerning <>User</> entities.
 */
@Component
@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    public static final String HARDCODED_RESET_PASSWORD = "new_password";
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
    private MockBeanConfig.EmailDispatcher emailDispatcher;

    @Autowired
    private CompetenceRepository competenceRepo;

    @Autowired
    private UnmigratedPersonRepository unmigratedPersonRepo;

    @Autowired
    private UnmigratedCompetenceProfileRepository unmigratedCompRepo;

    @Autowired
    private UnmigratedAvailabilityRepository unmigratedAvailabilityRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AuthorityRepository authorityRepo;

    @Autowired
    private LanguageRepository languageRepo;

    @Autowired
    private CompetenceTranslationRepository translationRepo;

    @Autowired
    private AvailabilityRepository availabilityRepository;

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
    public User addNewUser(String email, String firstName, String lastName, String clearTextPassword, Role role, String ssn) throws UserServiceError {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        validateUser(email, firstName, lastName, clearTextPassword, ssn);

        if(userRepo.existsByEmailIgnoreCase(email))
            throw new UserServiceError(ERROR_CODE.CONFLICTING_USER);

        User user = new User(email, firstName, lastName, ssn, encoder.encode(clearTextPassword));
        Authority userAuth = new Authority(role.toString(), user);
        userRepo.save(user);
        authorityRepo.save(userAuth);
        return user;
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
     * Saves an availability to a user if the user already does
     * not already have an availability with the same dates.
     * @param user to thom the availability is saved
     * @param from availability start date
     * @param to availability end date
     */
    @Transactional
    public void saveAvailabilityToUser(User user, LocalDate from, LocalDate to) throws UserServiceError {
        Availability av = new Availability(from, to, user);
        if(!user.getAvailabilityList().contains(av)) {
            user.addAvailability(av);
        } else {
            throw new UserServiceError(ERROR_CODE.CONFLICT_AVAILABIITY);
        }
    }

    /**
     * Saves a competence to a user.
     * @param user to whom the competence is saved
     * @param competence to save
     * @param years of experience
     */
    public void saveCompetenceToUser(User user, Competence competence, Double years) {
        user.addCompetence(competence, years);
    }

    /**
     * Saves a locale to a user.
     * @param user to whom the locale is saved
     * @param locale to save
     */
    public void saveLocaleToUser(User user, Locale locale) {
        user.setLocale(locale);
    }

    /**
     * Restores an incomplete user, identified by an email address.
     * Calling this method will, if possible move the user and its
     * associated availabilities and competence profiles to the appropriate
     * tables.
     *
     * This method does not throw if no user is found, since this would leak
     * data about what users are in the database.
     * @param email the email of the incomplete user
     * @throws UserServiceError the first validational error that was encoutnered
     */
    @Transactional
    public void restoreUnmigratedPerson(String email) throws UserServiceError {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<UnmigratedPerson> maybeUp = unmigratedPersonRepo.findByEmailIgnoreCase(email);
        if(!maybeUp.isPresent()) {
            LOGGER.trace("Could not restore user, reason: NONEXISTENT '" + email + "'");
            return;
        }
        UnmigratedPerson up = maybeUp.get();

        if(userRepo.existsByEmailIgnoreCase(up.getEmail())) {
            LOGGER.trace("Could not restore user, reason: CONFLICT '" + email + "'");
            throw new UserServiceError(ERROR_CODE.CONFLICTING_USER);
        }

        User newUser = new User(up);
        newUser.setPassword(encoder.encode(HARDCODED_RESET_PASSWORD));
        try {
            this.validateUser(newUser, HARDCODED_RESET_PASSWORD);

            //We silently ignore nonexistent competences
            unmigratedCompRepo
            .findAllBypersonId(up.getPersonId())
            .forEach(umComp ->
                translationRepo.findByText(umComp.getCompetenceName())
                .map(CompetenceTranslation::getCompetence)
                .ifPresentOrElse(c -> {
                        LOGGER.trace("\tTransfering competence from old to new User:\n" + umComp);
                        newUser.addCompetence(c, umComp.getYearsOfExperience());
                        unmigratedCompRepo.delete(umComp);
                },               () ->
                        LOGGER.trace("Skipping transfer of nonexistent competence: " + umComp.getCompetenceName())
                )
            );

            unmigratedAvailabilityRepo
            .findAllByPersonId(up.getPersonId())
            .forEach(umAvail -> {
                    LOGGER.trace("\tTransfering availability from old to new User:\n" + umAvail);
                    newUser.addAvailability(umAvail.getFromDate(), umAvail.getToDate());
                    unmigratedAvailabilityRepo.delete(umAvail);
            });
            LOGGER.trace("\tsaving new User.");
            User savedUser = userRepo.save(newUser);
            Role r = up.getRole_id() == 1 ? Role.ROLE_ADMIN : Role.ROLE_USER;
            LOGGER.trace("\tsaving Authority for new User.");
            authorityRepo.save(new Authority(r.toString(), savedUser));
            LOGGER.trace("\tdeleting old UnmigratedUser.");
            unmigratedPersonRepo.delete(up);
        } catch (Exception e) {
            LOGGER.error("Could not restore user, reason: EXCEPTION '" + email + "' [reverting transaction]", e);
            //very important to rethrow this error!
            throw e;
        }
        emailDispatcher.sendEmail(email, "Your account has been restored, " +
                "you can login with your email address " +
                "and the password: " + HARDCODED_RESET_PASSWORD + ".");
        LOGGER.trace("successfully restored user '" + email + "'.");
    }

    /**
     * Gets a user from the data store.
     * This method is transactional because of the greedy
     * lookup on linked attributes.
     * @param email the email of the user to get.
     * @return the found user or empty
     */
    @Transactional
    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmailIgnoreCase(email);
    }


    /**
     * Returns true if a matching row exists, else false
     * @param email the email to check by.
     * @return true if the user exists else false
     */
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmailIgnoreCase(email);
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
        Function<String, Boolean> stringRules = str ->
            str != null && !str.isEmpty() && str.trim().length() == str.length();

        if (!stringRules.apply(email))
            throw new UserServiceError(ERROR_CODE.INVALID_EMAIL);
        if (!stringRules.apply(firstName))
            throw new UserServiceError(ERROR_CODE.INVALID_FIRST_NAME);
        if (!stringRules.apply(lastName))
            throw new UserServiceError(ERROR_CODE.INVALID_LAST_NAME);
        if (clearTextPassword == null || clearTextPassword.length() < 3)
            throw new UserServiceError(ERROR_CODE.INVALID_PASSWORD);
        if (!stringRules.apply(ssn))
            throw new UserServiceError(ERROR_CODE.INVALID_SSN);
        return true;
    }

    public boolean validateUser(User user, String cleartextPass) throws UserServiceError {
        return this.validateUser(user.getEmail(), user.getFirstName(), user.getLastName(), cleartextPass, user.getSsn());
    }

    @PostConstruct
    @Transactional
    void addDefaultData() {
        DefaultDataUtility.addDefaultData(
                this,
                userRepo,
                languageRepo,
                competenceRepo,
                unmigratedPersonRepo,
                unmigratedCompRepo,
                unmigratedAvailabilityRepo);
    }
}
