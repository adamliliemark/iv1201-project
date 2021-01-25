package com.iv1201.project.recruitment.web;
import com.iv1201.project.recruitment.model.AvailableExpertises;
import com.iv1201.project.recruitment.model.Expertise;
import com.iv1201.project.recruitment.model.LiveUser;
import com.iv1201.project.recruitment.persistence.*;
import com.iv1201.project.recruitment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.*;

@Controller
public class AppController {

    @Autowired
    UserService userService;

    private final Map<String, LiveUser> users = new HashMap<String, LiveUser>();
    private final AvailableExpertises availableExpertises = new AvailableExpertises();


    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        return "admin";
    }

    @GetMapping("/")
    public String home(Principal user, Model model) {
        if(!(user.getName().isEmpty()))
            model.addAttribute("loggedIn", true);
        else
            model.addAttribute("loggedIn", false);
        model.addAttribute("msg", "hejsan");
        return "home";
    }

    @PostMapping("/")
    public String post(Principal user, Model model) {
        if(!(user.getName().isEmpty()))
            model.addAttribute("loggedIn", true);
        else
            model.addAttribute("loggedIn", false);
        model.addAttribute("msg", "svejsan");
        return "home";
    }

    @GetMapping("/create")
    public String createAccount(Model model) {
        model.addAttribute("logegdIn", false);
        return "create_account";
    }

    @PostMapping("/create")
    public String accountCreation(Model model) {
        // this is where we deal with account creation
        model.addAttribute("loggedIn", true);
        return "create_account";
    }

    @GetMapping("/apply")
    public String applyForPosition(@ModelAttribute LiveUser user, @ModelAttribute Expertise expertise, Principal principalUser, Model model) {
        user.setUser(new User("mail@mail.com", "john", "doe", 192830L, "pass"));
        model.addAttribute("user", user);
        users.put(principalUser.getName(), user);
        model.addAttribute("expertise", expertise);
        model.addAttribute("availableExpertises", availableExpertises.getAvailableExpertises());
        return "apply_for_position";
    }

    @PostMapping("/apply")
    public String fetchApplication(@ModelAttribute Expertise expertise, Principal puser, Model model) {
        LiveUser user = users.get(puser.getName());
        user.tempSetComp(expertise.getExpertise(), expertise.getYears());
        model.addAttribute("user", user);
        model.addAttribute("availableExpertises", availableExpertises.getAvailableExpertises());
        return "apply_for_position";
    }
}
