package io.github.eduardout.e_commerce.dto.request;

import io.github.eduardout.e_commerce.dto.ConstraintViolationAssertion;
import io.github.eduardout.e_commerce.entity.User;
import io.github.eduardout.e_commerce.entity.data.builder.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class UserCreationRequestTest {
    private final ConstraintViolationAssertion constraintViolationAssertion = new ConstraintViolationAssertion();
    private User userTest;

    @BeforeEach
    void setUp() {
        userTest = Users.anUser().build();
    }

    static Stream<String> streamOfInvalidNotBlank() {
        return Stream.of(null, "");
    }

    @Test
    void testValidUserCreationRequest() {
        constraintViolationAssertion.assertEmptyConstraintViolations(
                new UserCreationRequest(
                        userTest.getUsername(),
                        userTest.getRole(),
                        userTest.getPassword(),
                        userTest.getPassword(),
                        userTest.getEmail()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidNotBlank")
    void testNotBlankUsername(String username) {
        constraintViolationAssertion.assertConstraintViolation(
                "Username is mandatory",
                "username",
                new UserCreationRequest(
                        username,
                        userTest.getRole(),
                        userTest.getPassword(),
                        userTest.getPassword(),
                        userTest.getEmail()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidUsernameSize")
    void testSizeUsername(String username) {
        constraintViolationAssertion.assertConstraintViolation(
                "Username must be between 4 and 50 characters size",
                "username",
                new UserCreationRequest(
                        username,
                        userTest.getRole(),
                        userTest.getPassword(),
                        userTest.getPassword(),
                        userTest.getEmail()
                )
        );
    }

    static Stream<String> streamOfInvalidUsernameSize() {
        return Stream.of("lodemgardenlodemgardenlodemgardenlodemgardenlodemgarden", "u", "", "pas");
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidNotBlank")
    void testNotBlankRole(String role) {
        constraintViolationAssertion.assertConstraintViolation(
                "Role is mandatory", "role",
                new UserCreationRequest(
                        userTest.getUsername(),
                        role,
                        userTest.getPassword(),
                        userTest.getPassword(),
                        userTest.getEmail()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidPasswords")
    void testInvalidPatternPassword(String password) {
        constraintViolationAssertion.assertConstraintViolation(
                """
                        Password must commit the following requirements:
                        - Must be at least 15 characters and max 20.
                        - At least 1 lowercase and 1 uppercase character.
                        - At least 1 numeric digit.
                        - At least 1 special character.
                        """,
                "password",
                new UserCreationRequest(
                        userTest.getUsername(),
                        userTest.getRole(),
                        password,
                        password,
                        userTest.getEmail()
                )
        );
    }

    static Stream<String> streamOfInvalidPasswords() {
        return Stream.of(
                "t3wHGNrL7ZyFu",
                "%P@FuA4z!pKrQZY8k4&#t",
                "AWCDGS@Y@AY46B%",
                "%gkvjc#@9qejn2a",
                "c$ZdDoCM!okWwUz",
                "u5yUY5qcnJjoEza"
        );
    }

    @Test
    void testPasswordMismatch() {
        constraintViolationAssertion.assertConstraintViolation(
                "Password and password confirmation don't match",
                new UserCreationRequest(
                        userTest.getUsername(),
                        userTest.getRole(),
                        "6N^o57ZAC!@JHJC",
                        "ovPJuKA^^M9optq",
                        userTest.getEmail()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("invalidEmails")
    void testInvalidEmails(String email) {
        constraintViolationAssertion.assertConstraintViolation(
                """
                         Email must commit the following requirements:
                         - Local part (before) @ must be min 1 character and max 64
                         - Domain part (after) @ should be min 1 character and max 64
                         - Cannot contain sequential dots
                         - Cannot contain sequential underscores
                         - Cannot contain sequential hyphen
                        """,
                "email",
                new UserCreationRequest(
                        userTest.getUsername(),
                        userTest.getRole(),
                        userTest.getPassword(),
                        userTest.getPassword(),
                        email
                )
        );
    }

    static Stream<String> invalidEmails() {
        return Stream.of(
                "pepito+promo@empresa.net",
                ".usuario@ejemplo.com",
                "usuario.@ejemplo.com",
                "usuario..prueba@ejemplo.com",
                "usuario@-dominio.com",
                "usuario@dominio-.com",
                "bolt@perrito.3com",
                "algo@dominio.123",
                "nombre apellido@ejemplo.com",
                "user@do..main.com",
                "us..er@dominio.com"
        );
    }

    @ParameterizedTest
    @MethodSource("validEmails")
    void testValidEmails(String email) {
        constraintViolationAssertion.assertEmptyConstraintViolations(
                new UserCreationRequest(
                        userTest.getUsername(),
                        userTest.getRole(),
                        userTest.getPassword(),
                        userTest.getPassword(),
                        email
                )
        );
    }

    static Stream<String> validEmails() {
        return Stream.of(
                "usuario@ejemplo.com",
                "juan.perez@gmail.com",
                "maria_lopez-23@outlook.com",
                "nombre.apellido@ejemplo.org",
                "pepito123@empresa.net",
                "a12345@dominio.org",
                "uusuario_demo@sub.dominio.net",
                "mi-correo@sub.dominio.com",
                "contacto@empresa-aero.aero",
                "x-mas-tree@mi-dominio.space",
                "abc.def@ejemplo.travel",
                "bolt-runner@perrito.xyz",
                "a-named-guy@b.co",
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@dominio.com",
                "a@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.com",
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.com"
        );
    }
}