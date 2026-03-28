package io.github.eduardout.e_commerce.entity;

import io.github.eduardout.e_commerce.entity.data.CustomerTestDataLoader;
import io.github.eduardout.e_commerce.entity.data.PaymentTestDataLoader;
import io.github.eduardout.e_commerce.entity.data.builder.Payments;
import io.github.eduardout.e_commerce.repository.CustomerRepository;
import io.github.eduardout.e_commerce.repository.PaymentRepository;
import io.github.eduardout.e_commerce.util.PaymentStatus;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PaymentTest {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CustomerRepository customerRepository;
    private Customer customer;
    private Set<Payment> payments;

    private void setUpCustomer() {
        CustomerTestDataLoader customerTestDataLoader = new CustomerTestDataLoader(customerRepository);
        customer = customerTestDataLoader.setUp()
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private void setUpPayments() {
        setUpCustomer();
        PaymentTestDataLoader paymentTestDataLoader = new PaymentTestDataLoader(paymentRepository, customer);
        payments = paymentTestDataLoader.setUp();
    }

    @Nested
    class TestNullability {
        @Test
        void testNullCustomer() {
            Payment unexpectedPayment = Payments.aPayment()
                    .withCustomer(null)
                    .build();
            assertThrows(DataIntegrityViolationException.class, () -> paymentRepository.save(unexpectedPayment));
        }

        @Test
        void testNullPaymentStatus() {
            setUpCustomer();
            Customer actualCustomer = customer;

            Payment unexpectedPayment = Payments.aPayment()
                    .withCustomer(actualCustomer)
                    .withPaymentStatus(null)
                    .build();

            assertThrows(DataIntegrityViolationException.class, () -> paymentRepository.save(unexpectedPayment));
        }

        @Test
        void testNullExternalId() {
            setUpCustomer();
            Customer actualCustomer = customer;

            Payment unexpectedPayment = Payments.aPayment()
                    .withCustomer(actualCustomer)
                    .withExternalId(null)
                    .build();

            unexpectedPayment.setCustomer(actualCustomer);

            assertThrows(DataIntegrityViolationException.class, () -> paymentRepository.save(unexpectedPayment));
        }

        @Test
        void testNullAmount() {
            setUpCustomer();
            Customer actualCustomer = customer;
            Payment unexpectedPayment = Payments.aPayment()
                    .withCustomer(actualCustomer)
                    .withAmount(null)
                    .build();
            assertThrows(DataIntegrityViolationException.class, () -> paymentRepository.save(unexpectedPayment));
        }
    }

    @Nested
    class TestCrudOperations {
        @Test
        void testFindAllByCustomerId() {
            setUpPayments();
            Long customerId = customer.getId();

            List<Payment> expectedPayments = paymentRepository.findAllByCustomerId(customerId);
            assertEquals(2, expectedPayments.size());
        }

        @Test
        void testDeleteByCustomerId() {
            setUpPayments();
            Long customerId = customer.getId();

            paymentRepository.deleteByCustomerId(customerId);

            List<Payment> expectedPayments = paymentRepository.findAllByCustomerId(customerId);
            assertTrue(expectedPayments.isEmpty());
        }
    }
}