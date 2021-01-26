package com.iv1201.project.recruitment.persistence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * UserDTO is an interface to connect Availability and Competences to a specific
 * user in a session.
 */
public interface UserDTO {
    HashMap<CompetenceProfile, Integer> competences = new HashMap<>();
    ArrayList<Availability> availability = new ArrayList<>();

    default HashMap<CompetenceProfile, Integer> getCompetences() {
        return competences;
    }

    default void addCompetence(String competence, Integer years) {
        for(CompetenceProfile cmp : competences.keySet()) {
            if(cmp.getCompetence().getName().equals(competence)) {
                competences.put(cmp, years);
                return;
            }
        }
        Competence comp = new Competence(competence);
        CompetenceProfile compProf = new CompetenceProfile(comp, years);
        competences.put(compProf, years);
    }

    default void updateDuplicateCompetence(String competence, Integer years) {
        for(CompetenceProfile cmp : competences.keySet()) {
            if(cmp.getCompetence().getName().equals(competence)) {
                competences.put(cmp, years);
            }
        }

    }

    /**
     * The Availability arraylist is a bodged solution to having a mutable
     * object in the interface. This is because the User instance is persisted
     * as a whole, and should not store values not persisted.
     *
     */
    default ArrayList<Availability> getAvailability() {
        return availability;
    }

    /**
     * If there already is an availability, replace it. Else, add it.
     * @param from date
     * @param to date
     */
    default void addAvailability(LocalDate from, LocalDate to) {
        if(availability.size() > 0) {
            setAvailability(from, to);
        } else {
            availability.add(new Availability(from, to));
        }
    }

    /**
     * If the list is empty, add it. Else, replace it.
     * @param from date
     * @param to date
     */
    default void setAvailability(LocalDate from, LocalDate to) {
        if(availability.size() == 0) {
            addAvailability(from, to);
        } else {
            availability.set(0, new Availability(from, to));
        }
    }
}
