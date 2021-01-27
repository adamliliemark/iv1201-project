package com.iv1201.project.recruitment.web;

import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

public class AvailabilityForm {
    @NotEmpty
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate from;

    @NotEmpty
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate to;

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }
}
