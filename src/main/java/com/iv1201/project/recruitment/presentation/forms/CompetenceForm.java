package com.iv1201.project.recruitment.presentation.forms;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * Stores the values entered by a user when a new application is being entered.
 */
public class CompetenceForm {

    @NotNull(message="form.competence.name.null")
    @Getter @Setter  private String name;

    @NotNull(message="form.competence.years.null")
    @PositiveOrZero(message="form.competence.years.negative")
    @Getter @Setter private Double years;
}
