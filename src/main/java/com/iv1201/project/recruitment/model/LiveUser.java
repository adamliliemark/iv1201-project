package com.iv1201.project.recruitment.model;

import com.iv1201.project.recruitment.persistence.User;

import java.util.HashMap;

public class LiveUser {
    private User user;
    private final HashMap<String, Integer> mapCompetences;

    public LiveUser() {
        this.mapCompetences = new HashMap<>();
    }

      public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public HashMap<String, Integer> getCompetences() {
        return mapCompetences;
    }

    public void mapCompetenceYearCombo(String competence, int years) {
        this.mapCompetences.put(competence, years);
    }
}
