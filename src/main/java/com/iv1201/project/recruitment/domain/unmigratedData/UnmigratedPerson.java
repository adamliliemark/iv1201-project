package com.iv1201.project.recruitment.domain.unmigratedData;

import javax.persistence.*;

/**
 * When the database is migrated, some users are not valid (not uniquely identifiable, missing email etc.)
 * Later iterations of the product could include "rescue my account" functionality, then this data must be present
 * This class specifies structure for this data.
 */
@Entity
public class UnmigratedPerson {

    protected UnmigratedPerson() {}

    public UnmigratedPerson(Long personId, Long role_id, String username, String email, String name, String surname, String password, String ssn) {
        this.personId = personId;
        this.role_id = role_id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.ssn = ssn;
    }

    @Id
    @Column(nullable = false, unique = true)
    Long personId;

    @Column
    private Long role_id;

    @Column
    private String username;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String password;

    @Column
    private String ssn;

    public Long getPersonId() {
        return personId;
    }

    public Long getRole_id() {
        return role_id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public String getSsn() {
        return ssn;
    }
}

