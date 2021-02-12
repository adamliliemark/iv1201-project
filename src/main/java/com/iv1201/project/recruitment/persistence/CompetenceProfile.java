package com.iv1201.project.recruitment.persistence;

import javax.persistence.*;


/**
 * Maps a User to a specific Competence
 * One User may have many Competences
 */
@Entity
public class CompetenceProfile {

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

    /**
     * A protected empty class constructor.
     */
    protected CompetenceProfile(){};

    /**
     * A class constructor setting the initial values of the class attributes.
     * @param competence is the initial value of the class attribute competence.
     * @param years is the initial value of the class attribute yearsOfExperience.
     * @param user is the initial value of the class attribute user.
     */
    public CompetenceProfile(Competence competence, double years, User user) {
        this.user = user;
        this.competence = competence;
        this.yearsOfExperience = years;
    }

    /**
     * Retrieves the current value of the id class attribute.
     * @return is the current value of the id class attribute.
     */
    public Long getId() {
        return id;
    }

    /**
     * Retrieves the current name of the competence class attribute.
     * @return is the current value of the name of the competence class attribute.
     */
    public String getName() {
        return competence.getName();
    }

    /**
     * Retrieves the current value of the yearsOfExperience class attribute.
     * @return is the current value of the yearsOfExperience class attribute.
     */
    public double getYearsOfExperience() {
        return yearsOfExperience;
    }

    /**
     * Sets the class attribute yearsOfExperience to a new value.
     * @param yearsOfExperience is the new value of the class attribute lastName.
     */
    public void setYearsOfExperience(double yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    /**
     * Retrieves the current value of the competence class attribute.
     * @return is the current value of the competence class attribute.
     */
    public Competence getCompetence() {
        return competence;
    }

    /**
     * Sets the class attribute competence to a new value.
     * @param competence is the new value of the class attribute lastName.
     */
    public void setCompetence(Competence competence) {
        this.competence = competence;
    }
}
