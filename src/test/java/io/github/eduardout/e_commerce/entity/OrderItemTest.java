package io.github.eduardout.e_commerce.entity;

import io.github.eduardout.e_commerce.entity.data.*;
import io.github.eduardout.e_commerce.entity.data.builder.OrderItems;
import io.github.eduardout.e_commerce.repository.*;
import org.hibernate.AssertionFailure;
import org.junit.jupiter.api.DisplayName;
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
class OrderItemTest {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    private Seller seller;
    private Customer customer;
    private Set<Orders> orders;
    private Set<Payment> payments;
    private Set<Product> products;

    private void setUpProducts() {
        CategoryTestDataLoader categoryTestDataLoader = new CategoryTestDataLoader(productCategoryRepository);
        ProductTestDataLoader productTestDataLoader = new ProductTestDataLoader(
                productRepository, categoryTestDataLoader.setUp()
        );
        products = productTestDataLoader.setUp();
    }

    private void setUpSeller() {
        SellerTestDataLoader sellerTestDataLoader = new SellerTestDataLoader(sellerRepository);
        seller = sellerTestDataLoader.setUp()
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private void setUpCustomer() {
        CustomerTestDataLoader customerTestDataLoader = new CustomerTestDataLoader(customerRepository);
        customer = customerTestDataLoader.setUp()
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private void setUpPayments() {
        PaymentTestDataLoader paymentTestDataLoader = new PaymentTestDataLoader(paymentRepository, customer);
        payments = paymentTestDataLoader.setUp();
    }

    private void setUpOrders() {
        setUpProducts();
        setUpSeller();
        setUpPayments();
        OrdersTestDataLoader ordersTestDataLoader = new OrdersTestDataLoader(
                ordersRepository, customer, seller, payments, products
        );
        orders = ordersTestDataLoader.setUp();
    }

    private Orders findFirstOrderTest(Set<Orders> orders) {
        return orders
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private OrderItem findFirstOrderItemTest(Orders orders) {
        return orders.getOrderItems()
                .stream()
                .findFirst()
                .orElseThrow();
    }

    @Nested
    class TestNullability {
        @DisplayName("Should throw an AssertionFailure when Orders identifier of the composite key OrderItemId is null.")
        @Test
        void testNullOrderIdentifierOnCompositeKey() {
            OrderItem orderItem = OrderItems.anOrderItem()
                    .withOrders(null)
                    .build();
            assertThrows(AssertionFailure.class, () -> orderItemRepository.save(orderItem));
        }

        @DisplayName("Should throw an AssertionFailure when Product identifier of the composite key OrderItemId is null.")
        @Test
        void testNullProductIdentifierOnCompositeKey() {
            setUpCustomer();
            setUpOrders();
            Orders actualOrder = findFirstOrderTest(orders);
            OrderItem orderItem = OrderItems.anOrderItem()
                    .withOrders(actualOrder)
                    .withProduct(null)
                    .build();
            assertThrows(AssertionFailure.class, () -> orderItemRepository.save(orderItem));
        }

        @Test
        void testNullQuantity() {
            setUpCustomer();
            setUpOrders();
            Orders actualOrder = findFirstOrderTest(orders);
            OrderItem actualOrderItem = findFirstOrderItemTest(actualOrder);
            Product product = productRepository.findByName("Pringles").orElseThrow();
            OrderItem orderItem = OrderItems.anOrderItem()
                    .withOrders(actualOrderItem.getOrders())
                    .withProduct(product)
                    .withQuantity(null)
                    .build();
            assertThrows(DataIntegrityViolationException.class, () -> orderItemRepository.saveAndFlush(orderItem));
        }

        @Test
        void testNullLineAmount() {
            setUpCustomer();
            setUpOrders();
            Orders actualOrder = findFirstOrderTest(orders);
            OrderItem actualOrderItem = findFirstOrderItemTest(actualOrder);
            Product product = productRepository.findByName("Pringles").orElseThrow();
            OrderItem orderItem = OrderItems.anOrderItem()
                    .withOrders(actualOrderItem.getOrders())
                    .withProduct(product)
                    .withLineAmount(null)
                    .build();
            assertThrows(DataIntegrityViolationException.class, () -> orderItemRepository.saveAndFlush(orderItem));
        }
    }

    @Nested
    class TestCrudOperations {
        @Test
        void testSaveOrderItem() {
            setUpCustomer();
            setUpOrders();
            Orders actualOrder = findFirstOrderTest(orders);
            Product product = productRepository.findByName("Pringles").orElseThrow();
            OrderItem orderItem = OrderItems.anOrderItem()
                    .withOrders(actualOrder)
                    .withProduct(product)
                    .withLineAmount(product.getSellPrice())
                    .build();
            OrderItem expectedOrderItem = orderItemRepository.save(orderItem);
            assertAll(
                    () -> assertNotNull(expectedOrderItem),
                    () -> assertNotNull(expectedOrderItem.getOrders().getId()),
                    () -> assertNotNull(expectedOrderItem.getProduct().getId())
            );
        }

        @Test
        void testUpdateOrderItem() {
            setUpCustomer();
            setUpOrders();
            Orders actualOrder = findFirstOrderTest(orders);
            OrderItem actualOrderItem = findFirstOrderItemTest(actualOrder);
            Integer unexpectedQuantity = actualOrderItem.getQuantity();
            BigDecimal unexpectedLineAmount = actualOrderItem.getLineAmount();
            OrderItem updatedOrderItem = OrderItems.anOrderItem()
                    .withOrders(actualOrderItem.getOrders())
                    .withProduct(actualOrderItem.getProduct())
                    .withQuantity(6)
                    .withLineAmount(actualOrderItem.getProduct().getSellPrice().multiply(new BigDecimal("6")))
                    .build();
            OrderItem expectedOrderItem = orderItemRepository.saveAndFlush(updatedOrderItem);
            assertAll(
                    () -> assertNotNull(expectedOrderItem),
                    () -> assertNotEquals(unexpectedQuantity, expectedOrderItem.getQuantity()),
                    () -> assertNotEquals(unexpectedLineAmount, expectedOrderItem.getLineAmount())
            );
        }

        @Test
        void testFindByOrderId() {
            setUpCustomer();
            setUpOrders();
            Orders actualOrder = findFirstOrderTest(orders);
            List<OrderItem> actualOrderItem = orderItemRepository.findAllByOrderId(actualOrder.getId());
            assertFalse(actualOrderItem.isEmpty());
        }

        @Test
        void testFindByOrderItemId() {
            setUpCustomer();
            setUpOrders();
            Orders actualOrder = findFirstOrderTest(orders);
            OrderItem actualOrderItem = findFirstOrderItemTest(actualOrder);
            Long orderId = actualOrderItem.getOrders().getId();
            Long productId = actualOrderItem.getProduct().getId();
            OrderItemId orderItemId = new OrderItemId(orderId, productId);
            Optional<OrderItem> expectedOrderItem = orderItemRepository.findById(orderItemId);
            assertTrue(expectedOrderItem.isPresent());
        }

        @DisplayName("Should delete all the OrderItems associated to an Orders entity")
        @Test
        void deleteOrderItemsByOrderId() {
            setUpCustomer();
            setUpOrders();
            Orders actualOrder = findFirstOrderTest(orders);
            OrderItem actualOrderItem = findFirstOrderItemTest(actualOrder);
            Long orderId = actualOrderItem.getOrders().getId();
            orderItemRepository.deleteByOrderId(orderId);
            List<OrderItem> unexpectedOrderItems = orderItemRepository.findAllByOrderId(orderId);
            assertTrue(unexpectedOrderItems.isEmpty());
        }
    }
}