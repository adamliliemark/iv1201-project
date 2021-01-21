package com.iv1201.project.recruitment.persistence;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Availability {

    protected Availability() {}

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
}