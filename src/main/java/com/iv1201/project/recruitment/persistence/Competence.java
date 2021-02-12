package com.iv1201.project.recruitment.persistence;

import javax.persistence.*;
import java.util.List;

/**
 * Represents a stored Competence
 */
@Entity
public class Competence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy="competence", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    private List<CompetenceTranslation> translations;

    /**
     * A class constructor.
     */
    public Competence() {}

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
        CompetenceTranslation ct = translations.stream()
                .filter(c -> c.getLanguage().getLanguageCode().equals(languageCode))
                .findAny()
                .orElse(null);
        return ct != null ? ct.getText() : "MISSING TRANSLATION";
    }

    /**
     * Sets the class attribute id to a new value.
     * @param id is the new value of the class attribute lastName.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get default translation (en_US)
     * @return the en_US localized translation
     */
    public String getName() {
        return this.getName("en_US");
    }

    /**
     * Retrieves the current value of the id class attribute.
     * @return is the current value of the id class attribute.
     */
    public Long getId() {
        return this.id;
    }
}