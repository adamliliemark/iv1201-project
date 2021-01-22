package com.iv1201.project.recruitment.web;
import com.iv1201.project.recruitment.persistence.User;
import com.iv1201.project.recruitment.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
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

    HashMap<String, Integer> exp = new HashMap<>();
    @GetMapping("/apply")
    public String applyForPosition(@ModelAttribute Appl appl, Model model) {
        appl.setExpertise("Hot dogs");
        appl.setYears(39);
        model.addAttribute("application", appl);
        System.out.println(appl.getExpertise());
        System.out.println(appl.getYears());
        return "apply_for_position";
    }

    @PostMapping("/apply")
    public String fetchApplication(@ModelAttribute Appl appl, Model model) {
        String exps = appl.getExpertise();
        int years = appl.getYears();
        System.out.println(exps);
        exp.put(exps, years);
        model.addAttribute("experiences", exp);
        model.addAttribute("application", appl);
        return "apply_for_position";
    }
}
