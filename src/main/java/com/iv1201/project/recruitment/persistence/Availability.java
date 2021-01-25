package com.iv1201.project.recruitment.persistence;


import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "DATE", nullable = false, unique = false)
    private LocalDate fromDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
}