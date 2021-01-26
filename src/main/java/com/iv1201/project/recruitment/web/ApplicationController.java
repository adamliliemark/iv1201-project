package com.iv1201.project.recruitment.web;

import com.iv1201.project.recruitment.model.Expertise;
import com.iv1201.project.recruitment.persistence.*;
import com.iv1201.project.recruitment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Optional;


/**
 * ApplicationController is the controller for when an
 * applicant makes an application. All mappings return the
 * same view but modify it with model attributes, hence
 * no return values are explained in the JavaDoc.
 */
@Controller
@Scope("session")
public class ApplicationController {

    @Autowired
    UserService userService;

    @Autowired
    private CompetenceRepository competenceRepo;

    private User user;

    @GetMapping("/apply")
    public String startApplication(Principal principal, Model model) {
        Optional<User> userMaybe = userService.findByEmail(principal.getName());

        if(!userMaybe.isPresent())
            throw new RuntimeException("Expected a user to exist on protected endpoint");

        user = userMaybe.get();
        model.addAttribute("form", "expertise");
        model.addAttribute("user", user);
        model.addAttribute("expertise", new Expertise());
        model.addAttribute("availableExpertises", competenceRepo.findAll());
        return "applicationView";
    }

    /**
     * A mapping to fetch the data from the expertiseForm fragment.
     * @param expertise the expertise to fetch
     * @param principal the logged in user
     * @param model for Thymeleaf
     */
    @PostMapping("/apply/expertise")
    public String fetchExpertise(@ModelAttribute Expertise expertise, Principal principal, Model model) {
        Optional<Competence> addedCompetence = competenceRepo.findByName(expertise.getExpertise());
        if(!addedCompetence.isPresent())
            throw new RuntimeException("Nonexistant competence added");
        user.addCompetence(addedCompetence.get(), expertise.getYears());

        //This saves the user and commits the competences.
        //This should be moved to a later stage, but here for testing
        //userService.saveUser(user);

        if(checkForLastFetch(user)) {
            model.addAttribute("last", true);
        }

        model.addAttribute("user", user);
        model.addAttribute("form", "expertise");
        model.addAttribute("availableExpertises", competenceRepo.findAll());
        return "applicationView";
    }

    private boolean checkForLastFetch(User user) {
        return user.getCompetences().size() == competenceRepo.count();
    }

    /**
     * A mapping to fetch the data from the availabilityForm fragment.
     * @param availability the availability (dates) to fetch
     * @param principal the logged in user
     * @param model for Thymeleaf
     */
    @PostMapping("/apply/availability")
    public String fetchAvailability(@ModelAttribute Availability availability, Principal principal, Model model) {
        user.setAvailability(availability);
        model.addAttribute("user", user);
        model.addAttribute("form", "availability");
        return "applicationView";
    }

    /**
     * Final page of an application. Reviews the user's application.
     * @param principal the logged in user
     * @param model for Thymeleaf
     */
    @PostMapping("/apply/review")
    public String reviewApplication(Principal principal, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("form", "review");
        return "applicationView";
    }
}
