package com.iv1201.project.recruitment.web;

import com.iv1201.project.recruitment.model.AvailableParameters;
import com.iv1201.project.recruitment.persistence.*;
import com.iv1201.project.recruitment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Parameter;
import java.security.Principal;



@Controller
@Scope("session")
public class ListController {
    @Autowired
    UserService userService;

    @Autowired
    private CompetenceRepository competenceRepo;

    @GetMapping("/list")
    public String list(Model model){

        Iterable<Competence> all = competenceRepo.findAll();

        StringBuilder stringBuilder = new StringBuilder();

        all.forEach(c -> stringBuilder.append(c.getId() + " ").append(c.getName() + "\n")
        );
        model.addAttribute("competence", stringBuilder.toString());

        model.addAttribute("parameter", );

        return "list";
    }

    @PostMapping("/list/parameters")
    public String listParameters(Principal principal, Model model){

        System.out.println("inne i list paramters");

        return "list";
    }

}
