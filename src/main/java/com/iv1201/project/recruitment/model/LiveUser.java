package com.iv1201.project.recruitment.model;

import com.iv1201.project.recruitment.persistence.Availability;
import com.iv1201.project.recruitment.persistence.User;

import java.util.HashMap;

/**
 * LiveUser is a data holding class. A logged in user will be
 * assigned a LiveUser, and data produced during the session
 * will be stored here.
 */
public class LiveUser {
    private User user;
    private final HashMap<String, Integer> mapCompetences;
    private Availability availability;

    public LiveUser() {
        this.mapCompetences = new HashMap<>();
    }

    /**
     * A LiveUser's competences are stored in a hashmap; this means
     * that duplicates are handled automatically.
     * @param competence the competence to store
     * @param years the associated years of experience
     */
    public void mapCompetenceYearCombo(String competence, int years) {
        this.mapCompetences.put(competence, years);
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

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }
}
