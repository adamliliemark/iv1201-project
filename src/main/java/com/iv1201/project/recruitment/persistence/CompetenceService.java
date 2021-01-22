package com.iv1201.project.recruitment.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CompetenceService {

    @Autowired
    private CompetenceRepository competenceRepository;

    public List<Competence> findAll(){

        Iterable<Competence> all = competenceRepository.findAll();

        List competence = new ArrayList<Competence>();

        all.forEach(c -> competence.add(c));

        return competence;
    }

}
