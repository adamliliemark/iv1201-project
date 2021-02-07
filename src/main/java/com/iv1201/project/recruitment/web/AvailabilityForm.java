package com.iv1201.project.recruitment.web;

import com.iv1201.project.recruitment.persistence.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AvailabilityFormConstraint
public class AvailabilityForm {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @FutureOrPresent
    private LocalDate from;

    @NotNull
    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate to;

    private User user;

    public void setUser(User user) { this.user = user; }

    public User getUser() { return user; }

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
