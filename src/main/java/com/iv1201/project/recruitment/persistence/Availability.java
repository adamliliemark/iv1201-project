package com.iv1201.project.recruitment.persistence;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Availability {

    protected Availability() {}

    public Availability(LocalDate from, LocalDate to) {
        this.fromDate = from;
        this.toDate = to;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "DATE", nullable = false, unique = false)
    private LocalDate fromDate;

    @Column(columnDefinition = "DATE", nullable = false, unique = false)
    private LocalDate toDate;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn
    private User user;

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }
}