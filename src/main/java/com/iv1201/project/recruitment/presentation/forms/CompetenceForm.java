package com.iv1201.project.recruitment.presentation.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * Stores the values entered by a user when a new application is being entered.
 */
public class CompetenceForm {

    @NotNull
    private String name;

    @NotNull
    @PositiveOrZero
    private double years;

    /**
     * Retrieves the current value of the name class attribute.
     * @return is the current value of the name class attribute.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the class attribute name to a new value.
     * @param name is the new value of the class attribute lastName.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the current value of the years class attribute.
     * @return is the current value of the years class attribute.
     */
    public double getYears() {
        return years;
    }

    /**
     * Sets the class attribute years to a new value.
     * @param years is the new value of the class attribute lastName.
     */
    public void setYears(double years) {
        this.years = years;
    }
}
