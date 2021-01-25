package com.iv1201.project.recruitment.web;

import com.iv1201.project.recruitment.model.AvailableExpertises;
import com.iv1201.project.recruitment.model.Expertise;
import com.iv1201.project.recruitment.model.LiveUser;
import com.iv1201.project.recruitment.persistence.Availability;
import com.iv1201.project.recruitment.persistence.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


/**
 * ApplicationController is the controller for when an
 * applicant makes an application. All mappings return the
 * same view but modify it with model attributes, hence
 * no return values are explained in the JavaDoc.
 */
@Controller
public class ApplicationController {
    private final Map<String, LiveUser> users = new HashMap<>();
    private final AvailableExpertises availableExpertises = new AvailableExpertises();

    /**
     * The starting point of a user's application.
     * @param liveUser the local user kept in cache
     * @param expertise a data holder for the expertise form
     * @param availability a data holder for the availability form
     * @param principalUser the logged in user
     * @param model the model that Thymeleaf uses
     */
    @GetMapping("/apply")
    public String applyForPosition(@ModelAttribute LiveUser liveUser, @ModelAttribute Expertise expertise, @ModelAttribute Availability availability, Principal principalUser, Model model) {
        liveUser.setUser(new User(principalUser.getName(), "john", "doe", 192830L, "pass"));
        users.put(principalUser.getName(), liveUser);
        model.addAttribute("form", "expertise");
        model.addAttribute("user", liveUser);
        model.addAttribute("expertise", expertise);
        model.addAttribute("availableExpertises", availableExpertises.getAvailableExpertises());
        return "applicationView";
    }

    /**
     * A mapping to fetch the data from the expertiseForm fragment.
     * @param expertise the expertise to fetch
     * @param principalUser the logged in user
     * @param model for Thymeleaf
     */
    @PostMapping("/apply/expertise")
    public String fetchExpertise(@ModelAttribute Expertise expertise, Principal principalUser, Model model) {
        LiveUser liveUser = users.get(principalUser.getName());
        liveUser.mapCompetenceYearCombo(expertise.getExpertise(), expertise.getYears());
        if(liveUser.getCompetences().size() == availableExpertises.getAvailableExpertises().size()) {
            model.addAttribute("last", true);
        }
        model.addAttribute("user", liveUser);
        model.addAttribute("form", "expertise");
        model.addAttribute("availableExpertises", availableExpertises.getAvailableExpertises());
        return "applicationView";
    }

    /**
     * A mapping to fetch the data from the availabilityForm fragment.
     * @param availability the availability (dates) to fetch
     * @param principalUser the logged in user
     * @param model for Thymeleaf
     */
    @PostMapping("/apply/availability")
    public String fetchAvailability(@ModelAttribute Availability availability, Principal principalUser, Model model) {
        LiveUser liveUser = users.get(principalUser.getName());
        liveUser.setAvailability(new Availability(availability.getFromDate(), availability.getToDate()));
        users.get(principalUser
                .getName())
                .setAvailability(availability);
        model.addAttribute("user", liveUser);
        model.addAttribute("form", "availability");
        return "applicationView";
    }

    /**
     * Final page of an application. Reviews the user's application.
     * @param principalUser the logged in user
     * @param model for Thymeleaf
     */
    @PostMapping("/apply/review")
    public String reviewApplication(Principal principalUser, Model model) {
        model.addAttribute("user", users.get(principalUser.getName()));
        model.addAttribute("form", "review");
        return "applicationView";
    }
}
