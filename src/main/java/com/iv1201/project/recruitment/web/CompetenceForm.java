package com.iv1201.project.recruitment.web;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class CompetenceForm {

    @NotNull
    private String name;

    @NotNull
    @PositiveOrZero
    private double years;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getYears() {
        return years;
    }

    public void setYears(double years) {
        this.years = years;
    }
}
