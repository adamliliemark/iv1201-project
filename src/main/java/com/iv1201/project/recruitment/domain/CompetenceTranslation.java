package com.iv1201.project.recruitment.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Represents a stored translation of one specific Competence to one specific Language
 * @see Competence
 * @see Language
 */
@Entity
public class CompetenceTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private Long id;

    @ManyToOne
    @JoinColumn
    @Getter private Language language;

    @ManyToOne
    @JoinColumn
    @Getter private Competence competence;

    @Column
    @Getter String text;

    /**
     * A protected and empty class constructor.
     */
    protected CompetenceTranslation() {}

    /**
     * A class constructor setting the initial values fo the class attributes.
     * @param comp is the initial value of the class attribute competence.
     * @param la is the initial value of the class attribute language.
     * @param text is the initial value of the class attribute text.
     */
    public CompetenceTranslation(Competence comp, Language la, String text) {
        this.competence = comp;
        this.language = la;
        this.text = text;
    }
}