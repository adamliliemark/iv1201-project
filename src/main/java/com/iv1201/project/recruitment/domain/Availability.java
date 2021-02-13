package com.iv1201.project.recruitment.domain;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Represents a stored Availability
 * Maps a user to a specific availability interval
 */
@Entity
@Cacheable
@Table(name="Availability", uniqueConstraints = {@UniqueConstraint(columnNames = {"FROM_DATE", "TO_DATE", "USER_EMAIL"})})
public class Availability {

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

    /**
     * Empty class constructor.
     */
    protected Availability() {}

    /**
     * Populated class constructor setting the class variables to their initial values.
     * @param from is the initial value of the class attribute fromDate.
     * @param to is the initial value of the class attribute toDate.
     * @param user is the initial value of the class attribute user.
     */
    public Availability(LocalDate from, LocalDate to, User user) {
        this.fromDate = from;
        this.toDate = to;
        this.user = user;
    }

    /**
     * Retrieves the current value of the fromDate class attribute.
     * @return is the current value of the fromDate class attribute.
     */
    public LocalDate getFromDate() {
        return fromDate;
    }

    /**
     * Retrieves the current value of the toDate class attribute.
     * @return is the current value of the toDate class attribute.
     */
    public LocalDate getToDate() {
        return toDate;
    }

    /**
     * Sets the class attribute fromDate to a new value.
     * @param fromDate is the new value of the class attribute lastName.
     */
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * Sets the class attribute toDate to a new value.
     * @param toDate is the new value of the class attribute lastName.
     */
    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
}