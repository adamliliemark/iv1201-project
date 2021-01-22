package com.iv1201.project.recruitment.web;
import com.iv1201.project.recruitment.persistence.Authority;
import com.iv1201.project.recruitment.persistence.AuthorityRepo;
import com.iv1201.project.recruitment.persistence.User;
import com.iv1201.project.recruitment.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Optional;

@Controller
public class AppController {

    @PostConstruct
    public void addDefaultUsers() {
        if(!userRepo.findByEmail("testuser@example.com").isPresent()) {
            User user = new User(
                    "testuser@example.com",
                    "userFirstName",
                    "userLastName",
                    1000L,
                    new BCryptPasswordEncoder().encode("pass"));
            Authority userAuth = new Authority("ROLE_USER", user);
            userRepo.save(user);
            authorityRepo.save(userAuth);
        }
        if(!userRepo.findByEmail("testadmin@example.com").isPresent()) {
            User admin = new User(
                    "testadmin@example.com",
                    "adminFirstName",
                    "adminLastName",
                    1000L,
                    new BCryptPasswordEncoder().encode("pass"));
            Authority adminAuth = new Authority("ROLE_ADMIN", admin);
            userRepo.save(admin);
            authorityRepo.save(adminAuth);
        }
    }


    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AuthorityRepo authorityRepo;

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
