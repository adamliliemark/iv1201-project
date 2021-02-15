package com.iv1201.project.recruitment.presentation;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@ControllerAdvice
public class ErrorHandler implements ErrorController {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(IllegalArgumentException exception, Model model) {
        String exc = exception.getMessage().toUpperCase().split("\\.")[0];
        switch (exc) {
            case "FORM":
                model.addAttribute("error", exception.getMessage());
                break;
            case "GENERIC":
                model.addAttribute("error", "generic");
                break;
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "error";
    }
}
