package com.iv1201.project.recruitment.web;

import com.iv1201.project.recruitment.persistence.*;
import com.iv1201.project.recruitment.service.SearchService;
import com.iv1201.project.recruitment.service.SearchServiceError;
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


/**
 * Serves as the controller for when a recruiter wants to search and
 * list applications of interest. All HTTPRequest map to the same view
 * which is dynamically updated by model attributes and shows proper
 * information to the user.
 */
@Controller
@Scope("session")
public class ListController {

    private Iterable<Competence> competences;
    private List<ApplicationDTO> applications;
    private ListForm listForm;
    private boolean searched;
    private int min = 0;
    private int max = 3;
    private final int interval = 3;

    @Autowired
    private SearchService searcher;

    @Autowired
    private CompetenceRepository competenceRepo;

    /**
     * Called when the user wants to enter the listView and set the initial state of the view.
     *
     * @param model is the object carrying the data processed by the Thymeleaf framework.
     * @return is the listView with its initial state values.
     */
    @GetMapping("/list")
    public String list(Model model){

        if(competences == null)
            competences = competenceRepo.findAll();

        searched = false;
        model.addAttribute("listFormObject", new ListForm());
        model.addAttribute("searched", searched);
        model.addAttribute("expertise", competences);
        return "listView";
    }

    /**
     * Called when the user has entered parameters of interest and searches for applications
     * through the <>Search</> class attribute.
     *
     * @param listForm is the object containing the entered search parameters.
     * @param model is the object carrying the data processed by the Thymeleaf framework.
     * @return is the listView with its current state values containing the search result.
     */
    @PostMapping("/list/applications")
    public String listParameters (@Valid @ModelAttribute("listFormObject") ListForm listForm, Model model, BindingResult bindingResult) {

        if (!bindingResult.hasErrors()) {

            if (searcher == null)
                searcher = new SearchService();

                try {
                    applications = searcher.getApplications(listForm, competences);
                    model.addAttribute("applicationsObject", applications);
                    searched = true;
                } catch (SearchServiceError e) {
                    e.printStackTrace();
                }
        }
        this.listForm = listForm;
        model.addAttribute("min", min);
        model.addAttribute("max", max);
        model.addAttribute("interval", interval);
        model.addAttribute("searched", searched);
        model.addAttribute("expertise", competences);
        return "listView";
    }
    /**
     * Called when the search result is to large to display on one single page and the
     * user wants to see the next set of search result following the currently showing.
     *
     * @param model is the object carrying the data processed by the Thymeleaf framework.
     * @return is the listView with its current state values containing the next set of the search result.
     */

    @PostMapping("/list/next")
    public String listNextParameters(Model model){

        max = max + interval;
        min = min + interval;

        addAttributes(model);
        return "listView";
    }

    /**
     * Called when the search result is to large to display on one single page and the
     * user wants to see the previous set of search result preceding the currently showing.
     *
     * @param model is the object carrying the data processed by the Thymeleaf framework.
     * @return is the listView with its current state values containing the previous set of the search result.
     */
    @PostMapping("/list/prev")
    public String listPrevParameters(Model model){

        max = max - interval;
        min = min - interval;

        addAttributes(model);
        return "listView";
    }

    private void addAttributes(Model model){
        model.addAttribute("min", min);
        model.addAttribute("max", max);
        model.addAttribute("interval", interval);
        model.addAttribute("listFormObject", listForm);
        model.addAttribute("searched", searched);
        model.addAttribute("applicationsObject",applications);
        model.addAttribute("expertise", competences);
    }
}
