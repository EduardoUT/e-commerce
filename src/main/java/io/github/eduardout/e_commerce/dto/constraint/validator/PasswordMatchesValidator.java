package io.github.eduardout.e_commerce.dto.constraint.validator;

import io.github.eduardout.e_commerce.dto.constraint.annotation.PasswordMatches;
import io.github.eduardout.e_commerce.dto.request.UserCreationRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserCreationRequest> {
    @Override
    public boolean isValid(UserCreationRequest userCreationRequest, ConstraintValidatorContext constraintValidatorContext) {
        String password = userCreationRequest.password();
        String passwordConfirmation = userCreationRequest.passwordConfirmation();
        if (password == null || passwordConfirmation == null) {
            return true;
        }
        return password.equals(passwordConfirmation);
    }
}
