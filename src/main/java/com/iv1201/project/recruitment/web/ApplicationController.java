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
    private UserService userService;

    @Autowired
    private CompetenceService competenceService;

    private User user;
    private Map<String, Competence> competences;

    /**
     * startApplication is the starting point of an application.
     * @param locale the user's locale which determines the translation table in the database
     * @param competenceFormObject the object that fetches the competenceForm
     * @param principal the logged in user
     * @param model for thymeleaf
     * @return applicationView with competence form
     */
    @GetMapping("/apply")
    public String startApplication(Locale locale, @ModelAttribute("competenceFormObject") CompetenceForm competenceFormObject, Principal principal, Model model) {
        Optional<User> userMaybe = userService.findByEmail(principal.getName());
        if(!userMaybe.isPresent()) {
            throw new RuntimeException("Expected a user to exist on protected endpoint");
        }
        competences = competenceService.getAllWithLocalNames(locale.toString());
        user = userMaybe.get();
        model.addAttribute("form", "competence");
        model.addAttribute("user", user);
        model.addAttribute("availableExpertises", competences.keySet());
        return "applicationView";
    }

    /**
     * Mapping to fetch the competence form.
     * @param competenceFormObject the filled out form from thymeleaf
     * @param bindingResult validation of the form
     * @param model for thymeleaf
     * @return applicationView with competence form
     */
    @PostMapping("/apply/expertise")
    public String fetchCompetenceForm(@Valid @ModelAttribute("competenceFormObject") CompetenceForm competenceFormObject, BindingResult bindingResult, Model model) {
        if(!bindingResult.hasErrors()) {
            if(!competences.containsKey(competenceFormObject.getName())) {
                throw new RuntimeException("Expected the supplied competence to exist in the database.");
            }
            user.addCompetence(competences.get(competenceFormObject.getName()), competenceFormObject.getYears());
        }
        if(checkForLastFetch(user)) {
            model.addAttribute("last", true);
        }
        model.addAttribute("user", user);
        model.addAttribute("form", "competence");
        model.addAttribute("availableExpertises", competences.keySet());
        return "applicationView";
    }

    private boolean checkForLastFetch(User user) {
        return user.getCompetences().size() == competences.size();
    }

    /**
     * Mapping to fetch the availability form.
     * @param availabilityFormObject the filled out form from thymeleaf
     * @param bindingResult validation of the form
     * @param model for thymeleaf
     * @return applicationView with availability form
     */
    @PostMapping("/apply/availability")
    public String fetchAvailabilityForm(@Valid @ModelAttribute("availabilityFormObject") AvailabilityForm availabilityFormObject, BindingResult bindingResult, Model model) {
        if(!bindingResult.hasErrors()) {
            user.setAvailability(new Availability(availabilityFormObject.getFrom(), availabilityFormObject.getTo()));
        }
        model.addAttribute("user", user);
        model.addAttribute("form", "availability");
        return "applicationView";
    }

    /**
     * Delivers the review page.
     * @param model for Thymeleaf
     * @return applicationView with review form
     */
    @PostMapping("/apply/review")
    public String reviewApplication(Model model) {
        model.addAttribute("user", user);
        model.addAttribute("form", "review");
        return "applicationView";
    }

    /**
     * Saves the application and redirects the user back to their start page.
     * @param model for thymeleaf
     * @return back to start page
     */
    @PostMapping("/apply/submit")
    public String submittedApplication(Model model) {
        user = userService.saveUser(user);
        model.addAttribute("user", user);
        model.addAttribute("form", "submitted");
        return "redirect:/";
    }
}
