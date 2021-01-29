package com.iv1201.project.recruitment.web;

import com.iv1201.project.recruitment.persistence.*;
import com.iv1201.project.recruitment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import java.util.Locale;
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
    MessageSource messageSource;

    @Autowired
    UserService userService;

    @Autowired
    private CompetenceRepository competenceRepo;

    private User user;

    @GetMapping("/apply")
    public String startApplication(Locale locale, @ModelAttribute("competenceFormObject") CompetenceForm competenceForm, Principal principal, Model model) {
        System.out.println(locale);

        Optional<User> userMaybe = userService.findByEmail(principal.getName());
        if(!userMaybe.isPresent())
            throw new RuntimeException("Expected a user to exist on protected endpoint");

        user = userMaybe.get();
        model.addAttribute("form", "competence");
        model.addAttribute("user", user);
        model.addAttribute("availableExpertises", competenceRepo.findAll());
        return "applicationView";
    }

    @PostMapping("/apply/expertise")
    public String fetchCompetenceForm(@Valid @ModelAttribute("competenceFormObject") CompetenceForm competenceForm, BindingResult bindingResult, Model model) {
        if(!bindingResult.hasErrors()) {
            if(!competenceRepo.findByName(competenceForm.getName()).isPresent())
                throw new RuntimeException("Expected the supplied competence to exist in the database.");
            user.addCompetence(competenceRepo.findByName(competenceForm.getName()).get(), competenceForm.getYears());
        }
        if(checkForLastFetch(user)) {
            model.addAttribute("last", true);
        }

        model.addAttribute("user", user);
        model.addAttribute("form", "competence");
        model.addAttribute("availableExpertises", competenceRepo.findAll());
        return "applicationView";
    }

    private boolean checkForLastFetch(User user) {
        return user.getCompetences().size() == competenceRepo.count();
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

    @PostMapping("/apply/submit")
    public String submittedApplication(Model model) {
        user = userService.saveUser(user);
        model.addAttribute("user", user);
        model.addAttribute("form", "submitted");
        return "applicationView";
    }
}
