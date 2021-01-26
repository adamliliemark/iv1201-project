package com.iv1201.project.recruitment.persistence;


import javax.persistence.*;

@Entity
public class CompetenceProfile {

    public CompetenceProfile() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision=4, scale=2)
    private double yearsOfExperience;

    @Column(nullable = false, unique = false)
    private String authority;

    @ManyToOne
    @JoinColumn
    private Competence competence;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn
    private User userEmail;

    public CompetenceProfile(String competence, int years) {
        this.competence = new Competence();
        this.competence.setName(competence);
        this.yearsOfExperience = years;
    }

    public CompetenceProfile(Competence competence, int years) {
        this.competence = competence;
        this.yearsOfExperience = years;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(double yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Competence getCompetence() {
        return competence;
    }

    public void setCompetence(Competence competence) {
        this.competence = competence;
    }

    public User getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(User userEmail) {
        this.userEmail = userEmail;
    }
}