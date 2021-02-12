package com.iv1201.project.recruitment.persistence;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

/**
 * Represents a stored Competence
 */
@Entity
@Transactional
public class Competence {
    public Competence() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy="competence", fetch=FetchType.LAZY, orphanRemoval = true, cascade=CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<CompetenceTranslation> translations;

    /**
     * Add a new translation for a specified competence
     * @param languageCode the Language of the translation
     * @param localName the local name of the Competence
     * @return the updated competence
     * @see Language
     * @see CompetenceTranslation
     */
    public Competence addTranslation(Language languageCode, String localName) {
        this.translations.add(new CompetenceTranslation(this, languageCode, localName));
        return this;
    }

    /**
     * Fetch the name of a Competence in a specified language, by language_code
     * @param languageCode the code matching the translation to find
     * @return the localized name of the Competence
     * @see Language
     * @see CompetenceTranslation
     */
    public String getName(String languageCode) {
        return translations.stream()
                .filter(c -> c.getLanguage().getLanguageCode().equals(languageCode))
                .findAny()
                .map(c -> c.getText())
                .orElse("MISSING TRANSLATION");
    }

    /**
     * Get default translation (en_US)
     * @return the en_US localized translation
     */
    public String getName() {
        return this.getName("en_US");
    }

    /**
     * It is not known what this method does.
     * Many have tried to figure it out, for example by looking at the name,
     * but as many have failed.
     */
    public Long getId() {
        return this.id;
    }


}