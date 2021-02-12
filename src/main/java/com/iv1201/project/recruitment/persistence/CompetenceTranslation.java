package com.iv1201.project.recruitment.persistence;


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
    private Long id;

    @ManyToOne
    @JoinColumn
    private Language language;

    @ManyToOne
    @JoinColumn
    private Competence competence;

    @Column
    String text;

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

    /**
     * Retrieves the current value of the text class attribute.
     * @return is the current value of the text class attribute.
     */
    public String getText() {
        return this.text;
    }

    /**
     * Retrieves the current value of the language class attribute.
     * @return is the current value of the language class attribute.
     */
    public Language getLanguage() {
        return this.language;
    }
}