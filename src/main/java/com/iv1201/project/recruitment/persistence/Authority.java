package com.iv1201.project.recruitment.persistence;


import javax.persistence.*;

@Entity(name = "authorities")
public class Authority {

    protected Authority() {}

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String authority;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn
    private User user;
}