package com.iv1201.project.recruitment.domain;


import lombok.Getter;
import lombok.Setter;
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
    @Getter @Setter private LocalDate fromDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="TO_DATE", columnDefinition = "DATE", nullable = false, unique = false)
    @Getter @Setter private LocalDate toDate;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "USER_EMAIL")
    @Getter private User user;

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
}