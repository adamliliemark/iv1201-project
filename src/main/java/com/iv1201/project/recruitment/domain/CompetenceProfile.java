package com.iv1201.project.recruitment.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


/**
 * Maps a User to a specific Competence
 * One User may have many Competences
 */
@Entity
public class CompetenceProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private Long id;

    @Column(precision=4, scale=2)
    @Getter @Setter private double yearsOfExperience;

    @ManyToOne
    @JoinColumn
    @Getter @Setter private Competence competence;

    @ManyToOne
    @JoinColumn(nullable = false)
    @Getter private User user;

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
}
