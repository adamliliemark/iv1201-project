package com.iv1201.project.recruitment.service;

import com.iv1201.project.recruitment.persistence.Competence;
import com.iv1201.project.recruitment.persistence.CompetenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CompetenceService {
    @Autowired
    CompetenceRepository competenceRepo;

    public Map<String, Competence> getAllWithLocalNames(String languageCode) {
        Iterable<Competence> competences = competenceRepo.findAll();
        Map<String, Competence> result = new HashMap<>();
        for(Competence c : competences) {
            result.put(c.getName(languageCode), c);
        }
        return result;
    }
}
