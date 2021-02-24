package com.iv1201.project.recruitment;

import com.iv1201.project.recruitment.presentation.forms.RestoreForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class RestoreFormTest {
    private RestoreForm nullEmail, invalidEmail, blankEmail, okEmail;

    @Autowired
    SmartValidator validator;

    public class MessageHolder {
        String str;
        void setStr(String s) {str = s;}
    }

    @BeforeEach
    public void beforeEach() {
        nullEmail = new RestoreForm();
        nullEmail.setEmail(null);
        invalidEmail = new RestoreForm();
        invalidEmail.setEmail("aaaaaa.com");
        okEmail = new RestoreForm();
        okEmail.setEmail("test@example.com");
        blankEmail = new RestoreForm();
        blankEmail.setEmail("");
    }

    @Test
    public void correct_error_code_on_null_email() {
        BindingResult result = new BeanPropertyBindingResult(nullEmail, "restoreForm");
        var holder = new MessageHolder();
        validator.validate(nullEmail, result);
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.getFieldErrors().size()).isEqualTo(1);
        result.getFieldErrors()
                .forEach(fe -> holder.setStr(fe.getDefaultMessage()));
        System.out.println(result);
        assertThat(holder.str).isEqualTo("error.missingEmail");
    }

    @Test
    public void correct_error_code_on_blank_email() {
        BindingResult result = new BeanPropertyBindingResult(blankEmail, "restoreForm");
        var holder = new MessageHolder();
        validator.validate(blankEmail, result);
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.getFieldErrors().size()).isEqualTo(1);
        result.getFieldErrors()
                .forEach(fe -> holder.setStr(fe.getDefaultMessage()));
        System.out.println(result);
        assertThat(holder.str).isEqualTo("error.missingEmail");
    }


    @Test
    public void correct_error_code_on_malformed_email() {
        BindingResult result = new BeanPropertyBindingResult(invalidEmail, "restoreForm");
        var holder = new MessageHolder();
        validator.validate(invalidEmail, result);
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.getFieldErrors().size()).isEqualTo(1);
        result.getFieldErrors()
                .forEach(fe -> holder.setStr(fe.getDefaultMessage()));
        System.out.println(result);
        assertThat(holder.str).isEqualTo("error.invalidEmail");
    }

    @Test
    public void no_error_on_ok_email() {
        BindingResult result = new BeanPropertyBindingResult(okEmail, "restoreForm");
        var holder = new MessageHolder();
        validator.validate(okEmail, result);
        assertThat(result.hasErrors()).isFalse();
        assertThat(result.getFieldErrors().size()).isEqualTo(0);
        assertThat(holder.str).isNull();
    }
}
