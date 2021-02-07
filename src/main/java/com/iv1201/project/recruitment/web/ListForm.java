package com.iv1201.project.recruitment.web;

import java.time.LocalDate;

public class ListForm {


    private LocalDate date;
    private String competence;
    private String firstName;
    private String lastName;
    private AvailabilityForm availabilityForm;
    private int min = 0;
    private int max = 3;
    private int size = max - min;


    void increase(){
        setMin(getMin() + getSize());
        setMax(getMax() + getSize());
    }

    void decrease(){
        setMin(getMin() - getSize());
        setMax(getMax() - getSize());
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public AvailabilityForm getAvailabilityForm() {
        return availabilityForm;
    }

    public void setAvailabilityForm(AvailabilityForm availabilityForm) {
        this.availabilityForm = availabilityForm;
    }
}
