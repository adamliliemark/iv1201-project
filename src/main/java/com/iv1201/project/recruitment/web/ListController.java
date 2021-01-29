package com.iv1201.project.recruitment.web;

import com.iv1201.project.recruitment.persistence.*;
import com.iv1201.project.recruitment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
@Scope("session")
public class ListController {
    @Autowired
    UserService userService;

    @Autowired
    private CompetenceRepository competenceRepo;

    @GetMapping("/list")
    public String list(@Valid @ModelAttribute("listFormObject") ListForm listForm, Model model){

//        Iterable<Competence> all = competenceRepo.findAll();
//
//        StringBuilder stringBuilder = new StringBuilder();
//
//        all.forEach(c -> stringBuilder.append(c.getId() + " ").append(c.getName() + "\n")
//        );

        model.addAttribute("listFormObject", new ListForm());
        model.addAttribute("expertise", competenceRepo.findAll());


        return "list";
    }

    @PostMapping("/list/applications")
    public String listParameters(@Valid @ModelAttribute("listFormObject") ListForm listForm , BindingResult bindingResult, Model model){

        model.addAttribute("listFormObject", new ListForm());
        model.addAttribute("expertise", competenceRepo.findAll());
        return "list";
    }

}
