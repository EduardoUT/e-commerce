package io.github.eduardout.e_commerce.entity;

import io.github.eduardout.e_commerce.entity.data.*;
import io.github.eduardout.e_commerce.entity.data.builder.OrdersTestBuilder;
import io.github.eduardout.e_commerce.repository.*;
import io.github.eduardout.e_commerce.util.OrderStatus;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrdersTest {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private Session session;
    private Customer customer;
    private Seller seller;
    private Set<Orders> orders;
    private Set<Payment> payments;
    private Set<Product> products;

    @BeforeEach
    void beforeEach() {
        setUpCustomer();
    }

    private void setUpCustomer() {
        CustomerTestDataLoader customerTestDataLoader = new CustomerTestDataLoader(customerRepository);
        customer = customerTestDataLoader.setUp()
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private void setUpSeller() {
        SellerTestDataLoader sellerTestDataLoader = new SellerTestDataLoader(sellerRepository);
        seller = sellerTestDataLoader.setUp()
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private void setUpPayments() {
        PaymentTestDataLoader paymentTestDataLoader = new PaymentTestDataLoader(paymentRepository, customer);
        payments = paymentTestDataLoader.setUp();
    }

    private void setUpProducts() {
        ProductCategoryTestDataLoader productCategoryTestDataLoader = new ProductCategoryTestDataLoader(productCategoryRepository);
        ProductTestDataLoader productTestDataLoader = new ProductTestDataLoader(
                productRepository, productCategoryTestDataLoader.setUp()
        );
        products = productTestDataLoader.setUp();
    }

    private void setUpOrders() {
        setUpProducts();
        setUpPayments();
        setUpSeller();
        OrdersTestDataLoader ordersTestDataLoader = new OrdersTestDataLoader(
                ordersRepository, customer, seller, payments, products
        );
        orders = ordersTestDataLoader.setUp();
    }

    @Nested
    class TestNullability {
        private final Class<DataIntegrityViolationException> dataIntegrityViolationExceptionClass =
                DataIntegrityViolationException.class;

        @Test
        void testNullSeller() {
            setUpPayments();
            Payment actualPayment = payments.stream().findFirst().orElseThrow();

            Orders unexpectedOrder = OrdersTestBuilder.anOrder()
                    .withSeller(null)
                    .withCustomer(customer)
                    .withPayment(actualPayment)
                    .build();

            assertThrows(dataIntegrityViolationExceptionClass, () -> ordersRepository.save(unexpectedOrder));
        }

        @Test
        void testNullCustomer() {
            setUpPayments();
            Payment actualPayment = payments.stream().findFirst().orElseThrow();

            Orders unexpectedOrder = OrdersTestBuilder.anOrder()
                    .withPayment(actualPayment)
                    .withCustomer(null)
                    .build();

            assertThrows(dataIntegrityViolationExceptionClass, () -> ordersRepository.save(unexpectedOrder));
        }

        @Test
        void testNullPayment() {
            Orders unexpectedOrder = OrdersTestBuilder.anOrder()
                    .withPayment(null)
                    .build();
            assertThrows(dataIntegrityViolationExceptionClass, () -> ordersRepository.save(unexpectedOrder));
        }

        @Test
        void testNullOrderStatus() {
            setUpPayments();
            Payment actualPayment = payments.stream().findFirst().orElseThrow();

            Orders unexpectedOrder = OrdersTestBuilder.anOrder()
                    .withCustomer(customer)
                    .withPayment(actualPayment)
                    .withOrderStatus(null)
                    .build();

            assertThrows(dataIntegrityViolationExceptionClass, () -> ordersRepository.save(unexpectedOrder));
        }

        @Test
        void testNullTotal() {
            setUpPayments();
            Payment actualPayment = payments.stream().findFirst().orElseThrow();

            Orders unexpectedOrder = OrdersTestBuilder.anOrder()
                    .withCustomer(customer)
                    .withPayment(actualPayment)
                    .withTotal(null)
                    .build();

            assertThrows(dataIntegrityViolationExceptionClass, () -> ordersRepository.save(unexpectedOrder));
        }

        @Test
        void testNullSubTotal() {
            setUpPayments();
            Payment actualPayment = payments.stream().findFirst().orElseThrow();

            Orders unexpectedOrder = OrdersTestBuilder.anOrder()
                    .withCustomer(customer)
                    .withPayment(actualPayment)
                    .withSubTotal(null)
                    .build();

            assertThrows(dataIntegrityViolationExceptionClass, () -> ordersRepository.save(unexpectedOrder));
        }
    }

    @Nested
    class TestCrudOperations {
        @Test
        void testDeleteOrdersByCustomerId() {
            setUpOrders();
            Long customerId = customer.getId();
            Set<Orders> actualOrders = orders;
            actualOrders.forEach(order -> orderItemRepository.deleteByOrderId(order.getId()));
            ordersRepository.deleteByCustomerId(customerId);
            List<Orders> unexpectedOrders = ordersRepository.findAllByCustomerId(customerId);
            assertTrue(unexpectedOrders.isEmpty());
        }

        @Test
        void findAllByOrderId() {
            setUpOrders();
            session.clear();
            List<Orders> expectedOrders = ordersRepository.findAllByCustomerId(customer.getId());
            assertFalse(expectedOrders.isEmpty());
        }
    }
}