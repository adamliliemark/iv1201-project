package com.iv1201.project.recruitment.domain;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a stored Competence
 */
@Entity
@Transactional
public class Competence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter private Long id;

    @OneToMany(mappedBy="competence", fetch=FetchType.LAZY, orphanRemoval = true, cascade=CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @Getter @Setter private Set<CompetenceTranslation> translations;

    /**
     * Creates a Competence with empty translation list.
     * Allows default constructor to be effect-free and protected.
     * @return created Competence instance
     */
    public static Competence create() {
        Competence c = new Competence();
        c.translations = new HashSet<>();
        return c;
    }
    /**
     * A class constructor.
     */
    protected Competence() {}

    /**
     * Add a new translation for a specified competence
     * @param languageCode the Language of the translation
     * @param localName the local name of the Competence
     * @see Language
     * @see CompetenceTranslation
     */
    public void addTranslation(Language languageCode, String localName) {
        this.translations.add(new CompetenceTranslation(this, languageCode, localName));
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
}