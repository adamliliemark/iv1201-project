package com.iv1201.project.recruitment.presentation.forms;

import com.iv1201.project.recruitment.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.requireNonNull;

/**
 * Validation of AvailabilityForm.
 * @see AvailabilityForm
 * @see AvailabilityFormConstraint
 */
public class AvailabilityFormValidator implements ConstraintValidator<AvailabilityFormConstraint, AvailabilityForm> {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    /**
     * Initialize the constraint.
     * @param constraint the constraint to initialize.
     */
    public void initialize(AvailabilityFormConstraint constraint) {}

    /**
     * Check if the form is valid.
     *
     * Checks:
     * For null-values, which is already a constraint in the AvailabilityForm class,
     * but breaks the following checks; hence a double check.
     * For toDate before fromDate
     * For duplicates in database
     *
     * @param availabilityForm incoming object.
     * @param context of ConstraintValidator.
     * @return true if form is valid, false otherwise
     */
    public boolean isValid(AvailabilityForm availabilityForm, ConstraintValidatorContext context) {
        requireNonNull(availabilityForm);
        if(availabilityForm.getFrom() == null || availabilityForm.getTo() == null) {
            return false;
        }
        return !availabilityForm.getTo().isBefore(availabilityForm.getFrom());
    }
}