package io.github.eduardout.e_commerce.dto.request;

import io.github.eduardout.e_commerce.dto.ConstraintViolationAssertion;
import io.github.eduardout.e_commerce.entity.*;
import io.github.eduardout.e_commerce.entity.data.builder.Sellers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SellerCreationRequestTest {
    private final ConstraintViolationAssertion constraintViolationAssertion = new ConstraintViolationAssertion();
    private Seller sellerTest;
    private PersonalDataCreationRequest personalDataCreationRequest;
    private UserCreationRequest userCreationRequest;
    private AddressCreationRequest addressCreationRequest;
    private FiscalDataCreationRequest fiscalDataCreationRequest;

    @BeforeEach
    void setUp() {
        sellerTest = Sellers.aSeller().build();
        setUpUserCreationRequest();
        setUpPersonalDataCreationRequest();
        setUpAddressCreationRequest();
        setUpFiscalDataCreationRequest();

    }

    private void setUpUserCreationRequest() {
        User user = sellerTest.getUser();
        userCreationRequest = new UserCreationRequest(
                user.getUsername(),
                user.getRole(),
                user.getPassword(),
                user.getPassword(),
                user.getEmail()
        );
    }

    private void setUpPersonalDataCreationRequest() {
        PersonalData personalData = sellerTest.getPersonalData();
        personalDataCreationRequest = new PersonalDataCreationRequest(
                personalData.getFirstName(),
                personalData.getMaternalSurname(),
                personalData.getPaternalSurname(),
                personalData.getBirthDate(),
                personalData.getPhoneNumber()
        );
    }

    private void setUpAddressCreationRequest() {
        Address address = sellerTest.getAddress();
        addressCreationRequest = new AddressCreationRequest(
                address.getStreet(),
                address.getNeighborhood(),
                address.getMunicipality(),
                address.getState(),
                address.getZipCode(),
                address.getExternalNumber(),
                address.getInternalNumber()
        );
    }

    private void setUpFiscalDataCreationRequest() {
        FiscalData fiscalData = sellerTest.getFiscalData();
        fiscalDataCreationRequest = new FiscalDataCreationRequest(
                fiscalData.getRfc(),
                fiscalData.getRegimeTax(),
                fiscalData.getFiscalAddress()
        );
    }

    @Test
    void testValidSellerCreationRequest() {
        constraintViolationAssertion.assertEmptyConstraintViolations(
                new SellerCreationRequest(
                        userCreationRequest,
                        personalDataCreationRequest,
                        addressCreationRequest,
                        fiscalDataCreationRequest
                )
        );
    }

    @Test
    void testNotNullUser() {
        constraintViolationAssertion.assertConstraintViolation(
                "User is mandatory",
                "userCreationRequest",
                new SellerCreationRequest(
                        null,
                        personalDataCreationRequest,
                        addressCreationRequest,
                        fiscalDataCreationRequest
                )
        );
    }

    @Test
    void testNotNullPersonalData() {
        constraintViolationAssertion.assertConstraintViolation(
                "Personal data is mandatory",
                "personalDataCreationRequest",
                new SellerCreationRequest(
                        userCreationRequest,
                        null,
                        addressCreationRequest,
                        fiscalDataCreationRequest
                )
        );
    }

    @Test
    void testNotNullAddress() {
        constraintViolationAssertion.assertConstraintViolation(
                "Address is mandatory",
                "addressCreationRequest",
                new SellerCreationRequest(
                        userCreationRequest,
                        personalDataCreationRequest,
                        null,
                        fiscalDataCreationRequest
                )
        );
    }

    @Test
    void testNotNullFiscalData() {
        constraintViolationAssertion.assertConstraintViolation(
                "Fiscal data is mandatory",
                "fiscalDataCreationRequest",
                new SellerCreationRequest(
                        userCreationRequest,
                        personalDataCreationRequest,
                        addressCreationRequest,
                        null
                )
        );
    }
}