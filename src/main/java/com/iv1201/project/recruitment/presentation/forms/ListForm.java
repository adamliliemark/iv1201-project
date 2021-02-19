package com.iv1201.project.recruitment.presentation.forms;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Stores the parameters entered by the user via the ListView and serves as a DTO class.
 * @see ListFormConstraint for constraints.
 * @see ListFormValidator for validation.
 */
@ListFormConstraint
public class ListForm {

    @Getter @Setter private String competence;
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
    @Getter @Setter private AvailabilityForm availabilityForm;
}
