package com.iv1201.project.recruitment.domain;

import com.iv1201.project.recruitment.application.UserService;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Represents a stored authority
 * An authority denotes the access levels of a User
 * @see UserService.Role
 */
@Entity(name = "authorities")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_EMAIL", "AUTHORITY"})})
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private Long id;

    @Column(nullable = false, unique = false)
    @Getter private String authority;

    @ManyToOne
    @JoinColumn
    @Getter private User user;

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