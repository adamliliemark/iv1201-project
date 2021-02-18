package com.iv1201.project.recruitment.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Represents a stored Language
 * Maps competenceTranslations to specific languages
 */
@Entity
public class Language {

    @Id
    @Getter private String languageCode;

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
}