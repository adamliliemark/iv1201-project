package com.iv1201.project.recruitment.persistence;


import javax.persistence.*;

@Entity(name = "authorities")
public class Authority {

    protected Authority() {}

    public Authority(String authority, User user) {
        this.user = user;
        this.authority = authority;
    }

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String authority;

    @ManyToOne
    @JoinColumn
    private User user;
}