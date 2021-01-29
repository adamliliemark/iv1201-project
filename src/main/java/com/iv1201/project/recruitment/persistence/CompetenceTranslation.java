package com.iv1201.project.recruitment.persistence;


import javax.persistence.*;

@Entity
public class CompetenceTranslation {

    protected CompetenceTranslation() {}

    public CompetenceTranslation(Competence comp, Language la, String text) {
        this.competence = comp;
        this.language = la;
        this.text = text;
    }

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

    public String getText() {
        return this.text;
    }

    public Language getLanguage() {
        return this.language;
    }
}