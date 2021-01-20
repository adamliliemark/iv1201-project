package com.iv1201.project.recruitment.persistence;

import javax.persistence.*;

/*Forces creation if necessary creation of users table with additional fields,
* Faster iteration time than a creation statement, but lacks constraints.
*  */
@Entity(name = "users")
public class User {

    protected User() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = false)
    private String firstName;

    @Column (nullable = false, unique = true)
    Long ssn;

    @Column(nullable = false, unique = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = false)
    private String password;

    @Column(nullable = false, unique = false)
    private Boolean enabled;
}

