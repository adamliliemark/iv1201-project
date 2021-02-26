package com.iv1201.project.recruitment.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.validation.BindException;

import javax.validation.ValidationException;

/**
 * ErrorHandler is a controller to catch all unexpected exceptions
 * and deliver a generic error message to the client.
 */
@Controller
@ControllerAdvice
public class ErrorHandler implements ErrorController {

    private static Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);


    /**
     * Catches unexpected validation errors, for instance data of the wrong type.
     * @param exception ValidationException
     * @param model for thymeleaf
     * @return errorFallbackView with validation exception error message
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(ValidationException exception, Model model) {
        logExceptionErrorLevel(exception);
        model.addAttribute("error", "validexc");
        return "errorFallbackView";
    }

    /**
     * Catches when a mismatch in types slips through validation of forms.
     * @param exception BindException
     * @param model for thymeleaf
     * @return errorFallbackView with bind exception error message
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(BindException exception, Model model) {
        logExceptionErrorLevel(exception);
        model.addAttribute("error", "bindexc");
        return "errorFallbackView";
    }

    /**
     * Catcher of null pointer exceptions, mainly used for when
     * a client tries to write null values to non nullable fields
     * in the database.
     * @param exception the thrown exception
     * @param model for thymeleaf
     * @return errorFallbackView with null pointer error message
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(NullPointerException exception, Model model) {
        logExceptionErrorLevel(exception);
        model.addAttribute("error", "nullpointer");
        return "errorFallbackView";
    }

    /**
     * The catcher of all unexpected exceptions.
     * @param exception the thrown exception
     * @param model for thymeleaf
     * @return errorFallbackView with generic error message
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception exception, Model model) {
        logExceptionErrorLevel(exception);
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

    private void logExceptionErrorLevel(Exception exception) {
        LOGGER.error("Exception handler got {}: {}", exception.getClass().getName(), exception.getMessage(), exception);
    }
}
