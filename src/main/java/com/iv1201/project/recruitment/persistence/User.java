package com.iv1201.project.recruitment.persistence;

import javax.persistence.*;


@Entity(name = "users")
public class User {

    protected User() {}

    public User(String email, String firstName, String lastName, Long ssn, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
        this.password = password;
        this.enabled = true;
    }

    @Id
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = false)
    private String firstName;

    @Column(nullable = false, unique = false)
    private String lastName;

    @Column (nullable = false, unique = false)
    Long ssn;

    @Column(nullable = false, unique = false)
    private String password;

    @Column(nullable = false, unique = false)
    private Boolean enabled;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getSsn() { return ssn; }

    public String getLastName() {
        return lastName;
    }
}

