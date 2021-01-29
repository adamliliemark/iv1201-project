package com.iv1201.project.recruitment.web;

import com.iv1201.project.recruitment.persistence.*;
import com.iv1201.project.recruitment.service.CompetenceService;
import com.iv1201.project.recruitment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import com.iv1201.project.recruitment.persistence.Availability;
import com.iv1201.project.recruitment.persistence.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

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
    private CompetenceService competenceService;

    private User user;
    private Map<String, Competence> competences;

    @GetMapping("/apply")
    public String startApplication(@ModelAttribute("competenceFormObject") CompetenceForm competenceForm, Principal principal, Model model) {
        Optional<User> userMaybe = userService.findByEmail(principal.getName());
        if(!userMaybe.isPresent())
            throw new RuntimeException("Expected a user to exist on protected endpoint");
        competences = competenceService.getAllWithLocalNames("en_US");

        user = userMaybe.get();
        model.addAttribute("form", "competence");
        model.addAttribute("user", user);
        model.addAttribute("availableExpertises", competences.keySet());
        return "applicationView";
    }

    @PostMapping("/apply/expertise")
    public String fetchCompetenceForm(@Valid @ModelAttribute("competenceFormObject") CompetenceForm competenceForm, BindingResult bindingResult, Model model) {
        if(!bindingResult.hasErrors()) {
            if(!competences.containsKey(competenceForm.getName()))
                throw new RuntimeException("Expected the supplied competence to exist in the database.");
            user.addCompetence(competences.get(competenceForm.getName()), competenceForm.getYears());
        }
        if(checkForLastFetch(user)) {
            model.addAttribute("last", true);
        }
        //This saves the user for testing, should be done later
        //user = userService.saveUser(user);
        model.addAttribute("user", user);
        model.addAttribute("form", "competence");
        model.addAttribute("availableExpertises", competences.keySet());
        return "applicationView";
    }

    private boolean checkForLastFetch(User user) {
        return user.getCompetences().size() == competences.size();
    }

    @PostMapping("/apply/availability")
    public String fetchAvailabilityForm(@Valid @ModelAttribute("availabilityFormObject") AvailabilityForm availabilityForm, BindingResult bindingResult, Model model) {
        if(!bindingResult.hasErrors()) {
            user.setAvailability(new Availability(availabilityForm.getFrom(), availabilityForm.getTo()));
        }
        model.addAttribute("user", user);
        model.addAttribute("form", "availability");
        return "applicationView";
    }

    /**
     * Final page of an application. Reviews the user's application.
     * @param model for Thymeleaf
     */
    @PostMapping("/apply/review")
    public String reviewApplication(Model model) {
        model.addAttribute("user", user);
        model.addAttribute("form", "review");
        return "applicationView";
    }
}
