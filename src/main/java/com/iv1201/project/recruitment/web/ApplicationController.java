package com.iv1201.project.recruitment.web;

import com.iv1201.project.recruitment.model.AvailableExpertises;
import com.iv1201.project.recruitment.model.Expertise;
import com.iv1201.project.recruitment.model.LiveUser;
import com.iv1201.project.recruitment.persistence.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ApplicationController {

    private final Map<String, LiveUser> users = new HashMap<>();
    private final AvailableExpertises availableExpertises = new AvailableExpertises();

    @GetMapping("/apply")
    public String applyForPosition(@ModelAttribute LiveUser liveUser, @ModelAttribute Expertise expertise, Principal principalUser, Model model) {
        liveUser.setUser(new User(principalUser.getName(), "john", "doe", 192830L, "pass"));
        users.put(principalUser.getName(), liveUser);
        model.addAttribute("user", liveUser);
        model.addAttribute("expertise", expertise);
        model.addAttribute("availableExpertises", availableExpertises.getAvailableExpertises());
        return "apply_for_position";
    }

    @PostMapping("/apply")
    public String fetchApplication(@ModelAttribute Expertise expertise, Principal principalUser, Model model) {
        LiveUser liveUser = users.get(principalUser.getName());
        liveUser.mapCompetenceYearCombo(expertise.getExpertise(), expertise.getYears());
        if(liveUser.getCompetences().size() == availableExpertises.getAvailableExpertises().size()) {
            model.addAttribute("last", true);
        }
        model.addAttribute("user", liveUser);
        model.addAttribute("availableExpertises", availableExpertises.getAvailableExpertises());
        return "apply_for_position";
    }


}
