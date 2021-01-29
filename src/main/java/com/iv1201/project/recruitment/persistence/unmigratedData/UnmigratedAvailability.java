package com.iv1201.project.recruitment.persistence.unmigratedData;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Date;

/**
 * When the database is migrated, some users are not valid (not uniquely identifiable, missing email etc.)
 * Since we dop not want to lose that information
 * we ask hibernate to create a table for this data so the migration script has somewhere to put it.
 */
@Entity
public class UnmigratedAvailability {

    protected UnmigratedAvailability() {}

    @Id
    @Column(nullable = false, unique = true)
    Long availabilityId;

    @Column
    private Long personId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "DATE", nullable = false, unique = false)
    private LocalDate fromDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "DATE", nullable = false, unique = false)
    private LocalDate toDate;

}

