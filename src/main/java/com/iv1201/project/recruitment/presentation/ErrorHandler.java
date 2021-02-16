package com.iv1201.project.recruitment.presentation;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ErrorHandler is a controller to catch all unexpected exceptions
 * and deliver a generic error message to the client.
 */
@Controller
@ControllerAdvice
public class ErrorHandler implements ErrorController {

    /**
     * The catcher of all exceptions thrown that isn't catched
     * locally.
     * @param exception the thrown exception
     * @param model for thymeleaf
     * @return errorFallbackView with generic error message
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception exception, Model model) {
        model.addAttribute("error", "generic");
        return "errorFallbackView";
    }

    /**
     * Boilerplate needed for implementing ErrorController
     * @return not needed
     */
    @Override
    public String getErrorPath() {
        return "";
    }
}
