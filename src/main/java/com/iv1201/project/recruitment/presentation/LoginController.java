package com.iv1201.project.recruitment.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * Checks if the user is logged in and if that is the case redirects the user
 * to to the applications root URL. If not then the user is sent to the loginView.
 */
@Controller
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * Called when the /login URL is called by the user. Checks if the user is
     * logged in and sends it either to the root URL or the loginView.
     * @param principal is the object representing the currently logged in user.
     * @return is the page the user is redirected to.
     */
    @GetMapping("/login")
    public String login(Principal principal) {
        if(principal != null)
            return "redirect:/";
        return "loginView";
    }
}
