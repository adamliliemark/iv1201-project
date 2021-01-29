package com.iv1201.project.recruitment.persistence;


import javax.persistence.*;
import java.util.List;

@Entity
public class Competence {
    public Competence() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy="competence", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    private List<CompetenceTranslation> translations;

    public Competence addTranslation(Language l, String text) {
        this.translations.add(new CompetenceTranslation(this, l, text));
        return this;
    }

    public String getName(String code) {
        CompetenceTranslation ct = translations.stream()
                .filter(c -> c.getLanguage().getLanguageCode().equals(code))
                .findAny()
                .orElse(null);
        return ct != null ? ct.getText() : "MISSING TRANSLATION";
    }

    public String getName() {
        return this.getName("en_US");
    }

    public Long getId() {
        return this.id;
    }
}