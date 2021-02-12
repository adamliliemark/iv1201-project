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
    private String lastName;

    @Column
    private String ssn;
}

