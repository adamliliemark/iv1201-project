package com.iv1201.project.recruitment.persistence.unmigratedData;

import javax.persistence.*;

/**
 * When the database is migrated, some users are not valid (not uniquely identifiable, missing email etc.)
 * Since we dop not want to lose that information
 * we ask hibernate to create a table for this data so the migration script has somewhere to put it.
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

