package io.github.eduardout.e_commerce.dto.request;

import io.github.eduardout.e_commerce.util.ConstraintViolationAssertion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserCreationRequestTest {

    private static final String USERNAME_TEST = "usernameTest";
    private static final String ROLE_TEST = "USER";
    private static final String PASSWORD_TEST = "t3wHGNrL7ZyFuv@";
    private static final String EMAIL_TEST = "username_test@gmail.com";
    private ConstraintViolationAssertion constraintViolationAssertion;
    private final String expectedPropertyPathOnUsername = "username";
    private final String expectedMessageOnPassword = """
            Password must commit the following requirements:
            - Must be at least 15 characters and max 20.
            - At least 1 lowercase and 1 uppercase character.
            - At least 1 numeric digit.
            - At least 1 special character.
            """;
    private final String expectedPropertyPathOnPassword = "password";

    @BeforeEach
    void setUp() {
        constraintViolationAssertion = new ConstraintViolationAssertion();
    }

    @DisplayName("Should test  when username is blank")
    @Test
    void testInvalidUsername() {
        constraintViolationAssertion.assertConstraintViolations(
                "Username is mandatory", expectedPropertyPathOnUsername,
                new UserCreationRequest(
                        "",
                        ROLE_TEST,
                        PASSWORD_TEST,
                        PASSWORD_TEST,
                        EMAIL_TEST
                )
        );
    }

    @DisplayName("Should test  when username is out of range")
    @Test
    void testInvalidUsernameMinMaxSize() {
        String expectedMessage = "Username must be between 4 and 50 characters size";
        constraintViolationAssertion.assertConstraintViolations(
                expectedMessage, expectedPropertyPathOnUsername,
                new UserCreationRequest(
                        "lodemgardenlodemgardenlodemgardenlodemgardenlodemgarden",
                        ROLE_TEST,
                        PASSWORD_TEST,
                        PASSWORD_TEST,
                        EMAIL_TEST
                )
        );

        constraintViolationAssertion.assertConstraintViolations(
                expectedMessage, expectedPropertyPathOnUsername,
                new UserCreationRequest(
                        "u",
                        ROLE_TEST,
                        PASSWORD_TEST,
                        PASSWORD_TEST,
                        EMAIL_TEST
                )
        );
    }


    @DisplayName("Should test when role is blank")
    @Test
    void testInvalidRole() {
        constraintViolationAssertion.assertConstraintViolations(
                "Role is mandatory", "role",
                new UserCreationRequest(
                        USERNAME_TEST,
                        "",
                        PASSWORD_TEST,
                        PASSWORD_TEST,
                        EMAIL_TEST
                )
        );
    }

    @DisplayName("Should test invalid password when out of range")
    @Test
    void testInvalidPasswordMinMaxSize() {
        // When password length is less than 15
        constraintViolationAssertion.assertConstraintViolations(
                expectedMessageOnPassword, expectedPropertyPathOnPassword,
                new UserCreationRequest(
                        USERNAME_TEST,
                        ROLE_TEST,
                        "t3wHGNrL7ZyFu",
                        "t3wHGNrL7ZyFu",
                        EMAIL_TEST
                )
        );

        // When password length is greater than 20
        constraintViolationAssertion.assertConstraintViolations(
                expectedMessageOnPassword, expectedPropertyPathOnPassword,
                new UserCreationRequest(
                        USERNAME_TEST,
                        ROLE_TEST,
                        "%P@FuA4z!pKrQZY8k4&#t",
                        "%P@FuA4z!pKrQZY8k4&#t",
                        EMAIL_TEST
                )
        );
    }

    @DisplayName("Should test invalid password when don't have at least 1 lower or upper case character")
    @Test
    void testInvalidPasswordUpperAndLowerCase() {
        //When password don't have at least 1 lower case character
        constraintViolationAssertion.assertConstraintViolations(
                expectedMessageOnPassword, expectedPropertyPathOnPassword,
                new UserCreationRequest(
                        USERNAME_TEST,
                        ROLE_TEST,
                        "AWCDGS@Y@AY46B%",
                        "AWCDGS@Y@AY46B%",
                        EMAIL_TEST
                )
        );

        //When password don't have at least 1 upper case character
        constraintViolationAssertion.assertConstraintViolations(
                expectedMessageOnPassword, expectedPropertyPathOnPassword,
                new UserCreationRequest(
                        USERNAME_TEST,
                        ROLE_TEST,
                        "%gkvjc#@9qejn2a",
                        "%gkvjc#@9qejn2a",
                        EMAIL_TEST
                )
        );
    }

    @DisplayName("Should test invalid password when don't have at least one numeric digit")
    @Test
    void testInvalidPasswordNumericDigit() {
        constraintViolationAssertion.assertConstraintViolations(
                expectedMessageOnPassword, expectedPropertyPathOnPassword,
                new UserCreationRequest(
                        USERNAME_TEST,
                        ROLE_TEST,
                        "c$ZdDoCM!okWwUz",
                        "c$ZdDoCM!okWwUz",
                        EMAIL_TEST
                )
        );
    }

    @DisplayName("Should test invalid password when don't have at least one special character")
    @Test
    void testInvalidPasswordSpecialCharacter() {
        constraintViolationAssertion.assertConstraintViolations(
                expectedMessageOnPassword, expectedPropertyPathOnPassword,
                new UserCreationRequest(
                        USERNAME_TEST,
                        ROLE_TEST,
                        "u5yUY5qcnJjoEza",
                        "u5yUY5qcnJjoEza",
                        EMAIL_TEST
                )
        );
    }

    @DisplayName("Should throw IllegalArgumentException when password and passwordConfirmation don't match")
    @Test
    void testWhenPasswordNotMatch() {
        IllegalArgumentException e = assertThrowsExactly(IllegalArgumentException.class, () ->
                new UserCreationRequest(
                        USERNAME_TEST,
                        ROLE_TEST,
                        "6N^o57ZAC!@JHJC",
                        "ovPJuKA^^M9optq",
                        EMAIL_TEST
                ));
        assertEquals("Password and password confirmation don't match", e.getMessage());
    }

    @DisplayName("Should test when email is invalid")
    @ParameterizedTest
    @MethodSource(value = "invalidEmails")
    void testInvalidEmails(UserCreationRequest userCreationRequest) {
        String expectedMessage = """
                 Email must commit the following requirements:
                 - Username (before) @ must be at least 6 characters and max 30.
                 - Domain must be max 62 characters.
                 - Cannot contain sequential dots
                 - Cannot contain sequential underscores
                 - Cannot contain sequential hyphen
                """;
        String expectedPropertyPath = "email";
        constraintViolationAssertion.assertConstraintViolations(expectedMessage, expectedPropertyPath, userCreationRequest);
    }

    static Stream<UserCreationRequest> invalidEmails() {
        return Stream.of(
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "pepito+promo@empresa.net"),
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, ".usuario@ejemplo.com"),
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "usuario.@ejemplo.com"),
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "usuario..prueba@ejemplo.com"),
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "usuario@-dominio.com"),
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "usuario@dominio-.com"),
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "bolt@perrito.3com"),
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "algo@dominio.123"),
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "nombre apellido@ejemplo.com"),
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "user@do..main.com"),
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "us..er@dominio.com")
        );
    }

    @DisplayName("Should test when email is valid")
    @ParameterizedTest
    @MethodSource(value = "validEmails")
    void testValidEmails(UserCreationRequest userCreationRequest) {
        constraintViolationAssertion.assertEmptyConstraintViolations(userCreationRequest);
    }

    static Stream<UserCreationRequest> validEmails() {
        return Stream.of(
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "usuario@ejemplo.com"),
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "juan.perez@gmail.com"),
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "maria_lopez-23@outlook.com"),
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "nombre.apellido@ejemplo.org"),
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "pepito123@empresa.net"),
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "a12345@dominio.org"),
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "uusuario_demo@sub.dominio.net"),
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "mi-correo@sub.dominio.com"),
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "contacto@empresa-aero.aero"),
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "x-mas-tree@mi-dominio.space"),
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "abc.def@ejemplo.travel"),
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "bolt-runner@perrito.xyz"),
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "a-named-guy@b.co"),
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@dominio.com"),
                new UserCreationRequest(USERNAME_TEST, ROLE_TEST, PASSWORD_TEST, PASSWORD_TEST, "a@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.com")
        );
    }
}