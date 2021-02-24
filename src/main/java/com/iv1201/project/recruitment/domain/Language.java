package com.iv1201.project.recruitment.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        return this.nativeName.hashCode() + this.languageCode.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language = (Language) o;
        return Objects.equals(languageCode, language.languageCode) && Objects.equals(nativeName, language.nativeName);
    }
}