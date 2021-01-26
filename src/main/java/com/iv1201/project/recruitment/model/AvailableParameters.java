package com.iv1201.project.recruitment.model;

import java.util.ArrayList;

public class AvailableParameters {

    private ArrayList<String> availableParameters;

    public AvailableParameters() {
        availableParameters = new ArrayList<>();
        availableParameters.add("Time period");
        availableParameters.add("Application date");
        availableParameters.add("Competence");
        availableParameters.add("Name");
    }

    public ArrayList<String> getAvailableParameters() {
        return availableParameters;
    }

    public void setAvailableParameters(ArrayList<String> availableParameters) {
        this.availableParameters = availableParameters;
    }
}
