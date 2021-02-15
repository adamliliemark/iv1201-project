package com.iv1201.project.recruitment.presentation.forms;

import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Stores the availability period values entered by, as well as the the user when performing a new job application.
 */
@AvailabilityFormConstraint
public class AvailabilityForm {


    @NotNull(message="form.from.null")
    @FutureOrPresent(message="form.from.notFutureOrPresent")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate from;

    @NotNull(message="form.to.null")
    @FutureOrPresent(message="form.to.notFutureOrPresent")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate to;

    /**
     * Retrieves the current value of the from class attribute.
     * @return is the current value of the from class attribute.
     */
    public LocalDate getFrom() {
        return from;
    }

    /**
     * Sets the class attribute from to a new value.
     * @param from is the new value of the class attribute lastName.
     */
    public void setFrom(LocalDate from) {
        this.from = from;
    }

    /**
     * Retrieves the current value of the to class attribute.
     * @return is the current value of the to class attribute.
     */
    public LocalDate getTo() {
        return to;
    }

    /**
     * Sets the class attribute to to a new value.
     * @param to is the new value of the class attribute lastName.
     */
    public void setTo(LocalDate to) {
        this.to = to;
    }
}
