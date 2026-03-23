package io.github.eduardout.e_commerce.dto.request;

import io.github.eduardout.e_commerce.dto.ConstraintViolationAssertion;
import io.github.eduardout.e_commerce.entity.Customer;
import io.github.eduardout.e_commerce.entity.PersonalData;
import io.github.eduardout.e_commerce.entity.User;
import io.github.eduardout.e_commerce.entity.data.builder.Customers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerCreationRequestTest {
    private final ConstraintViolationAssertion constraintViolationAssertion = new ConstraintViolationAssertion();
    private Customer customerTest;
    private UserCreationRequest userCreationRequest;
    private PersonalDataCreationRequest personalDataCreationRequest;

    @BeforeEach
    void setUp() {
        customerTest = Customers.aCustomer().build();
        setUpUserCreationRequest();
        setUpPersonalDataCreationRequest();
    }

    private void setUpUserCreationRequest() {
        User user = customerTest.getUser();
        userCreationRequest = new UserCreationRequest(
                user.getUsername(),
                user.getRole(),
                user.getPassword(),
                user.getPassword(),
                user.getEmail()
        );
    }

    private void setUpPersonalDataCreationRequest() {
        PersonalData personalData = customerTest.getPersonalData();
        personalDataCreationRequest = new PersonalDataCreationRequest(
                personalData.getFirstName(),
                personalData.getMaternalSurname(),
                personalData.getPaternalSurname(),
                personalData.getBirthDate(),
                personalData.getPhoneNumber()
        );
    }

    @Test
    void testValidCustomerCreationRequest() {
        constraintViolationAssertion.assertEmptyConstraintViolations(
                new CustomerCreationRequest(userCreationRequest, personalDataCreationRequest)
        );
    }

    @Test
    void testNotNullUserCreationRequest() {
        constraintViolationAssertion.assertConstraintViolation(
                "User is mandatory",
                "userCreationRequest",
                new CustomerCreationRequest(null, personalDataCreationRequest)
        );
    }

    @Test
    void testNotNullPersonalDataCreationRequest() {
        constraintViolationAssertion.assertConstraintViolation(
                "Personal data is mandatory",
                "personalDataCreationRequest",
                new CustomerCreationRequest(userCreationRequest, null)
        );
    }
}