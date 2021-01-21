package com.iv1201.project.recruitment.persistence;


import javax.persistence.*;

@Entity
public class Competence {

    protected Competence() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String name;
}