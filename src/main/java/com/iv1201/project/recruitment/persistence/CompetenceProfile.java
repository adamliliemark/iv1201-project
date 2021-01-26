package com.iv1201.project.recruitment.persistence;

import javax.persistence.*;

@Entity
public class CompetenceProfile {

    protected CompetenceProfile(User user) {
        this.user = user;
    }

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

    protected CompetenceProfile(){};

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