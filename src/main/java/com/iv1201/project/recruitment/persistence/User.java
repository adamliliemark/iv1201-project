package com.iv1201.project.recruitment.persistence;

import javax.persistence.*;
import java.util.List;
import java.util.Locale;

/**
 * Represents a stored User
 */
@Entity(name = "users")
public class User {

    protected User() {}

    /**
     * Creates an instance of a user
     * The user can be stored
     * @param email the email
     * @param firstName the first name
     * @param lastName the last name
     * @param ssn the social securty number as a string
     * @param password the cleartext password
     */
    public User(String email, String firstName, String lastName, String ssn, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
        this.password = password;
        this.enabled = true;
    }

    @Transient
    Locale locale;

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Id
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = false)
    private String firstName;

    @Column(nullable = false, unique = false)
    private String lastName;

    @Column (nullable = false, unique = false)
    String ssn;

    @Column(nullable = true, unique = false)
    private String password;

    @Column(nullable = false, unique = false)
    private Boolean enabled;

    @Transient
    private Availability availability;


    @OneToMany(mappedBy="user", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    private List<CompetenceProfile> competences;

    public List<CompetenceProfile> getCompetences() {
        return competences;
    }

    public void addCompetence(Competence competence, double years) {
        if(competences == null)
            throw new RuntimeException();

        CompetenceProfile comp = competences.stream()
                .filter(c -> c.getCompetence().getName().equals(competence.getName()))
                .findAny()
                .orElse(null);
        if(comp != null) {
            comp.setYearsOfExperience(years);
        } else {
            this.competences.add(new CompetenceProfile(competence, years, this));
        }
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSsn() { return ssn; }

    public String getLastName() {
        return lastName;
    }
}

