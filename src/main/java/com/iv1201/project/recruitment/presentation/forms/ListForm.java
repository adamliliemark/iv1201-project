package com.iv1201.project.recruitment.presentation.forms;

/**
 * Stores the parameters entered by the user via the ListView and serves as a DTO class.
 * @see ListFormConstraint for constraints.
 * @see ListFormValidator for validation.
 */
@ListFormConstraint
public class ListForm {

    private String competence;
    private String firstName;
    private String lastName;
    private AvailabilityForm availabilityForm;

    /**
     * Retrieves the current value of the lastName class attribute.
     * @return is the current value of the lastName class attribute.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the class attribute lastName to a new value.
     * @param lastName is the new value of the class attribute lastName.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * A constructor.
     */
    public ListForm(){
        this.availabilityForm = new AvailabilityForm();
    }


    /**
     * Retrieves the current value of the competence class attribute.
     * @return is the current value of the competence class attribute.
     */
    public String getCompetence() {
        return competence;
    }

    /**
     * Sets the class attribute competence to a new value.
     * @param competence is the new value of the class attribute lastName.
     */
    public void setCompetence(String competence) {
        this.competence = competence;
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
     * Retrieves the current value of the availabilityForm class attribute.
     * @return is the current value of the availabilityForm class attribute.
     */
    public AvailabilityForm getAvailabilityForm() {
        return availabilityForm;
    }

    /**
     * Sets the class attribute availabilityForm to a new value.
     * @param availabilityForm is the new value of the class attribute lastName.
     */
    public void setAvailabilityForm(AvailabilityForm availabilityForm) {
        this.availabilityForm = availabilityForm;
    }
}
