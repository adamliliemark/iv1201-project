package com.iv1201.project.recruitment.model;

import com.iv1201.project.recruitment.persistence.Availability;
import com.iv1201.project.recruitment.persistence.Competence;
import com.iv1201.project.recruitment.persistence.CompetenceProfile;
import com.iv1201.project.recruitment.persistence.User;

import java.util.ArrayList;

public class LiveUser {
    private User user;
    private ArrayList<CompetenceProfile> competences;
    private Availability availability;
    private CompetenceProfile temp;

    public LiveUser() {
        this.competences = new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<CompetenceProfile> getCompetences() {
        return competences;
    }

    public void setCompetences(ArrayList<CompetenceProfile> competences) {
        this.competences = competences;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public void tempSetComp(String competence, int years) {
        this.competences.add(new CompetenceProfile(competence, years));
    }
}
