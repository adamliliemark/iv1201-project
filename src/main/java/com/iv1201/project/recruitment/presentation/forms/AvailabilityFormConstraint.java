package com.iv1201.project.recruitment.presentation.forms;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * Custom constraint for AvailabilityForm.
 * @see AvailabilityFormValidator for validation.
 */
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AvailabilityFormValidator.class)
@Documented
public @interface AvailabilityFormConstraint {
    String message() default "{Availability constraint not met}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}