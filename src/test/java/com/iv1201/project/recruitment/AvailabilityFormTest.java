package com.iv1201.project.recruitment;

import com.iv1201.project.recruitment.presentation.forms.AvailabilityForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class AvailabilityFormTest {
    private AvailabilityForm nullFrom, nullTo, fromInPast, toInPast, toBeforeFrom;

    @Autowired
    SmartValidator validator;

    public static class MessageHolder {
        String str;
        void setStr(String s) {str = s;}
    }

    private static AvailabilityForm getOkForm() {
        var form = new AvailabilityForm();
        form.setFrom(LocalDate.of(2023, 1, 1));
        form.setTo(LocalDate.of(2024, 1, 1));
        return form;
    }

    private BindingResult validatorOf(AvailabilityForm form) {
        BindingResult result = new BeanPropertyBindingResult(form, "restoreForm");
        validator.validate(form, result);
        return result;
    }

    @BeforeEach
    public void beforeEach() {
        nullFrom = getOkForm();
        nullFrom.setFrom(null);
        nullTo = getOkForm();
        nullTo.setTo(null);
        fromInPast = getOkForm();
        fromInPast.setFrom(LocalDate.of(2020, 1, 1));
        toInPast = getOkForm();
        toInPast.setTo(LocalDate.of(2020, 1, 1));
        toBeforeFrom = getOkForm();
        toBeforeFrom.setTo(LocalDate.of(2022, 1, 1));
    }

    @Test
    public void correct_error_code_on_null_from() {
        var holder = new MessageHolder();
        var result = validatorOf(nullFrom);
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.getFieldErrors().size()).isEqualTo(1);
        result.getFieldErrors()
                .forEach(fe -> holder.setStr(fe.getDefaultMessage()));
        System.out.println(result);
        assertThat(holder.str).isEqualTo("form.from.null");
    }

    @Test
    public void correct_error_code_on_null_to() {
        var holder = new MessageHolder();
        var result = validatorOf(nullTo);
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.getFieldErrors().size()).isEqualTo(1);
        result.getFieldErrors()
                .forEach(fe -> holder.setStr(fe.getDefaultMessage()));
        System.out.println(result);
        assertThat(holder.str).isEqualTo("form.to.null");
    }

    @Test
    public void correct_error_code_on_from_in_past() {
        var holder = new MessageHolder();
        var result = validatorOf(fromInPast);
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.getFieldErrors().size()).isEqualTo(1);
        result.getFieldErrors()
                .forEach(fe -> holder.setStr(fe.getDefaultMessage()));
        System.out.println(result);
        assertThat(holder.str).isEqualTo("form.from.notFutureOrPresent");
    }


    @Test
    public void correct_error_code_on_to_in_past() {
        var holder = new MessageHolder();
        var result = validatorOf(toInPast);
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.getFieldErrors().size()).isEqualTo(1);
        result.getFieldErrors()
                .forEach(fe -> holder.setStr(fe.getDefaultMessage()));
        System.out.println(result);
        assertThat(holder.str).isEqualTo("form.to.notFutureOrPresent");
    }

    @Test
    public void correct_error_code_on_to_before_from() {
        var holder = new MessageHolder();
        var result = validatorOf(toBeforeFrom);
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.getAllErrors().size()).isEqualTo(1);
        result.getAllErrors()
                .forEach(fe -> holder.setStr(fe.getDefaultMessage()));
        System.out.println(result);
        assertThat(holder.str).isEqualTo("form.availability.datesInWrongOrder");
    }
}
