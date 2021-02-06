package com.iv1201.project.recruitment.persistence;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Represents a stored Availability
 * Maps a user to a specific availability interval
 */
@Entity
@Cacheable
@Table(name="Availability",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"FROM_DATE", "TO_DATE", "USER_EMAIL"})})
public class Availability {

    protected Availability() {}

    public Availability(LocalDate from, LocalDate to, User user) {
        this.fromDate = from;
        this.toDate = to;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="FROM_DATE", columnDefinition = "DATE", nullable = false, unique = false)
    private LocalDate fromDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="TO_DATE", columnDefinition = "DATE", nullable = false, unique = false)
    private LocalDate toDate;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "USER_EMAIL")
    private User user;

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
}