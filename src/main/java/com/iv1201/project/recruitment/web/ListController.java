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

    @Autowired
    private CompetenceRepository competenceRepo;

    @GetMapping("/list")
    public String list(@Valid @ModelAttribute("listFormObject") ListForm listForm, Model model){
        model.addAttribute("expertise", competenceRepo.findAll());
        return "list";
    }

    @PostMapping("/list/applications")
    public String listParameters(@Valid @ModelAttribute("listFormObject") ListForm listForm , BindingResult bindingResult, Model model){

        if(searcher == null)
            searcher = new Search();

        try {
            searcher.getApplications(listForm);
        }catch (SearchError e){
            e.printStackTrace();
        }
        model.addAttribute("expertise", competenceRepo.findAll());
        return "list";
    }

}
