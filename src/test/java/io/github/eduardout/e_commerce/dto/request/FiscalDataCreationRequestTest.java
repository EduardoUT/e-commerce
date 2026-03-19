package io.github.eduardout.e_commerce.dto.request;

import io.github.eduardout.e_commerce.dto.ConstraintViolationAssertion;
import io.github.eduardout.e_commerce.entity.FiscalData;
import io.github.eduardout.e_commerce.entity.data.builder.FiscalDatas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class FiscalDataCreationRequestTest {
    private FiscalData fiscalDataTest;
    private final ConstraintViolationAssertion constraintViolationAssertion = new ConstraintViolationAssertion();

    @BeforeEach
    void setUp() {
        fiscalDataTest = FiscalDatas.aFiscalData().build();
    }

    @Test
    void testValidFiscalDataCreationRequest() {
        constraintViolationAssertion.assertEmptyConstraintViolations(
                new FiscalDataCreationRequest(
                        fiscalDataTest.getRfc(),
                        fiscalDataTest.getRegimeTax(),
                        fiscalDataTest.getFiscalAddress()
                )
        );
    }

    @Test
    void testNotNullRfc() {
        constraintViolationAssertion.assertConstraintViolation(
                "RFC is mandatory",
                "rfc",
                new FiscalDataCreationRequest(
                        null,
                        fiscalDataTest.getRegimeTax(),
                        fiscalDataTest.getFiscalAddress()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidRfc")
    void testPatternRfc(String rfc) {
        constraintViolationAssertion.assertConstraintViolation(
                "Invalid RFC",
                "rfc",
                new FiscalDataCreationRequest(
                        rfc,
                        fiscalDataTest.getRegimeTax(),
                        fiscalDataTest.getFiscalAddress()
                )
        );
    }

    static Stream<String> streamOfInvalidRfc() {
        return Stream.of(
                "JKJJK4SJAS4556",
                "NR4SN4556DSAS",
                "D4SD565455D2"
        );
    }


    @Test
    void testNotNullRegimeTax() {
        constraintViolationAssertion.assertConstraintViolation(
                "Regime tax is mandatory",
                "regimeTax",
                new FiscalDataCreationRequest(
                        fiscalDataTest.getRfc(),
                        null,
                        fiscalDataTest.getFiscalAddress()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidRegimeTaxes")
    void testPatternRegimeTax(String regimeTax) {
        constraintViolationAssertion.assertConstraintViolation(
                "Regime tax must have 3 digits",
                "regimeTax",
                new FiscalDataCreationRequest(
                        fiscalDataTest.getRfc(),
                        regimeTax,
                        fiscalDataTest.getFiscalAddress()
                )
        );
    }

    static Stream<String> streamOfInvalidRegimeTaxes() {
        return Stream.of(
                "4566",
                "S4566",
                "D4566",
                "55"
        );
    }

    @Test
    void testNotNullFiscalAddress() {
        constraintViolationAssertion.assertConstraintViolation(
                "Fiscal address is mandatory",
                "fiscalAddress",
                new FiscalDataCreationRequest(
                        fiscalDataTest.getRfc(),
                        fiscalDataTest.getRegimeTax(),
                        null
                )
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidStringSize")
    void testSizeFiscalAddress(String fiscalAddress) {
        constraintViolationAssertion.assertConstraintViolation(
                "Fiscal address out of range",
                "fiscalAddress",
                new FiscalDataCreationRequest(
                        fiscalDataTest.getRfc(),
                        fiscalDataTest.getRegimeTax(),
                        fiscalAddress
                )
        );
    }

    static Stream<String> streamOfInvalidStringSize() {
        return Stream.of(
                "SSSSSSSSSSSddSSNnnnnnnnnnnnnnnnnnnnssssssssssssssssssssaaasss",
                "asssssssssssssssssssssssssddddddddddddddddddddddddddddd ddaffffffd",
                "sAsdqnwnennnnnnnnnnnnjsksassdkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk",
                "sass"
        );
    }
}