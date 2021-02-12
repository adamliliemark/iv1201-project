package com.iv1201.project.recruitment.persistence;

import javax.persistence.*;

/**
 * Represents a stored Language
 * Maps competenceTranslations to specific languages
 */
@Entity
public class Language {

    @Id
    private String languageCode;

    @Column
    String nativeName;

    /**
     * An empty and protected constructor.
     */
    protected Language() {}

    /**
     * A full attribute constructor.
     * @param languageCode is the initial value of the class attribute languageCode.
     * @param nativeName is teh initial value of the class attribute nativeName.
     */
    public Language(String languageCode, String nativeName) {
        this.languageCode = languageCode;
        this.nativeName = nativeName;
    }

    /**
     * Retrieves the current value of the languageCode class attribute.
     * @return is the current value of the languageCode class attribute.
     */
    public String getLanguageCode() {
        return this.languageCode;
    }
}