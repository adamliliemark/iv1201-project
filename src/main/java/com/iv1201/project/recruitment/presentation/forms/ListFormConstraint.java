package com.iv1201.project.recruitment.presentation.forms;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

/**
 * A custom constraint for the class ListForm.
 * @see ListFormValidator for validation specifics.
 */
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ListFormValidator.class)
@Documented
public @interface ListFormConstraint {
    String message() default "noSearch";
    String oneDate() default "oneDate";
    String dateWrongOrder() default  "form.list.wrongOrder";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}