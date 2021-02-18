package com.iv1201.project.recruitment.presentation.forms;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

/**
 * Validates a <>ListForm</> object.
 * @see ListForm
 * @see ListFormConstraint
 */
public class ListFormValidator implements ConstraintValidator<ListFormConstraint, ListForm> {

    private String oneDate;
    private String dateWrongOrder;

    /**
     * Sets the values of the alternative error messages.
     * @param constraint is the object containing the attributes of the set constraints.
     */
    @Override
    public void initialize(ListFormConstraint constraint){
        oneDate = constraint.oneDate();
        dateWrongOrder = constraint.dateWrongOrder();
    }

    /**
     * Checks if the entered form is valid.
     * @param listForm is the form being validated.
     * @param context is the context of the constraint validator.
     * @return is true if the form is valid. False if not.
     */
    @Override
    public boolean isValid(ListForm listForm, ConstraintValidatorContext context){

        LocalDate from = listForm.getAvailabilityForm().getFrom();
        LocalDate to = listForm.getAvailabilityForm().getTo();

        if((from == null && to == null && listForm.getCompetence().isEmpty() && listForm.getFirstName().isEmpty() && listForm.getLastName().isEmpty()))
            return false;

        if ((from != null && to == null) || (from == null && to != null)){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(oneDate).addConstraintViolation();
            return false;
        }

        if(from != null){
            if (to.isBefore(from)){
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(dateWrongOrder).addConstraintViolation();
                return false;
            }
        }

        return true;
    }
}
