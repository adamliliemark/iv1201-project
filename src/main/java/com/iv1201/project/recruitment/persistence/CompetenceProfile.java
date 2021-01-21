package com.iv1201.project.recruitment.persistence;


import javax.persistence.*;

@Entity
public class CompetenceProfile {

    protected CompetenceProfile() {}

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
}