package com.iv1201.project.recruitment.service;

import com.iv1201.project.recruitment.persistence.*;
import com.iv1201.project.recruitment.web.ListForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Search {

    @Autowired
    UserRepository userRepo;

    @Autowired
    AvailabilityRepository availabilityRepo;

    public List<ApplicationDTO> getApplications(ListForm input, Iterable<Competence> competences) throws SearchError {

            int competenceId = setCompetenceId(input.getCompetence(), competences);
            String[] names = setNames(input.getFirstName(), input.getLastName());
            List<ApplicationDTO> searchResult = new ArrayList<>();
            switch (handleInput(input)) {
                case "Availability":
                    searchResult = availabilityRepo.getAvailabilityApplications(input.getAvailabilityForm().getFrom(), input.getAvailabilityForm().getTo(),names[0], names[1], competenceId);
                    break;
                case "User":
                    searchResult = userRepo.getUserApplications(names[0], names[1], competenceId);
                    break;
                case "Empty":
                    System.err.println("Empty");
                    break;
                default:
                    throw new SearchError("Invalid applications search!");
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
