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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String authority;

    @ManyToOne
    @JoinColumn
    private User user;

    /**
     * An empty class constructor.
     */
    protected Authority() {}

    /**
     * A fully populated class cunstructor setting the class attributes to their initial values.
     * @param authority is the initial value of the class attribute authority.
     * @param user is the initial value of the class attribute user.
     */
    public Authority(String authority, User user) {
        this.user = user;
        this.authority = authority;
    }
}