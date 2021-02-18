package com.iv1201.project.recruitment.presentation.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Stores the email field entered requesting an account restore.
 */
public class RestoreForm {

    @NotBlank(message="error.missingEmail")
    @Email(message="error.invalidEmail")
    @Getter @Setter private String email;
}
