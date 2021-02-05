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

    @GetMapping("/admin")
    public String admin() {
        return "adminView";
    }

    @GetMapping("/")
    public String home() {
        return "homeView";
    }

}
