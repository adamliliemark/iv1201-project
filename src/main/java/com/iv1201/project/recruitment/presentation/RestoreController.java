package com.iv1201.project.recruitment.presentation;

import com.iv1201.project.recruitment.application.UserService;
import com.iv1201.project.recruitment.application.exceptions.UserServiceError;
import com.iv1201.project.recruitment.presentation.forms.CompetenceForm;
import com.iv1201.project.recruitment.presentation.forms.RestoreForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Locale;

/**
 * Checks if the user is logged in and if that is the case redirects the user
 * to to the applications root URL. If not then the user is sent to the loginView.
 */
@Controller
public class RestoreController {

    @Autowired
    UserService userService;

    /**
     *
     * @return the restore view
     */
    @GetMapping("/restore")
    public String restore(@ModelAttribute("restoreFormObject") RestoreForm restoreForm) {
        return "restoreView";
    }

    /**
     * Endpoint for requesting an account restore. An account can be restored if
     * it was migrated but incomplete (missing password).
     * In the finished product this should call
     * an emailService, but for now we use a hardcoded reset password.
     * @param restoreForm the user submitted data
     * @param bindingResult the form result
     * @param model the model
     * @return restoreSuccessView on successful restore, else restoreView with an error message
     */
    @PostMapping("/restore")
    public String restorePost(@Valid @ModelAttribute("restoreFormObject") RestoreForm restoreForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult
                .getFieldErrors()
                .forEach(fe -> model.addAttribute("error", fe.getDefaultMessage()));
        } else {
            try {
                userService.restoreUnmigratedPerson(restoreForm.getEmail());
                model.addAttribute("success", true);
            } catch (UserServiceError e) {
                switch(e.errorCode) {
                    case CONFLICTING_USER:
                        model.addAttribute("error", "restore.conflict");
                        break;
                    default:
                        model.addAttribute("error", "restore.uncaught");
                }
            }
        }
        return "restoreView";
    }
}
