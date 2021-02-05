package com.iv1201.project.recruitment.web;

import com.iv1201.project.recruitment.persistence.User;
import com.iv1201.project.recruitment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Optional;

/**
 * RootController controls the user's rootview, i.e. delivers either
 * the regular start page for regular users and delivers the admin
 * start page for admins.
 */
@Controller
public class RootController {

    // this controller needs to be implemented as stated above.

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String admin(Model model) {
        return "adminView";
    }

    @GetMapping("/")
    public String home(Principal principal, Model model) {
        Optional<User> userMaybe = userService.findByEmail(principal.getName());
        if(!userMaybe.isPresent()) {
            model.addAttribute("loggedIn", false);
        } else {
            model.addAttribute("loggedIn", true);
        }
        return "homeView";
    }

    @PostMapping("/")
    public String post(Principal principal, Model model) {
        Optional<User> userMaybe = userService.findByEmail(principal.getName());
        if(!userMaybe.isPresent()) {
            model.addAttribute("loggedIn", false);
        } else {
            model.addAttribute("loggedIn", true);
        }
        return "homeView";
    }



}
