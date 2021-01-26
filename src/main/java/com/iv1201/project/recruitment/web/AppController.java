package com.iv1201.project.recruitment.web;
<<<<<<< HEAD
import com.iv1201.project.recruitment.persistence.Competence;
import com.iv1201.project.recruitment.persistence.CompetenceRepository;
import com.iv1201.project.recruitment.persistence.User;
import com.iv1201.project.recruitment.persistence.UserRepository;
=======
import com.iv1201.project.recruitment.model.AvailableExpertises;
import com.iv1201.project.recruitment.model.Expertise;
import com.iv1201.project.recruitment.model.LiveUser;
import com.iv1201.project.recruitment.persistence.*;
import com.iv1201.project.recruitment.service.UserService;
>>>>>>> origin/master
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

<<<<<<< HEAD
import java.util.List;
import java.util.Optional;

@Controller
public class AppController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CompetenceRepository competenceRepo;

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

    @GetMapping("/list")
    public String list(Model model){

        Iterable<Competence> all = competenceRepo.findAll();

        StringBuilder stringBuilder = new StringBuilder();

        all.forEach(c -> stringBuilder.append(c.getId()));

        model.addAttribute("competence", stringBuilder.toString());

        return "list";
    }
=======
import java.security.Principal;
import java.util.*;

@Controller
public class AppController {

    @Autowired
    UserService userService;
>>>>>>> origin/master

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

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
