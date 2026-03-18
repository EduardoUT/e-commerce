package io.github.eduardout.e_commerce.dto.constraint.validator;

import io.github.eduardout.e_commerce.dto.constraint.annotation.CheckOnlyLetters;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class CheckOnlyLetterValidator implements ConstraintValidator<CheckOnlyLetters, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return true;
        }
        boolean isValid = Pattern.matches("^[A-Za-záéíóúÁÉÍÓÚñÑ]+(?:\\s[A-Za-záéíóúÁÉÍÓÚñÑ]+)*+$", s);

        if (!isValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("""
                            Invalid String argument:
                            - Digits and special characters are not allowed
                            - More than one space is not allowed
                            - It cannot start or end with white spaces
                            """)
                    .addConstraintViolation();
        }

        return Pattern.matches("^[A-Za-záéíóúÁÉÍÓÚñÑ]+(?:\\s[A-Za-záéíóúÁÉÍÓÚñÑ]+)*+$", s);
    }
}
