package com.iv1201.project.recruitment.web;
import com.iv1201.project.recruitment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class AppController {

    @Autowired
    UserService userService;

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



}
