package com.iv1201.project.recruitment.service;

import com.iv1201.project.recruitment.persistence.ApplicationDTO;
import com.iv1201.project.recruitment.persistence.UserRepository;
import com.iv1201.project.recruitment.web.ListForm;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


public class Search {

    @Autowired
    private UserRepository userRepo;


    public List<ApplicationDTO> getApplications(ListForm input) throws SearchError {

        try {
            switch (handleInput(input)) {
                case "Period":
                    System.err.println("Period");
                    break;
                case "Name":
                    System.err.println("Name");
                    break;
                case "Competence":
                    System.err.println("Competence");
                    break;
                default:
                    throw new SearchError("No Search Fields were entered!");
            }

        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return new ArrayList<ApplicationDTO>();
    }

    private String handleInput(ListForm input) throws IllegalArgumentException {

        if(input.getAvailabilityForm().getFrom() != null)
            return "Period";

        if(!input.getCompetence().isEmpty())
            return "Competence";

        if(!(input.getFirstName().isEmpty() && input.getLastName().isEmpty()))
            return "Name";

       throw new IllegalArgumentException();
    }

}
