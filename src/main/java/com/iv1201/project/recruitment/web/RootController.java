package com.iv1201.project.recruitment.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * RootController controls the user's rootview, i.e. delivers either
 * the regular start page for regular users and delivers the admin
 * start page for admins.
 */
@Controller
public class RootController {

    /**
     * Catches the logged in user and redirects to the correct end point.
     * @param httpServletRequest the request
     * @return redirection to the correct path
     */
    @RequestMapping("/")
    public String root(HttpServletRequest httpServletRequest) {
        String direction = httpServletRequest.isUserInRole("ROLE_USER") ? "home" : "admin";
        return "redirect:/" + direction;
    }

    /**
     * Deliver admin view
     * @return adminView
     */
    @GetMapping("/admin")
    public String admin() {
        return "adminView";
    }

    /**
     * Deliver home view
     * @return homeView
     * @param principal
     */
    @GetMapping("/home")
    public String home(Principal principal) {
        System.out.println(principal.getName());
        return "homeView";
    }
}
