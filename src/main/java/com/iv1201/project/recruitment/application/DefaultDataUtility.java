package com.iv1201.project.recruitment.application;

import com.iv1201.project.recruitment.application.exceptions.UserServiceError;
import com.iv1201.project.recruitment.domain.Competence;
import com.iv1201.project.recruitment.domain.Language;
import com.iv1201.project.recruitment.domain.User;
import com.iv1201.project.recruitment.domain.unmigratedData.UnmigratedAvailability;
import com.iv1201.project.recruitment.domain.unmigratedData.UnmigratedCompetenceProfile;
import com.iv1201.project.recruitment.domain.unmigratedData.UnmigratedPerson;
import com.iv1201.project.recruitment.repository.CompetenceRepository;
import com.iv1201.project.recruitment.repository.LanguageRepository;
import com.iv1201.project.recruitment.repository.UserRepository;
import com.iv1201.project.recruitment.repository.unmigratedData.UnmigratedAvailabilityRepository;
import com.iv1201.project.recruitment.repository.unmigratedData.UnmigratedCompetenceProfileRepository;
import com.iv1201.project.recruitment.repository.unmigratedData.UnmigratedPersonRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

class DefaultDataUtility {
    private static final String unmigratedUserEmail = "anonymous@kth.se";

    protected static void addDefaultData(
            UserService userService,
            UserRepository userRepo,
            LanguageRepository languageRepo,
            CompetenceRepository competenceRepo,
            UnmigratedPersonRepository umPersonRepo,
            UnmigratedCompetenceProfileRepository umCompetenceProfileRepo,
            UnmigratedAvailabilityRepository umAvailabilityRepo
            ) {
        try {
            if (!userRepo.existsByEmailIgnoreCase("testuser@example.com")) {
                System.err.println("Saving test user!");
                User user = userService.addNewUser("testuser@example.com",
                        "userFirstName",
                        "userLastName",
                        "pass",
                        UserService.Role.ROLE_USER,
                        "19880101");
                user.addAvailability(LocalDate.of(2022, 2, 2), LocalDate.of(2028, 2, 2));
                userRepo.save(user);
            }

            if (!userRepo.existsByEmailIgnoreCase("restoretest@example.com")) {
                System.err.println("Saving restore user!");
                User user = userService.addNewUser("restoretest@example.com",
                        "restoreFirstName",
                        "restoreLastName",
                        "pass",
                        UserService.Role.ROLE_USER,
                        "19880102");
                userRepo.save(user);
            }

            if (!userRepo.existsByEmailIgnoreCase("testadmin@example.com")) {
                System.err.println("Saving test admin!");
                userService.addNewUser("testadmin@example.com",
                        "adminFirstName",
                        "adminLastName",
                        "pass",
                        UserService.Role.ROLE_ADMIN,
                        "19890103");
            }

        } catch (UserServiceError e) {
            System.err.println("Error creating test users " + e.errorCode);
        }

        if (languageRepo.count() == 0) {
            Language swedish = languageRepo.save(new Language("sv_SE", "svenska"));
            Language english = languageRepo.save(new Language("en_US", "english"));
            Competence grilling = competenceRepo.save(Competence.create());
            Competence carousel = competenceRepo.save(Competence.create());
            grilling.addTranslation(swedish, "Korvgrillning");
            grilling.addTranslation(english, "Grilling sausage");
            carousel.addTranslation(swedish, "Karuselldrift");
            carousel.addTranslation(english, "Carousel operation");

            //Save them
            Competence[] competences = {
                    grilling,
                    carousel
            };

            for (Competence c : competences) {
                competenceRepo.save(c);
            }
        }

        if(!umPersonRepo.existsByEmailIgnoreCase(unmigratedUserEmail)) {
            UnmigratedPerson up = new UnmigratedPerson(0L, 2L, "username1", unmigratedUserEmail, "firstname",
                    "lastname", null, "1234");
            up = umPersonRepo.save(up);
            UnmigratedCompetenceProfile umComp = new UnmigratedCompetenceProfile(
                    0L, 0.5, "Karuselldrift", up.getPersonId());
            umCompetenceProfileRepo.save(umComp);
            UnmigratedAvailability umAvail = new UnmigratedAvailability(
                    0L, up.getPersonId(), LocalDate.of(2020, 1, 1), LocalDate.of(2022, 1, 1));
            umAvailabilityRepo.save(umAvail);
        }
    }
}

