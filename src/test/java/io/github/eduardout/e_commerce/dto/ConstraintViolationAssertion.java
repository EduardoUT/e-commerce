package io.github.eduardout.e_commerce.dto;

import jakarta.validation.*;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Initialize this class once and reuse its reference to test Jakarta Bean Validation constraints on a class, record
 * or interface attributes or parameter values.
 */
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
     * The use of this method is intended to test only one constraint validation to records that use annotations of the
     * package jakarta.validation.constraints.
     * </p>
     * <p>
     * Performs two assertions test on the Set of ConstraintViolation accordingly to the message and propertyPath
     * where the constraint validation was triggered.
     * </p>
     *
     * @param expectedMessage         Expected message assigned on the constraint annotation of the record to be tested
     * @param expectedParameterRecord Corresponds to the propertyPath resultant on the ConstraintViolation
     * @param recordToValidate        Record with Jakarta Bean Validation annotations
     * @param <T>                     The type of the record to infer the ConstraintViolation
     */
    public <T> void assertConstraintViolation(String expectedMessage,
                                              String expectedParameterRecord,
                                              T recordToValidate) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(recordToValidate);

        Optional<ConstraintViolation<T>> actualConstraintViolation = getActualConstraintViolation(
                constraintViolations,
                expectedMessage,
                expectedParameterRecord
        );

        assertAll(
                () -> assertTrue(actualConstraintViolation.isPresent(),
                        "No ConstraintViolation found with message %n '%s' %n on property '%s'"
                                .formatted(expectedMessage, expectedParameterRecord)),
                () -> assertEquals(expectedMessage, actualConstraintViolation
                                .map(ConstraintViolation::getMessage)
                                .orElse(null),
                        "ConstraintViolation message don't match with expected"),
                () -> assertEquals(expectedParameterRecord, actualConstraintViolation
                                .map(cv -> cv.getPropertyPath().toString())
                                .orElse(null),
                        "ConstraintViolation property path don't match with expected")
        );
    }

    /**
     * When all the parameters pass successfully all the constraint validations, the assertion simply evaluates an empty
     * Set of ConstraintViolation.
     *
     * @param recordToValidate Record with Jakarta Bean Validation annotations.
     * @param <T>              The type of the record to infer the ConstraintViolation
     */
    public <T> void assertEmptyConstraintViolations(T recordToValidate) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(recordToValidate);
        assertTrue(constraintViolations.isEmpty(), getConstraintViolationDetail(constraintViolations));
    }

    private <T> String getConstraintViolationDetail(Set<ConstraintViolation<T>> constraintViolations) {
        StringBuilder constraintViolationDetails = new StringBuilder();
        constraintViolationDetails
                .append(constraintViolations.size())
                .append(" Constraint Violations detected: \n");

        constraintViolations.forEach(cv ->
                constraintViolationDetails
                        .append(cv)
                        .append("\n\n"));
        return constraintViolationDetails.toString();
    }

    private <T> Optional<ConstraintViolation<T>> getActualConstraintViolation(
            Set<ConstraintViolation<T>> constraintViolations,
            String expectedMessage,
            String expectedPropertyPath) {
        return constraintViolations
                .stream()
                .filter(cv -> expectedMessage.equals(cv.getMessage()))
                .filter(cv -> expectedPropertyPath.equals(cv.getPropertyPath().toString()))
                .findAny();
    }
}
