package com.iv1201.project.recruitment.domain.unmigratedData;

import javax.persistence.*;

/**
 * When the database is migrated, some users are not valid (not uniquely identifiable, missing email etc.)
 * Later iterations of the product could include "rescue my account" functionality, then this data must be present
 * This class specifies structure for this data.
 */
@Entity
public class UnmigratedCompetenceProfile {
    protected UnmigratedCompetenceProfile(){};

    @Id
    private Long competenceProfileId;

    @Column(precision=4, scale=2)
    private double yearsOfExperience;

    @Column
    private String competenceName;

    @ManyToOne
    @JoinColumn(nullable = false)
    private UnmigratedPerson personId;
}
