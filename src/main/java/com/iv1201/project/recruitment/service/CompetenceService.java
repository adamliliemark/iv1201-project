package com.iv1201.project.recruitment.service;

import com.iv1201.project.recruitment.persistence.Competence;
import com.iv1201.project.recruitment.persistence.CompetenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class CompetenceService {
    @Autowired
    private CompetenceRepository competenceRepo;

    /**
     * Fetch all competences mapped to their local names
     * @param languageCode the languageCode to match
     * @return A Map, where keys are names in locale language and values are the matching Competences
     */
    @Transactional
    public Map<String, Competence> getAllWithLocalNames(String languageCode) {
        Iterable<Competence> competences = competenceRepo.findAll();
        Map<String, Competence> result = new HashMap<>();
        for(Competence c : competences) {
            result.put(c.getName(languageCode), c);
        }
        return result;
    }
}
