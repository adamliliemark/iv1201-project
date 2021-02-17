package com.iv1201.project.recruitment;

import com.iv1201.project.recruitment.domain.ApplicationDTO;
import com.iv1201.project.recruitment.domain.Competence;
import com.iv1201.project.recruitment.domain.User;
import com.iv1201.project.recruitment.repository.CompetenceRepository;
import com.iv1201.project.recruitment.repository.UserRepository;
import com.iv1201.project.recruitment.application.SearchService;
import com.iv1201.project.recruitment.presentation.forms.AvailabilityForm;
import com.iv1201.project.recruitment.presentation.forms.ListForm;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SearchTest {
    private final String user1Email = "user1Email", user2Email = "user2Email";
    private final String user1FirstName = "user1Name", user2FirstName = "user2Email";
    private final String user1LastName = "user1LastName", user2LastName = "user2LastName";
    private final String user1SSN = "890102", user2SSN = "900101";
    private List<Competence> competences;
    private ListForm listFormMock;
    private AvailabilityForm avForm;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SearchService search;

    @Autowired
    private CompetenceRepository competenceRepo;

    @BeforeAll
    public void beforeAll() {
        //There are default users added by userservice. This should be removed at some point
        //But until then these competences are used for tests
        competences = new ArrayList<>();
        competenceRepo.findAll().forEach(competences::add);
        User user1 = new User(user1Email, user1FirstName, user1LastName, user1SSN, "pass");
        User user2 = new User(user2Email, user2FirstName, user2LastName, user2SSN, "pass");
        userRepo.save(user1);
        userRepo.save(user2);

        user1 = userRepo.findByEmailIgnoreCase(user1Email).get();
        user2 = userRepo.findByEmailIgnoreCase(user2Email).get();

        user1.addCompetence(competences.get(0), 1.0);
        user1.addAvailability(
                LocalDate.of(2021, 1, 1),
                LocalDate.of(2021, 12,1));

        user2.addCompetence(competences.get(0), 1.0);
        user2.addAvailability(
                LocalDate.of(2021, 6, 1),
                LocalDate.of(2021, 12,1));

        userRepo.save(user1);
        userRepo.save(user2);
    }

    @BeforeEach
    public void each() {
        listFormMock = Mockito.mock(ListForm.class, Mockito.CALLS_REAL_METHODS);
        avForm = new AvailabilityForm();
        listFormMock.setAvailabilityForm(avForm);
        listFormMock.setCompetence("");
        listFormMock.setFirstName("");
        listFormMock.setLastName("");
    }

    @Test
    public void listing_empty_applications_should_be_empty_list() throws Exception {
        avForm.setFrom(LocalDate.of(2020, 1, 1));
        avForm.setFrom(LocalDate.of(2020, 1, 2));
        listFormMock.setAvailabilityForm(avForm);
        listFormMock.setFirstName(user1FirstName);
        listFormMock.setLastName(user1LastName);
        List<ApplicationDTO> searchResult = search.getApplications(listFormMock, competences);
        assertThat(searchResult.size()).isEqualTo(0);
    }

    @Test
    public void listing_applications_on_range_should_be_correct() throws Exception {
        avForm.setFrom(LocalDate.of(2021, 2, 1));
        avForm.setTo(LocalDate.of(2021, 2, 22));
        List<ApplicationDTO> searchResult = search.getApplications(listFormMock, competences);
        assertThat(searchResult.size()).isEqualTo(1);
        assertThat(searchResult.get(0).getFirstName()).isEqualTo(user1FirstName);
        assertThat(searchResult.get(0).getLastName()).isEqualTo(user1LastName);

        avForm.setFrom(LocalDate.of(2021, 6, 2));
        searchResult = search.getApplications(listFormMock, competences);
        assertThat(searchResult.size()).isEqualTo(2);
        assertThat(searchResult).anyMatch(app -> app.getFirstName().equals(user1FirstName) && app.getLastName().equals(user1LastName));
        assertThat(searchResult).anyMatch(app -> app.getFirstName().equals(user2FirstName) && app.getLastName().equals(user2LastName));

        avForm.setTo(LocalDate.of(2022, 6, 2));
        searchResult = search.getApplications(listFormMock, competences);
        assertThat(searchResult.size()).isEqualTo(0);

        avForm.setTo(LocalDate.of(2021, 6, 2));
        avForm.setFrom(LocalDate.of(2020, 12, 1));
        searchResult = search.getApplications(listFormMock, competences);
        assertThat(searchResult.size()).isEqualTo(0);
    }

    @Test
    public void listing_applications_on_name_should_be_correct() throws Exception {
        listFormMock.setAvailabilityForm(avForm);
        listFormMock.setFirstName(user1FirstName);
        listFormMock.setLastName(user1LastName);
        List<ApplicationDTO> searchResult = search.getApplications(listFormMock, competences);
        assertThat(searchResult.size()).isEqualTo(1);
        assertThat(searchResult.get(0).getFirstName()).isEqualTo(user1FirstName);
        assertThat(searchResult.get(0).getLastName()).isEqualTo(user1LastName);

        listFormMock.setLastName(user1LastName+1);
        searchResult = search.getApplications(listFormMock, competences);
        assertThat(searchResult.size()).isEqualTo(0);

        listFormMock.setFirstName(user1FirstName+1);
        searchResult = search.getApplications(listFormMock, competences);
        assertThat(searchResult.size()).isEqualTo(0);
    }

    @Test
    public void listing_applications_on_name_and_range_should_be_correct() throws Exception {
        listFormMock.setAvailabilityForm(avForm);
        listFormMock.setFirstName(user1FirstName);
        listFormMock.setLastName(user1LastName);
        avForm.setFrom(LocalDate.of(2021, 6, 2));
        avForm.setTo(LocalDate.of(2021, 7, 2));

        List<ApplicationDTO> searchResult = search.getApplications(listFormMock, competences);
        assertThat(searchResult.size()).isEqualTo(1);
        assertThat(searchResult.get(0).getFirstName()).isEqualTo(user1FirstName);
        assertThat(searchResult.get(0).getLastName()).isEqualTo(user1LastName);

        listFormMock.setLastName(user1LastName+1);
        searchResult = search.getApplications(listFormMock, competences);
        assertThat(searchResult.size()).isEqualTo(0);

        listFormMock.setLastName(user1LastName);
        avForm.setTo(LocalDate.of(2022,01,01));
        searchResult = search.getApplications(listFormMock, competences);
        assertThat(searchResult.size()).isEqualTo(0);
    }

    @Test
    public void search_should_be_case_agnostic() throws Exception {
        listFormMock.setAvailabilityForm(avForm);
        listFormMock.setFirstName(user1FirstName.toLowerCase(Locale.ROOT));
        listFormMock.setLastName(user1LastName.toUpperCase(Locale.ROOT));
        List<ApplicationDTO> searchResult = search.getApplications(listFormMock, competences);
        assertThat(searchResult.size()).isEqualTo(1);
        assertThat(searchResult.get(0).getFirstName()).isEqualTo(user1FirstName);
        assertThat(searchResult.get(0).getLastName()).isEqualTo(user1LastName);
    }
}
