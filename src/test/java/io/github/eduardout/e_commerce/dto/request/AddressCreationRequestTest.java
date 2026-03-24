package io.github.eduardout.e_commerce.dto.request;

import io.github.eduardout.e_commerce.entity.Address;
import io.github.eduardout.e_commerce.entity.data.builder.Addresses;
import io.github.eduardout.e_commerce.dto.ConstraintViolationAssertion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.stream.Stream;

class AddressCreationRequestTest {
    private Address testAddress;
    private final ConstraintViolationAssertion constraintViolationAssertion = new ConstraintViolationAssertion();
    private static final String LARGE_TEXT_ONE = "Lorem Ipsum is simply dummy text of the printing and";
    private static final String LARGE_TEXT_TWO = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.";

    @BeforeEach
    void setUp() {
        testAddress = Addresses.anAddress().build();
    }

    @ParameterizedTest
    @MethodSource("streamOfValidAddressRecords")
    void testValidAddress(AddressCreationRequest addressCreationRequest) {
        constraintViolationAssertion.assertEmptyConstraintViolations(
                addressCreationRequest
        );
    }

    static Stream<AddressCreationRequest> streamOfValidAddressRecords() {
        return Stream.of(
                new AddressCreationRequest(
                        "Av. Ejército Nacional Mexicano",
                        "Anáhuac I Secc",
                        "Alcaldía Miguel Hidalgo",
                        "CDMX",
                        "11320",
                        51,
                        2
                ),

                new AddressCreationRequest(
                        "Av. Paseo de la Reforma",
                        "Juárez",
                        "Alcaldía Cuauhtémoc",
                        "CDMX",
                        "06600",
                        10,
                        489
                ),

                new AddressCreationRequest(
                        "Insurgentes Sur",
                        "Del Valle Centro",
                        "Alcaldía Benito Juárez",
                        "CDMX",
                        "03100",
                        5,
                        1602
                ),

                new AddressCreationRequest(
                        "Av. Álvaro Obregón",
                        "Roma Norte",
                        "Alcaldía Cuauhtémoc",
                        "CDMX",
                        "06700",
                        3,
                        151
                ),

                new AddressCreationRequest(
                        "Av. Universidad",
                        "Santa Cruz Atoyac",
                        "Alcaldía Benito Juárez",
                        "CDMX",
                        "03310",
                        12,
                        3000
                ),

                new AddressCreationRequest(
                        "Av. Río San Joaquín",
                        "Granada",
                        "Alcaldía Miguel Hidalgo",
                        "CDMX",
                        "11520",
                        8,
                        430
                ),

                new AddressCreationRequest(
                        "Av. División del Norte",
                        "Portales Norte",
                        "Alcaldía Benito Juárez",
                        "CDMX",
                        "03300",
                        4,
                        999
                ),

                new AddressCreationRequest(
                        "Calzada de Tlalpan",
                        "Churubusco Country Club",
                        "Alcaldía Coyoacán",
                        "CDMX",
                        "04210",
                        7,
                        1721
                ),

                new AddressCreationRequest(
                        "Av. Patriotismo",
                        "San Pedro de los Pinos",
                        "Alcaldía Benito Juárez",
                        "CDMX",
                        "03800",
                        9,
                        201
                ),

                new AddressCreationRequest(
                        "Calle Primera Cerrada de la Segunda Cerrada de San Juan de Dios",
                        "Santa Fe",
                        "Alcaldía Cuajimalpa de Morelos",
                        "CDMX",
                        "05348",
                        15,
                        3900
                )
        );
    }


    @ParameterizedTest
    @MethodSource("streamOfInvalidValues")
    void testNotBlankAddress(String street) {
        AddressCreationRequest creationRequest = new AddressCreationRequest(
                street,
                testAddress.getNeighborhood(),
                testAddress.getMunicipality(),
                testAddress.getState(),
                testAddress.getZipCode(),
                testAddress.getExternalNumber(),
                testAddress.getInternalNumber());

        constraintViolationAssertion.assertConstraintViolation(
                "Street is mandatory",
                "street",
                creationRequest
        );
    }

    static Stream<String> streamOfInvalidValues() {
        return Stream.of("", null);
    }

    @Test
    void testStreetMaxSize() {
        constraintViolationAssertion.assertConstraintViolation(
                "Out of range, max characters for street are 70",
                "street",
                new AddressCreationRequest(
                        LARGE_TEXT_TWO,
                        testAddress.getNeighborhood(),
                        testAddress.getMunicipality(),
                        testAddress.getState(),
                        testAddress.getZipCode(),
                        testAddress.getExternalNumber(),
                        testAddress.getInternalNumber()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidValues")
    void testNotBlankNeighborhood(String neighborhood) {
        constraintViolationAssertion.assertConstraintViolation(
                "Neighborhood is mandatory",
                "neighborhood",
                new AddressCreationRequest(
                        testAddress.getStreet(),
                        neighborhood,
                        testAddress.getMunicipality(),
                        testAddress.getState(),
                        testAddress.getZipCode(),
                        testAddress.getExternalNumber(),
                        testAddress.getInternalNumber()
                )
        );
    }

    @Test
    void testMaxSizeNeighborhood() {
        constraintViolationAssertion.assertConstraintViolation(
                "Out of range, max characters for neighborhood are 70",
                "neighborhood",
                new AddressCreationRequest(
                        testAddress.getStreet(),
                        LARGE_TEXT_TWO,
                        testAddress.getMunicipality(),
                        testAddress.getState(),
                        testAddress.getZipCode(),
                        testAddress.getExternalNumber(),
                        testAddress.getInternalNumber()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidValues")
    void testNotBlankMunicipality(String municipality) {
        constraintViolationAssertion.assertConstraintViolation(
                "Municipality is mandatory",
                "municipality",
                new AddressCreationRequest(
                        testAddress.getStreet(),
                        testAddress.getNeighborhood(),
                        municipality,
                        testAddress.getState(),
                        testAddress.getZipCode(),
                        testAddress.getExternalNumber(),
                        testAddress.getInternalNumber()
                )
        );
    }

    @Test
    void testMaxSizeMunicipality() {
        constraintViolationAssertion.assertConstraintViolation(
                "Out of range, max characters for municipality are 50",
                "municipality",
                new AddressCreationRequest(
                        testAddress.getStreet(),
                        testAddress.getNeighborhood(),
                        LARGE_TEXT_ONE,
                        testAddress.getState(),
                        testAddress.getZipCode(),
                        testAddress.getExternalNumber(),
                        testAddress.getInternalNumber()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidValues")
    void testNotBlankState(String state) {
        constraintViolationAssertion.assertConstraintViolation(
                "State is mandatory",
                "state",
                new AddressCreationRequest(
                        testAddress.getStreet(),
                        testAddress.getNeighborhood(),
                        testAddress.getMunicipality(),
                        state,
                        testAddress.getZipCode(),
                        testAddress.getExternalNumber(),
                        testAddress.getInternalNumber()
                )
        );
    }

    @Test
    void testMaxSizeState() {
        constraintViolationAssertion.assertConstraintViolation(
                "Out of range, max characters for state are 50",
                "state",
                new AddressCreationRequest(
                        testAddress.getStreet(),
                        testAddress.getNeighborhood(),
                        testAddress.getMunicipality(),
                        LARGE_TEXT_ONE,
                        testAddress.getZipCode(),
                        testAddress.getExternalNumber(),
                        testAddress.getInternalNumber()
                )
        );
    }

    @Test
    void testNotNullZipCode() {
        constraintViolationAssertion.assertConstraintViolation(
                "Zip code is mandatory",
                "zipCode",
                new AddressCreationRequest(
                        testAddress.getStreet(),
                        testAddress.getNeighborhood(),
                        testAddress.getMunicipality(),
                        testAddress.getState(),
                        null,
                        testAddress.getExternalNumber(),
                        testAddress.getInternalNumber()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidZipCodes")
    void testPatternZipCode(String zipCode) {
        String expectedMessage = "The zip code must be of 5 numeric digits, and must not contain spaces";
        String expectedPropertyPath = "zipCode";

        constraintViolationAssertion.assertConstraintViolation(
                expectedMessage,
                expectedPropertyPath,
                new AddressCreationRequest(
                        testAddress.getStreet(),
                        testAddress.getNeighborhood(),
                        testAddress.getMunicipality(),
                        testAddress.getState(),
                        zipCode,
                        testAddress.getExternalNumber(),
                        testAddress.getInternalNumber()
                )
        );
    }

    static Stream<String> streamOfInvalidZipCodes() {
        return Stream.of("512", "51284 ", " 51254", "51 254", "D5548");
    }

    @Test
    void testNotNullExternalNumber() {
        constraintViolationAssertion.assertConstraintViolation(
                "External number should not be null",
                "externalNumber",
                new AddressCreationRequest(
                        testAddress.getStreet(),
                        testAddress.getNeighborhood(),
                        testAddress.getMunicipality(),
                        testAddress.getState(),
                        testAddress.getZipCode(),
                        null,
                        testAddress.getInternalNumber()
                )
        );
    }

    @Test
    void testPositiveExternalNumber() {
        constraintViolationAssertion.assertConstraintViolation(
                "External number must not be negative",
                "externalNumber",
                new AddressCreationRequest(
                        testAddress.getStreet(),
                        testAddress.getNeighborhood(),
                        testAddress.getMunicipality(),
                        testAddress.getState(),
                        testAddress.getZipCode(),
                        -1,
                        testAddress.getInternalNumber()
                )
        );
    }

    @Test
    void testNotNullInternalNumber() {
        constraintViolationAssertion.assertConstraintViolation(
                "Internal number should not be null, if omitted write 0",
                "internalNumber",
                new AddressCreationRequest(
                        testAddress.getStreet(),
                        testAddress.getNeighborhood(),
                        testAddress.getMunicipality(),
                        testAddress.getState(),
                        testAddress.getZipCode(),
                        testAddress.getExternalNumber(),
                        null
                )
        );
    }

    @Test
    void testPositiveOrZeroInternalNumber() {
        constraintViolationAssertion.assertConstraintViolation(
                "Internal number should not be negative, if omitted write 0",
                "internalNumber",
                new AddressCreationRequest(
                        testAddress.getStreet(),
                        testAddress.getNeighborhood(),
                        testAddress.getMunicipality(),
                        testAddress.getState(),
                        testAddress.getZipCode(),
                        testAddress.getExternalNumber(),
                        -1
                )
        );
    }
}