package com.iv1201.project.recruitment.service;

import com.iv1201.project.recruitment.persistence.CompetenceProfile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
public class ListService {

    @Query("SELECT a FROM CompetenceProfile a WHERE a.id = ?1")
    public Collection<CompetenceProfile> listApplications() {
        return null;
    }
}
