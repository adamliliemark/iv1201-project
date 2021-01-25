package com.iv1201.project.recruitment.model;

import java.util.ArrayList;

public class AvailableExpertises {
    private ArrayList<String> availableExpertises;

    public AvailableExpertises() {
        availableExpertises = new ArrayList<>();
        availableExpertises.add("Hot Dogs");
        availableExpertises.add("Sausage");
        availableExpertises.add("Gurka");
        availableExpertises.add("Tomat");

    }

    public ArrayList<String> getAvailableExpertises() {
        return availableExpertises;
    }

    public void setAvailableExpertises(ArrayList<String> availableExpertises) {
        this.availableExpertises = availableExpertises;
    }
}
