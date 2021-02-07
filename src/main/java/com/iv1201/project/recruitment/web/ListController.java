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
    private boolean searched;

    @Autowired
    private CompetenceRepository competenceRepo;

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/list")
    public String list(Model model){

        if(competences == null)
            competences = competenceRepo.findAll();

        searched = false;
        model.addAttribute("listFormObject", new ListForm());
        model.addAttribute("searched", searched);
        model.addAttribute("expertise", competences);
        return "list";
    }

    @PostMapping("/list/applications")
    public String listParameters (@Valid @ModelAttribute("listFormObject") ListForm listForm, Model model){

        if(searcher == null)
            searcher = new Search();

        searched = false;

        if (!listForm.isEmpty()) {
            try {
                applications = searcher.getApplications(listForm, userRepo, competences);
                model.addAttribute("applicationsObject", applications);
                searched = true;
            } catch (SearchError e) {
                e.printStackTrace();
            }
        }

        this.listForm = listForm;
        model.addAttribute("searched", searched);
        model.addAttribute("expertise", competences);
        return "list";
    }

    @PostMapping("/list/next")
    public String listNextParameters(Model model){

        listForm.next();

        model.addAttribute("listFormObject", listForm);
        model.addAttribute("searched", searched);
        model.addAttribute("applicationsObject",applications);
        model.addAttribute("expertise", competences);
        return "list";
    }

    @PostMapping("/list/prev")
    public String listPrevParameters( Model model){

        listForm.prev();

        model.addAttribute("listFormObject", listForm);
        model.addAttribute("searched", searched);
        model.addAttribute("applicationsObject", applications);
        model.addAttribute("expertise", competences);
        return "list";
    }

}
