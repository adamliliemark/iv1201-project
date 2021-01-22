package com.iv1201.project.recruitment.model;

import com.iv1201.project.recruitment.persistence.Availability;
import com.iv1201.project.recruitment.persistence.CompetenceProfile;
import com.iv1201.project.recruitment.persistence.User;

import java.util.List;

public class LiveUser {
    private User user;
    private List<CompetenceProfile> competences;
    private Availability availability;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CompetenceProfile> getCompetences() {
        return competences;
    }

    public void setCompetences(List<CompetenceProfile> competences) {
        this.competences = competences;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }
}
