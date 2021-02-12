package com.iv1201.project.recruitment.application;

import com.iv1201.project.recruitment.application.exceptions.SearchServiceError;
import com.iv1201.project.recruitment.domain.ApplicationDTO;
import com.iv1201.project.recruitment.domain.Competence;
import com.iv1201.project.recruitment.repository.AvailabilityRepository;
import com.iv1201.project.recruitment.repository.UserRepository;
import com.iv1201.project.recruitment.presentation.forms.ListForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Handles searches for and returns applications stored in the "Recruitment Applications" database.
 */
@Service
public class SearchService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    AvailabilityRepository availabilityRepo;

    /**
     * Called when a user has initiated a search database via the listView interface. Extracts the
     * parameters stored in the received <>ListForm</> object, parses them and set them to values
     * that can be searched for in the database.
     *
     * @param input is the <>ListForm</> object containing the users entered parameters of interest.
     * @param competences contains all stored competences in the database.
     * @return is the found applications in the form of a list of <>ApplicationDTO</>.
     * @throws SearchServiceError is thrown if there has been an issue with the search.
     */
    public List<ApplicationDTO> getApplications(ListForm input, Iterable<Competence> competences) throws SearchServiceError {

            int competenceId = setCompetenceId(input.getCompetence(), competences);
            String[] names = setNames(input.getFirstName(), input.getLastName());
            List<ApplicationDTO> searchResult;

            switch (handleInput(input)) {
                case "Availability":
                    searchResult = availabilityRepo.getAvailabilityApplications(input.getAvailabilityForm().getFrom(), input.getAvailabilityForm().getTo(),names[0], names[1], competenceId);
                    break;
                case "User":
                    searchResult = userRepo.getUserApplications(names[0], names[1], competenceId);
                    break;
                case "Empty":
                    throw new SearchServiceError("The entered listForm object was empty");
                default:
                    throw new SearchServiceError("Invalid applications search!");
            }

        return searchResult;
    }

    private String handleInput(ListForm input) {

        if(input.getAvailabilityForm().getFrom() != null)
            return "Availability";
        else if(!input.getCompetence().isEmpty() || !(input.getFirstName().isEmpty() && input.getLastName().isEmpty()))
            return "User";
        else
            return "Empty";
    }

    private String[] setNames(String firstName, String lastName){

        String[] names = new String[2];

        if (!firstName.isEmpty())
            names[0] = firstName;

        if(!lastName.isEmpty())
            names[1] = lastName;

        return names;
    }

    private int setCompetenceId(String competence, Iterable<Competence> competences){

        int id = 1;

        for (Competence c: competences) {
            if (c.getName().equals(competence))
                return id;
            id++;
        }
        return 0;
    }
}
