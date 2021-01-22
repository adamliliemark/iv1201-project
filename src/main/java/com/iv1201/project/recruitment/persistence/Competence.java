package com.iv1201.project.recruitment.persistence;


import javax.persistence.*;

@Entity
public class Competence {

    protected Competence() {}

    public Competence(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}