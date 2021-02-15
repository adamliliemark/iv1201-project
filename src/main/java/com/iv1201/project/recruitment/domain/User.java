package com.iv1201.project.recruitment.domain;

import com.iv1201.project.recruitment.domain.Availability;
import com.iv1201.project.recruitment.domain.Competence;
import com.iv1201.project.recruitment.domain.CompetenceProfile;
import com.iv1201.project.recruitment.domain.unmigratedData.UnmigratedPerson;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
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
    private Set<CompetenceProfile> competences;


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
        this.availabilityList = new HashSet<>();
        this.competences = new HashSet<>();
    }

    public User(UnmigratedPerson up) {
        this(up.getEmail(), up.getName(), up.getSurname(), up.getSsn(), up.getPassword());
    }


    /**
     * Adds a new availability period for the <>User</>.
     * @param from is the from date the user is available.
     * @param to is the date until the user is available.
     */
    public void addAvailability(LocalDate from, LocalDate to) {
        if(this.availabilityList == null)
            throw new RuntimeException();
        this.availabilityList.add(new Availability(from, to, this));
    }

    /**
     * Removes an availability from the user.
     * @param availability is the availability being removed.
     */
    public void removeAvailability(Availability availability) {
        this.availabilityList.stream()
                .filter(a -> a.getFromDate().equals(availability.getFromDate())
                        && a.getToDate().equals(availability.getToDate()))
                .findAny().ifPresent(av -> this.availabilityList.remove(av));
    }

    /**
     * Adds a new comptence to the user.
     * @param competence is the competence being added.
     * @param years is the number of years the user has practiced the field the competence describes.
     */
    public void addCompetence(Competence competence, double years) {
        CompetenceProfile comp = this.competences.stream()
                .filter(c -> c.getCompetence().getId().equals(competence.getId()))
                .findAny()
                .orElse(null);
        if(comp != null)
            comp.setYearsOfExperience(years);
        else
            this.competences.add(new CompetenceProfile(competence, years, this));
    }

    /**
     * Removes a competence from the user.
     * @param competence is the competence being removed.
     */
    public void removeCompetence(Competence competence) {
        this.competences.stream()
                .filter(c -> c.getCompetence().getId().equals(competence.getId()))
                .findAny().ifPresent(comp -> this.competences.remove(comp));
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
     * Sets the password hash for the user
     * @param password the new hashed password
     */
    public void setPassword(String password) {
        this.password = password;
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
    public Set<CompetenceProfile> getCompetences() {
        return this.competences;
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
        return this.firstName;
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
    public String getSsn() { return this.ssn; }

    /**
     * Retrieves the current value of the lastName class attribute.
     * @return is the current value of the lastName class attribute.
     */
    public String getLastName() {
        return this.lastName;
    }
}

