package com.iv1201.project.recruitment.domain.unmigratedData;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * When the database is migrated, some users are not valid (not uniquely identifiable, missing email etc.)
 * Later iterations of the product could include "rescue my account" functionality, then this data must be present
 * This class specifies structure for this data.
 */
@Entity
public class UnmigratedAvailability {

    public UnmigratedAvailability(Long availabilityId, Long personId, LocalDate fromDate, LocalDate toDate) {
        this.availabilityId = availabilityId;
        this.personId = personId;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

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

    public Long getPersonId() {
        return personId;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }
}

