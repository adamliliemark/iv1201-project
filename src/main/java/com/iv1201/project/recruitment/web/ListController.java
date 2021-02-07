package com.iv1201.project.recruitment.web;

import com.iv1201.project.recruitment.persistence.*;
import com.iv1201.project.recruitment.service.Search;
import com.iv1201.project.recruitment.service.SearchError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;


@Controller
@Scope("session")
public class ListController {

    private Search searcher;
    private Iterable<Competence> competences;
    private List<ApplicationDTO> applications;
    private ListForm listForm;

    @Autowired
    private CompetenceRepository competenceRepo;

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/list")
    public String list(Model model){

        if(competences == null)
            competences = competenceRepo.findAll();

        listForm = new ListForm();

        model.addAttribute("listFormObject", listForm);
        model.addAttribute("searched", false);
        model.addAttribute("expertise", competences);
        return "list";
    }

    @PostMapping("/list/applications")
    public String listParameters (@Valid @ModelAttribute("listFormObject") ListForm listForm, Model model){

        if(searcher == null)
            searcher = new Search();

        try {
            applications = searcher.getApplications(listForm, userRepo, competences);
        }catch (SearchError e){
            e.printStackTrace();
        }

        model.addAttribute("searched", true);
        model.addAttribute("applicationsObject", applications);
        model.addAttribute("expertise", competences);
        return "list";
    }

    @PostMapping("/list/next")
    public String listNextParameters( Model model){

        this.listForm.setMax(this.listForm.getMax() + this.listForm.getSize());
        this.listForm.setMin(this.listForm.getMin() + this.listForm.getSize());

        model.addAttribute("listFormObject", listForm);
        model.addAttribute("searched", true);
        model.addAttribute("applicationsObject", applications);
        model.addAttribute("expertise", competences);
        return "list";
    }

    @PostMapping("/list/prev")
    public String listPrevParameters( Model model){

        this.listForm.setMax(this.listForm.getMax() - this.listForm.getSize());
        this.listForm.setMin(this.listForm.getMin() - this.listForm.getSize());

        model.addAttribute("listFormObject", listForm);
        model.addAttribute("searched", true);
        model.addAttribute("applicationsObject", applications);
        model.addAttribute("expertise", competences);
        return "list";
    }

}
