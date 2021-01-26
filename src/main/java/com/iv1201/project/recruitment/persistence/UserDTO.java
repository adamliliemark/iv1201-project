package com.iv1201.project.recruitment.persistence;

import java.util.HashMap;

/**
 * UserDTO is an interface to connect 'Competences to a specific
 * user in a session.
 */
public interface UserDTO {
    HashMap<CompetenceProfile, Integer> competences = new HashMap<>();

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
}
