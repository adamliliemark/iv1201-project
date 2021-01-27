package com.iv1201.project.recruitment.web;

import com.iv1201.project.recruitment.model.AvailableExpertises;
import com.iv1201.project.recruitment.model.AvailableParameters;
import com.iv1201.project.recruitment.model.Expertise;
import com.iv1201.project.recruitment.model.LiveUser;
import com.iv1201.project.recruitment.persistence.*;
import com.iv1201.project.recruitment.service.UserService;
import org.apache.maven.plugin.descriptor.Parameter;
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
    public String home(Principal user, Modegithub fetch from masl model) {
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


}
