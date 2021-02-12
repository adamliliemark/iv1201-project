package com.iv1201.project.recruitment.persistence;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Represents a stored User
 */
@Entity(name = "users")
public class User {

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

    @OneToMany(mappedBy = "user", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Availability> availabilityList;

    @Transient
    Locale locale;

    @OneToMany(mappedBy="user", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    private List<CompetenceProfile> competences;


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


    /**
     * Adds a new availability period for the <>User</>.
     * @param from is the from date the user is available.
     * @param to is the date until the user is available.
     */
    public void addAvailability(LocalDate from, LocalDate to) {
        if(availabilityList == null)
            throw new RuntimeException();
        this.availabilityList.add(new Availability(from, to, this));
    }

    /**
     * Adds a new competence to the <>User</> object.
     * @param competence is the competence being added.
     * @param years is the number of years the user has practiced the competence.
     */
    public void addCompetence(Competence competence, double years) {

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

    /**
     * Retrieves the current value of the locale class attribute.
     * @return is the current value of the locale class attribute.
     */
    public Locale getLocale() {
        return this.locale;
    }

    /**
     * Sets the class attribute locale to a new value.
     * @param locale is the new value of the class attribute lastName.
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Retrieves the current value of the availabilityList class attribute.
     * @return is the current value of the availabilityList class attribute.
     */
    public Set<Availability> getAvailabilityList() { return availabilityList; }

    /**
     * Retrieves the current value of the competences class attribute.
     * @return is the current value of the competences class attribute.
     */
    public List<CompetenceProfile> getCompetences() {
        return competences;
    }

    /**
     * Retrieves the current value of the email class attribute.
     * @return is the current value of the email class attribute.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the class attribute email to a new value.
     * @param email is the new value of the class attribute lastName.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the current value of the firstName class attribute.
     * @return is the current value of the firstName class attribute.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the class attribute firstName to a new value.
     * @param firstName is the new value of the class attribute lastName.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves the current value of the ssn class attribute.
     * @return is the current value of the ssn class attribute.
     */
    public String getSsn() { return ssn; }

    /**
     * Retrieves the current value of the lastName class attribute.
     * @return is the current value of the lastName class attribute.
     */
    public String getLastName() {
        return lastName;
    }
}

