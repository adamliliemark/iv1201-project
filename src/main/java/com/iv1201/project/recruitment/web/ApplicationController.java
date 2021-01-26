package com.iv1201.project.recruitment.web;

import com.iv1201.project.recruitment.model.AvailableExpertises;
import com.iv1201.project.recruitment.model.Expertise;
import com.iv1201.project.recruitment.persistence.Availability;
import com.iv1201.project.recruitment.persistence.CompetenceProfile;
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
    private final AvailableExpertises availableExpertises = new AvailableExpertises();
    private final Map<String, User> users = new HashMap<>();

    @GetMapping("/apply")
    public String test(Principal principal, Model model) {
        User user = new User(principal.getName(), "jonny", "doe", 1020291L, "pass");
        users.put(principal.getName(), user);
        model.addAttribute("form", "expertise");
        model.addAttribute("user", user);
        model.addAttribute("expertise", new Expertise());
        model.addAttribute("availableExpertises", availableExpertises.getAvailableExpertises());
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
        User user = users.get(principal.getName());
        user.addCompetence(expertise.getExpertise(), expertise.getYears());
        if(checkForLastFetch(user)) {
            model.addAttribute("last", true);
        }
        model.addAttribute("user", user);
        model.addAttribute("form", "expertise");
        model.addAttribute("availableExpertises", availableExpertises.getAvailableExpertises());
        return "applicationView";
    }

    private boolean checkForLastFetch(User user) {
        return user.getCompetences().size() == availableExpertises.getAvailableExpertises().size();
    }

    /**
     * A mapping to fetch the data from the availabilityForm fragment.
     * @param availability the availability (dates) to fetch
     * @param principal the logged in user
     * @param model for Thymeleaf
     */
    @PostMapping("/apply/availability")
    public String fetchAvailability(@ModelAttribute Availability availability, Principal principal, Model model) {
        User user = users.get(principal.getName());
        user.setAvailability(availability.getFromDate(), availability.getToDate());
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
        model.addAttribute("user", users.get(principal.getName()));
        model.addAttribute("form", "review");
        return "applicationView";
    }
}
