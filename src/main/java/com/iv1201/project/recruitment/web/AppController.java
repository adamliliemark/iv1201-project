package com.iv1201.project.recruitment.web;
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

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        return "admin";
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("msg", "hejsan");
        model.addAttribute("loggedIn", false);
        return "home";
    }

    @PostMapping("/")
    public String post(Model model) {
        model.addAttribute("msg", "svejsan");
        model.addAttribute("loggedIn", true);
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

    private final Map<String, LiveUser> users = new HashMap<String, LiveUser>();

    @GetMapping("/apply")
    public String applyForPosition(@ModelAttribute LiveUser user, @ModelAttribute Expertise expertise, Principal puser, Model model) {
        user.setUser(new User("mail@mail.com", "john", "doe", 192830L, "pass"));
        user.tempSetComp("hot dogs", 8);
        model.addAttribute("user", user);
        users.put(puser.getName(), user);
        System.out.println(puser.getName());
        model.addAttribute("expertise", expertise);
        return "apply_for_position";
    }

    @PostMapping("/apply")
    public String fetchApplication(@ModelAttribute Expertise expertise, Principal puser, Model model) {
        LiveUser user = users.get(puser.getName());
        user.tempSetComp(expertise.getExpertise(), expertise.getYears());
        model.addAttribute("user", user);
        return "apply_for_position";
    }
}
