package com.iv1201.project.recruitment.web;
import com.iv1201.project.recruitment.persistence.User;
import com.iv1201.project.recruitment.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class AppController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping("/test")
    public String test(Model model) {
        /*
        String email = "test@example.com";
        long i= userRepo.count();
        System.err.println(i);
        Optional<User> userOrNot = userRepo.findById(email);
        if(userOrNot.isPresent()) {
            User u =  userOrNot.get();
            System.err.println(u.getEmail());
            u.setFirstName("Adam");
            userRepo.save(u);
        } else
            System.err.println("NO USER: " + email + " found :(");
        */
        return "test";
    }


    @GetMapping("/login")
    public String login(Model model) {
        return "login";
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

    @GetMapping("/apply")
    public String applyForPosition(Model model) {
        return "apply_for_position";
    }
}
