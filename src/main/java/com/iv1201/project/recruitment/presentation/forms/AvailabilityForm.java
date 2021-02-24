package com.iv1201.project.recruitment.presentation.forms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.GroupSequence;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Stores the availability period values entered by, as well as the the user when performing a new job application.
 */

@AvailabilityFormConstraint()
public class AvailabilityForm {

    @NotNull(message="form.from.null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message="form.from.notFutureOrPresent")
    @Getter @Setter private LocalDate from;

    @NotNull(message="form.to.null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message="form.to.notFutureOrPresent")
    @Getter @Setter private LocalDate to;
}
