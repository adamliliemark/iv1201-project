package com.iv1201.project.recruitment.persistence;


import javax.persistence.*;

/**
 * Represents a stored Language
 * Maps competenceTranslations to specific languages
 */
@Entity
public class Language {

    protected Language() {}

    public Language(String languageCode, String nativeName) {
        this.languageCode = languageCode;
        this.nativeName = nativeName;
    }


    @Id
    private String languageCode;

    @Column
    String nativeName;

    public String getLanguageCode() {
        return this.languageCode;
    }
}