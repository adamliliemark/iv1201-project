package com.iv1201.project.recruitment.persistence;


import javax.persistence.*;


/**
 * Represents a stored authority
 * An authority denotes the access levels of a User
 * @see com.iv1201.project.recruitment.service.UserService.Role
 */
@Entity(name = "authorities")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_EMAIL", "AUTHORITY"})})
public class Authority {
    protected Authority() {}

    public Authority(String authority, User user) {
        this.user = user;
        this.authority = authority;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String authority;

    @ManyToOne
    @JoinColumn
    private User user;
}