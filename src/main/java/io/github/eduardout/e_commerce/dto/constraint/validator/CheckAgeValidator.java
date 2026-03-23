package io.github.eduardout.e_commerce.dto.constraint.validator;

import io.github.eduardout.e_commerce.dto.constraint.annotation.CheckAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class CheckAgeValidator implements ConstraintValidator<CheckAge, LocalDate> {
    private static final int MINIMUM_AGE = 18;

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {
        if (birthDate == null) {
            return true;
        }
        return Period.between(birthDate, LocalDate.now()).getYears() >= MINIMUM_AGE;
    }
}
