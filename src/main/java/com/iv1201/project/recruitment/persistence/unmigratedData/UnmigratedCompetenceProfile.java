package com.iv1201.project.recruitment.persistence.unmigratedData;

import com.iv1201.project.recruitment.persistence.Competence;
import com.iv1201.project.recruitment.persistence.User;

import javax.persistence.*;

@Entity
public class UnmigratedCompetenceProfile {
    protected UnmigratedCompetenceProfile(){};

    @Id
    private Long competenceProfileId;

    @Column(precision=4, scale=2)
    private double yearsOfExperience;

    @Column
    private String competenceName;

    @ManyToOne
    @JoinColumn(nullable = false)
    private UnmigratedPerson personId;
}
