package com.iv1201.project.recruitment.web;

import java.time.LocalDate;

public class ListForm {


    private LocalDate date;
    private String competence;
    private String name;
    private AvailabilityForm availabilityForm;

    ListForm(){
        this.availabilityForm = new AvailabilityForm();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AvailabilityForm getAvailabilityForm() {
        return availabilityForm;
    }

    public void setAvailabilityForm(AvailabilityForm availabilityForm) {
        this.availabilityForm = availabilityForm;
    }
}
