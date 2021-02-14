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
    public String restore(Locale locale, @ModelAttribute("restoreFormObject") RestoreForm restoreFormObject, Model model) {
        return "restoreView";
    }

    /**
     * Endpoint for requesting a password reset. In the finished product this should call
     * an emailService, but for now we use a hardcoded reset password.
     * @param restoreFormObject the user submitted data
     * @param bindingResult the form result
     * @param model the model
     * @return restoreSuccessView on successful restore, else restoreView with an error message
     */
    @PostMapping("/restore")
    public String restorePost(@Valid @ModelAttribute("restoreFormObject") RestoreForm restoreFormObject, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult
                .getFieldErrors()
                .forEach(fe -> model.addAttribute("error", fe.getDefaultMessage()));
        } else {
            try {
                userService.restoreUnmigratedPerson(restoreFormObject.getEmail());
                model.addAttribute("success", true);
            } catch (UserServiceError e) {
                e.printStackTrace();
                //TODO: add the error message from the bindingresult to the model?
                //How do we do this.
                //TODO: We also want to map UserService errors to error messages? for example UserServiceError.CONFLICING_EMAIL shouöd map to a specific error message.
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
