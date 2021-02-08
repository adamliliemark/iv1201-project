package com.iv1201.project.recruitment.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Principal principal) {
        if(principal != null)
            return "redirect:/";
        return "loginView";
    }
}