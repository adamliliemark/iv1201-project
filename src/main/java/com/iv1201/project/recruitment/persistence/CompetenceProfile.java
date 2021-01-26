package com.iv1201.project.recruitment.persistence;

import org.hibernate.annotations.common.reflection.java.generics.CompoundTypeEnvironment;

import javax.persistence.*;

@Entity
public class CompetenceProfile {

    public CompetenceProfile(User user) {
        this.user = user;
    }

    protected CompetenceProfile(){};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision=4, scale=2)
    private double yearsOfExperience;

    @ManyToOne
    @JoinColumn
    private Competence competence;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    public String getName() {
        return competence.getName();
    }

    public CompetenceProfile(Competence competence, int years, User user) {
        this.user = user;
        this.competence = competence;
        this.yearsOfExperience = years;
    }

    public Long getId() {
        return id;
    }

    public double getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(double yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public Competence getCompetence() {
        return competence;
    }

    public void setCompetence(Competence competence) {
        this.competence = competence;
    }
}
