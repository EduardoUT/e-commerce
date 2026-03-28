package io.github.eduardout.e_commerce.entity;

import io.github.eduardout.e_commerce.entity.data.*;
import io.github.eduardout.e_commerce.entity.data.builder.*;
import io.github.eduardout.e_commerce.repository.*;
import org.hibernate.Session;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.*;

import static io.github.eduardout.e_commerce.entity.data.builder.Customers.aCustomer;
import static io.github.eduardout.e_commerce.entity.data.builder.PersonalDatas.aPersonalData;
import static io.github.eduardout.e_commerce.entity.data.builder.Users.anUser;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerTest {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private PurchaseItemRepository purchaseItemRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private Session session;
    private Set<Orders> orders;
    private Set<Product> products;
    private Set<Payment> payments;
    private Set<Purchase> purchases;
    private Customer customer;
    private Seller seller;
    private ShoppingCart shoppingCart;
    private final Class<DataIntegrityViolationException> dataIntegrityViolationExceptionClass =
            DataIntegrityViolationException.class;

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

    private void setUpProducts() {
        ProductCategoryTestDataLoader productCategoryTestDataLoader = new ProductCategoryTestDataLoader(productCategoryRepository);
        ProductTestDataLoader productTestDataLoader = new ProductTestDataLoader(
                productRepository,
                productCategoryTestDataLoader.setUp()
        );
        products = productTestDataLoader.setUp();
    }

    private void setUpShoppingCart() {
        setUpProducts();
        ShoppingCartTestDataLoader shoppingCartTestDataLoader = new ShoppingCartTestDataLoader(shoppingCartRepository, products);
        shoppingCart = shoppingCartTestDataLoader.setUp()
                .stream()
                .findFirst()
                .orElseThrow();
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

    private void setUpPayments() {
        PaymentTestDataLoader paymentTestDataLoader = new PaymentTestDataLoader(paymentRepository, customer);
        payments = paymentTestDataLoader.setUp();
    }

    private void setUpPurchases() {
        setUpProducts();
        PurchaseTestDataLoader purchaseTestDataLoader = new PurchaseTestDataLoader(purchaseRepository, products, customer);
        purchases = purchaseTestDataLoader.setUp();
    }

    @Nested
    class TestNullability {
        private Customer unexpectedCustomer;

        @Test
        void testNullUser() {
            unexpectedCustomer = Customers.aCustomer()
                    .withUser(null)
                    .build();
            assertThrows(dataIntegrityViolationExceptionClass, () -> customerRepository.save(unexpectedCustomer));
        }

        @Test
        void testNullPersonalData() {
            unexpectedCustomer = Customers.aCustomer()
                    .withPersonalData(null)
                    .build();
            assertThrows(dataIntegrityViolationExceptionClass, () -> customerRepository.save(unexpectedCustomer));
        }
    }

    @Nested
    class TestCrudOperations {
        @DisplayName("Should delete all CartItems associated to a ShoppingCart's Customer and the ShoppingCart associated" +
                "to a identifier's Customer")
        @Test
        void testDeleteCartItemsAndShoppingCart() {
            setUpCustomer();
            setUpShoppingCart();
            ShoppingCart actualShoppingCart = shoppingCart;
            customer.setShoppingCart(actualShoppingCart);

            customerRepository.save(customer);

            Long shoppingCartId = customer.getShoppingCart().getId();

            cartItemRepository.deleteByShoppingCartId(shoppingCartId);

            List<CartItem> unexpectedCartItems = cartItemRepository.findAllByShoppingCartId(shoppingCartId);
            assertTrue(unexpectedCartItems.isEmpty());

            shoppingCartRepository.deleteCustomerShoppingCartAssociation(shoppingCartId);
            shoppingCartRepository.deleteShoppingCartById(shoppingCartId);

            Optional<ShoppingCart> unexpectedShoppingCart = shoppingCartRepository.findByShoppingCartId(shoppingCartId);
            assertFalse(unexpectedShoppingCart.isPresent());
        }
    }

    @Nested
    class TestUnicity {
        @BeforeEach
        void beforeEach() {
            setUpCustomer();
        }

        @DisplayName(value = "A Customer should be associated with an unique User entity")
        @Test
        void testUniqueUserAssociation() {
            User unexpectedUser = customer.getUser();

            Customer unexpectedCustomer = aCustomer()
                    .withUser(unexpectedUser)
                    .withPersonalData(aPersonalData()
                            .withFirstName("Mark")
                            .withPaternalSurname("Collings")
                            .withMaternalSurname("Jansen")
                            .withBirthDate(LocalDate.of(1989, 2, 23))
                            .withPhoneNumber("55-4564454")
                            .build())
                    .build();

            assertThrows(dataIntegrityViolationExceptionClass, () -> customerRepository.save(unexpectedCustomer));
        }

        @DisplayName(value = "A Customer should be associated with an unique personal data entity")
        @Test
        void testUniquePersonalDataAssociation() {
            PersonalData unexpectedPersonalData = customer.getPersonalData();

            Customer unexpectedCustomer = aCustomer()
                    .withPersonalData(unexpectedPersonalData)
                    .build();

            assertThrows(dataIntegrityViolationExceptionClass, () -> customerRepository.save(unexpectedCustomer));
        }

        @DisplayName(value = "A Customer should be associated with an unique Address entity")
        @Test
        void testHaveUniqueAddressAssociation() {
            Address unexpectedAddress = customer.getAddress();

            Customer unexpectedCustomer = aCustomer()
                    .withAddress(unexpectedAddress)
                    .build();

            assertThrows(dataIntegrityViolationExceptionClass, () -> customerRepository.save(unexpectedCustomer));
        }

        @DisplayName(value = "A Customer should be associated with an unique Fiscal Data entity")
        @Test
        void testHaveUniqueFiscalDataAssociation() {
            FiscalData unexpectedFiscalData = customer.getFiscalData();

            Customer unexpectedCustomer = aCustomer()
                    .withFiscalData(unexpectedFiscalData)
                    .build();

            assertThrows(dataIntegrityViolationExceptionClass, () -> customerRepository.save(unexpectedCustomer));
        }

        @DisplayName("An Customer's Payment should not be associated with other Customer")
        @Test
        void testUniquePaymentAssociation() {
            setUpPayments();

            Set<Payment> actualPayments = payments;
            Payment actualPayment = actualPayments.stream().findFirst().orElseThrow();

            Customer unexpectedCustomer = Customers.aCustomer()
                    .withPaymentElement(actualPayment)
                    .build();

            customerRepository.saveAndFlush(unexpectedCustomer);
            String username = unexpectedCustomer.getUser().getUsername();
            session.clear();

            unexpectedCustomer = customerRepository.findByUserUsername(username).orElseThrow();
            Optional<Payment> unexpectedPayment = unexpectedCustomer.getPayments().stream().findFirst();
            assertFalse(unexpectedPayment.isPresent());
        }

        @Test
        void testUniquePurchaseAssociation() {
            setUpPurchases();
            Set<Purchase> actualPurchases = purchases;
            Purchase actualPurchase = actualPurchases.stream().findFirst().orElseThrow();

            Customer unexpectedCustomer = Customers.aCustomer()
                    .withPurchaseElement(actualPurchase)
                    .build();

            String username = unexpectedCustomer.getUser().getUsername();

            customerRepository.saveAndFlush(unexpectedCustomer);
            session.clear();

            unexpectedCustomer = customerRepository.findByUserUsername(username).orElseThrow();
            Optional<Purchase> unexpectedPurchase = unexpectedCustomer.getPurchases().stream().findFirst();
            assertFalse(unexpectedPurchase.isPresent());
        }

        @Test
        void testUniqueOrdersAssociation() {
            setUpOrders();
            Orders actualOrder = orders.stream().findFirst().orElseThrow();

            Customer unexpectedCustomer = Customers.aCustomer()
                    .withOrderElement(actualOrder)
                    .build();

            String username = unexpectedCustomer.getUser().getUsername();

            customerRepository.saveAndFlush(unexpectedCustomer);
            session.clear();

            unexpectedCustomer = customerRepository.findByUserUsername(username).orElseThrow();
            Optional<Orders> unexpectedOrder = unexpectedCustomer.getOrders().stream().findFirst();
            assertFalse(unexpectedOrder.isPresent());
        }

        @DisplayName("A Customer should be associated with an unique ShoppingCart entity")
        @Test
        void testUniqueShoppingCart() {
            setUpShoppingCart();
            ShoppingCart actualShoppingCart = shoppingCart;
            customer.setShoppingCart(actualShoppingCart);
            Customer expectedCustomerOne = customerRepository.saveAndFlush(customer);

            ShoppingCart unexpectedShoppingCart = expectedCustomerOne.getShoppingCart();

            Customer unexpectedCustomer = aCustomer()
                    .withUser(anUser()
                            .withUsername("janedoe")
                            .withPassword("UJJFhx6r*6i8R*m")
                            .build())
                    .withPersonalData(aPersonalData()
                            .withFirstName("Jane")
                            .withPaternalSurname("Dow")
                            .withMaternalSurname("Doe")
                            .withBirthDate(LocalDate.of(1956, 11, 22))
                            .withPhoneNumber("55-564645646")
                            .build())
                    .withShoppingCart(unexpectedShoppingCart)
                    .build();

            assertThrows(dataIntegrityViolationExceptionClass, () -> customerRepository.save(unexpectedCustomer));
        }
    }
}
