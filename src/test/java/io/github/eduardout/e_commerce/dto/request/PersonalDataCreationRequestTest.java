package io.github.eduardout.e_commerce.dto.request;

import io.github.eduardout.e_commerce.dto.ConstraintViolationAssertion;
import io.github.eduardout.e_commerce.entity.PersonalData;
import io.github.eduardout.e_commerce.entity.data.builder.PersonalDatas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PersonalDataCreationRequestTest {
    private static final String CHECK_ONLY_LETTERS_MESSAGE = """
            Invalid String argument:
            - Digits and special characters are not allowed
            - More than one space is not allowed
            - It cannot start or end with white spaces
            """;
    private final ConstraintViolationAssertion constraintViolationAssertion = new ConstraintViolationAssertion();
    private PersonalData personalDataTest;

    @BeforeEach
    void setUp() {
        personalDataTest = PersonalDatas.aPersonalData().build();
    }

    static Stream<String> streamOfInvalidStrings() {
        return Stream.of(
                "Lomc6-.. ",
                " --.sssd45",
                "sl,l--. ",
                "bot505-{}",
                "{,ñsña--.",
                "Li Chung  Yong"
        );
    }

    static Stream<String> streamOfInvalidSizeStrings() {
        return Stream.of(
                "Juaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaannnnnnn",
                "Marcossssssssssssssssssssssssssssssssssss",
                "Paco Damascoooooooooooooooooooooooooooooo"
        );
    }

    @Test
    void testValidPersonalDataCreationRequest() {
        constraintViolationAssertion.assertEmptyConstraintViolations(
                new PersonalDataCreationRequest(
                        personalDataTest.getFirstName(),
                        personalDataTest.getMaternalSurname(),
                        personalDataTest.getPaternalSurname(),
                        personalDataTest.getBirthDate(),
                        personalDataTest.getPhoneNumber()
                )
        );
    }

    @Test
    void testNotNullFirstName() {
        constraintViolationAssertion.assertConstraintViolation(
                "First name is mandatory",
                "firstName",
                new PersonalDataCreationRequest(
                        null,
                        personalDataTest.getMaternalSurname(),
                        personalDataTest.getPaternalSurname(),
                        personalDataTest.getBirthDate(),
                        personalDataTest.getPhoneNumber()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidStrings")
    void testCheckOnlyLettersOnName(String firstName) {
        constraintViolationAssertion.assertConstraintViolation(
                CHECK_ONLY_LETTERS_MESSAGE,
                "firstName",
                new PersonalDataCreationRequest(
                        firstName,
                        personalDataTest.getMaternalSurname(),
                        personalDataTest.getPaternalSurname(),
                        personalDataTest.getBirthDate(),
                        personalDataTest.getPhoneNumber()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidSizeStrings")
    void testSizeFirstName(String firstName) {
        constraintViolationAssertion.assertConstraintViolation(
                "First name is out of range",
                "firstName",
                new PersonalDataCreationRequest(
                        firstName,
                        personalDataTest.getMaternalSurname(),
                        personalDataTest.getPaternalSurname(),
                        personalDataTest.getBirthDate(),
                        personalDataTest.getPhoneNumber()
                )
        );
    }

    @Test
    void testNotNullMaternalSurname() {
        constraintViolationAssertion.assertConstraintViolation(
                "Maternal surname is mandatory",
                "maternalSurname",
                new PersonalDataCreationRequest(
                        personalDataTest.getFirstName(),
                        null,
                        personalDataTest.getPaternalSurname(),
                        personalDataTest.getBirthDate(),
                        personalDataTest.getPhoneNumber()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidStrings")
    void testCheckOnlyLettersOnMaternalSurname(String maternalSurname) {
        constraintViolationAssertion.assertConstraintViolation(
                CHECK_ONLY_LETTERS_MESSAGE,
                "maternalSurname",
                new PersonalDataCreationRequest(
                        personalDataTest.getFirstName(),
                        maternalSurname,
                        personalDataTest.getPaternalSurname(),
                        personalDataTest.getBirthDate(),
                        personalDataTest.getPhoneNumber()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidSizeStrings")
    void testSizeMaternalSurname(String maternalSurname) {
        constraintViolationAssertion.assertConstraintViolation(
                "Maternal surname is out of range",
                "maternalSurname",
                new PersonalDataCreationRequest(
                        personalDataTest.getFirstName(),
                        maternalSurname,
                        personalDataTest.getPaternalSurname(),
                        personalDataTest.getBirthDate(),
                        personalDataTest.getPhoneNumber()
                )
        );
    }

    @Test
    void testNotNullPaternalSurname() {
        constraintViolationAssertion.assertConstraintViolation(
                "Paternal surname is mandatory",
                "paternalSurname",
                new PersonalDataCreationRequest(
                        personalDataTest.getFirstName(),
                        personalDataTest.getMaternalSurname(),
                        null,
                        personalDataTest.getBirthDate(),
                        personalDataTest.getPhoneNumber()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidStrings")
    void testCheckOnlyLettersOnPaternalSurname(String paternalSurname) {
        constraintViolationAssertion.assertConstraintViolation(
                CHECK_ONLY_LETTERS_MESSAGE,
                "paternalSurname",
                new PersonalDataCreationRequest(
                        personalDataTest.getFirstName(),
                        personalDataTest.getMaternalSurname(),
                        paternalSurname,
                        personalDataTest.getBirthDate(),
                        personalDataTest.getPhoneNumber()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidSizeStrings")
    void testSizePaternalSurname(String paternalSurname) {
        constraintViolationAssertion.assertConstraintViolation(
                "Paternal surname is out of range",
                "paternalSurname",
                new PersonalDataCreationRequest(
                        personalDataTest.getFirstName(),
                        personalDataTest.getMaternalSurname(),
                        paternalSurname,
                        personalDataTest.getBirthDate(),
                        personalDataTest.getPhoneNumber()
                )
        );
    }

    @Test
    void testNotNullBirthDate() {
        constraintViolationAssertion.assertConstraintViolation(
                "Birth date is mandatory",
                "birthDate",
                new PersonalDataCreationRequest(
                        personalDataTest.getFirstName(),
                        personalDataTest.getMaternalSurname(),
                        personalDataTest.getPaternalSurname(),
                        null,
                        personalDataTest.getPhoneNumber()
                )
        );
    }

    @Test
    void testBirthDateAge() {
        IllegalArgumentException illegalArgumentException;
        illegalArgumentException = assertThrowsExactly(IllegalArgumentException.class, () ->
                new PersonalDataCreationRequest(
                        personalDataTest.getFirstName(),
                        personalDataTest.getMaternalSurname(),
                        personalDataTest.getPaternalSurname(),
                        LocalDate.of(2019, 2, 23),
                        personalDataTest.getPhoneNumber()
                ));

        assertEquals("You are under age", illegalArgumentException.getMessage());
    }

    @Test
    void testNotNullPhoneNumber() {
        constraintViolationAssertion.assertConstraintViolation(
                "Phone number is mandatory",
                "phoneNumber",
                new PersonalDataCreationRequest(
                        personalDataTest.getFirstName(),
                        personalDataTest.getMaternalSurname(),
                        personalDataTest.getPaternalSurname(),
                        personalDataTest.getBirthDate(),
                        null
                )
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidPhoneNumbers")
    void testPatternPhoneNumber(String phoneNumber) {
        constraintViolationAssertion.assertConstraintViolation(
                """
                        Phone number is invalid:
                        - It should not start or end whit white spaces
                        - Use only single hyphens as separators
                        - White spaces aren't allowed
                        - Plus symbol is optional
                        - Dialing code (area code) is mandatory
                        """,
                "phoneNumber",
                new PersonalDataCreationRequest(
                        personalDataTest.getFirstName(),
                        personalDataTest.getMaternalSurname(),
                        personalDataTest.getPaternalSurname(),
                        personalDataTest.getBirthDate(),
                        phoneNumber
                )
        );
    }

    static Stream<String> streamOfInvalidPhoneNumbers() {
        return Stream.of(
                "55 - 45 - 22 - 22",
                "52 45 456 65 23 112",
                "++55-999--75-45-41",
                "43  44 12 32 21 20",
                " 55 512 55 33 22",
                " 22 33 11 22 44 55 ",
                "22 33 11 22 44 55 "
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidSizePhoneNumbers")
    void testSizePhoneNumber(String phoneNumber) {
        constraintViolationAssertion.assertConstraintViolation(
                "Out of range, phone number must be between 10 and 24 digits including area code",
                "phoneNumber",
                new PersonalDataCreationRequest(
                        personalDataTest.getFirstName(),
                        personalDataTest.getMaternalSurname(),
                        personalDataTest.getPaternalSurname(),
                        personalDataTest.getBirthDate(),
                        phoneNumber
                )
        );
    }

    static Stream<String> streamOfInvalidSizePhoneNumbers() {
        return Stream.of(
                "+522462254645845464546646",
                "5548564654654564464655455",
                "52456578",
                "5512354"
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfValidPhoneNumbers")
    void testValidPhoneNumber(String phoneNumber) {
        constraintViolationAssertion.assertEmptyConstraintViolations(
                new PersonalDataCreationRequest(
                        personalDataTest.getFirstName(),
                        personalDataTest.getMaternalSurname(),
                        personalDataTest.getPaternalSurname(),
                        personalDataTest.getBirthDate(),
                        phoneNumber
                )
        );
    }

    static Stream<String> streamOfValidPhoneNumbers() {
        return Stream.of(
                "1-684-48-45-78-41-44",
                "+1-264-88-44-88-22-44",
                "+524588994422",
                "5522445522",
                "744-22-44-55-45",
                "+44-1534-45-22-55-45-44",
                "+1-45-78-89-11-20"
        );
    }
}