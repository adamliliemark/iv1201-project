package com.iv1201.project.recruitment.presentation.forms;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Stores the availability period values entered by, as well as the the user when performing a new job application.
 */
public class RestoreForm {
    @NotBlank(message="error.missingEmail")
    @Email(message="error.invalidEmail")
    private String email;

    /**
     * Gets the Email address
     * @return the email address
     */
    public String getEmail() { return this.email; }

    public void setEmail(String email) {this.email = email;}
}
