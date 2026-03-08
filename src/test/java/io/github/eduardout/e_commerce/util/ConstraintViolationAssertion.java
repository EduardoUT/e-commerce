package io.github.eduardout.e_commerce.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ConstraintViolationAssertion {

    private final Validator validator;

    public ConstraintViolationAssertion() {
        validator = setUpValidator();
    }

    private Validator setUpValidator() {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            return validatorFactory.getValidator();
        }
    }

    /**
     * <p>
     * The use of this method is intended to test only one constraint validation to classes that use annotations of the
     * package jakarta.validation.constraints.
     * </p>
     * <p>
     * Performs an assertion test on the Set of ConstraintViolation accordingly to the message and propertyPath
     * where the constraint validation was triggered.
     * </p>
     *
     * @param expectedMessage      Expected message assigned on the constraint annotations of the class to be tested
     * @param expectedPropertyPath Expected propertyPath which corresponds to the constraint annotation of the class
     * @param classToValidate      Class with constraint validations of jakarta.validation.constraints
     * @param <T>                  The type of the class to infer the ConstraintViolation
     */
    public <T> void assertConstraintViolations(String expectedMessage, String expectedPropertyPath,
                                               T classToValidate) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(classToValidate);
        assertTrue(isAnyMatchingExpectedMessageAndPropertyPath(constraintViolations, expectedMessage, expectedPropertyPath));
    }

    /**
     * When the data validates class pass the constraint validations, the Set is empty.
     *
     * @param classToValidate Class with constraint validations of jakarta.validation.constraints
     * @param <T>             The type of the class to infer the ConstraintViolation
     */
    public <T> void assertEmptyConstraintViolations(T classToValidate) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(classToValidate);
        assertTrue(constraintViolations.isEmpty());
    }

    /**
     * <p>
     * Evaluates if the message and propertyPath properties of the ConstraintViolation are the expected.
     * </p>
     *
     * @param constraintViolations The collection Set cof ConstraintViolation type.
     * @param expectedMessage      The expected message of the assertion test.
     * @param expectedPropertyPath The expected propertyPath of the assertion test.
     * @param <T>                  The type of the class to validate.
     * @return Returns true if message and propertyPath match with at least one of the expected, otherwise false if
     * the collection is empty or none of the elements match.
     */
    private <T> boolean isAnyMatchingExpectedMessageAndPropertyPath(Set<ConstraintViolation<T>> constraintViolations,
                                                                    String expectedMessage, String expectedPropertyPath) {
        return constraintViolations
                .stream()
                .anyMatch(constraintViolation ->
                        constraintViolation.getMessage().equals(expectedMessage) &&
                                constraintViolation.getPropertyPath().toString().equals(expectedPropertyPath)
                );
    }
}
