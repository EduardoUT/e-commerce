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
        CategoryTestDataLoader categoryTestDataLoader = new CategoryTestDataLoader(productCategoryRepository);
        ProductTestDataLoader productTestDataLoader = new ProductTestDataLoader(
                productRepository,
                categoryTestDataLoader.setUp()
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
        @DisplayName(value = "Should persist customer information")
        @Test
        void testSaveCustomer() {
            Customer expectedCustomer = aCustomer()
                    .withUser(anUser()
                            .withUsername("TestUserSave")
                            .withPassword("$ECpx9MKTz8FzZt")
                            .withEmail("rolling_people@gmail.com")
                            .build())
                    .withPersonalData(aPersonalData()
                            .withFirstName("Paolo")
                            .withPaternalSurname("Gómez")
                            .withMaternalSurname("Comodoro")
                            .withBirthDate(LocalDate.of(1989, 5, 12))
                            .withPhoneNumber("55-44489485")
                            .build())
                    .withAddress(Addresses.anAddress()
                            .withStreet("St Ethiene Street")
                            .withNeighborhood("St Ethiene")
                            .withMunicipality("Polca")
                            .withState("France")
                            .withZipCode("D-4564")
                            .withExternalNumber(45)
                            .withInternalNumber(null)
                            .build())
                    .withFiscalData(FiscalDatas.aFiscalData()
                            .withRfc("RGFG45646SAD")
                            .withRegimeTax("45")
                            .withFiscalAddress("Wisconsin Street Ave Saint Louis")
                            .build())
                    .build();

            customerRepository.save(expectedCustomer);

            assertAll(
                    () -> assertNotNull(expectedCustomer),
                    () -> assertNotNull(expectedCustomer.getId())
            );
        }

        @DisplayName("Should save a valid ShoppingCart on an existing Customer")
        @Test
        void testSaveShoppingCart() {
            setUpCustomer();
            setUpShoppingCart();
            ShoppingCart actualShoppingCart = shoppingCart;
            customer.setShoppingCart(actualShoppingCart);

            customerRepository.save(customer);

            ShoppingCart expectedShoppingCart = customer.getShoppingCart();
            assertAll(
                    () -> assertNotNull(expectedShoppingCart),
                    () -> assertNotNull(expectedShoppingCart.getId())
            );
        }

        @DisplayName("Should save a Purchase on an existing Customer ShoppingCart entity")
        @Test
        void testSavePurchases() {
            setUpCustomer();
            setUpShoppingCart();
            ShoppingCart actualShoppingCart = shoppingCart;
            CartItem actualCartItem = actualShoppingCart.getCartItems()
                    .stream()
                    .findFirst()
                    .orElseThrow();

            Purchase expectedPurchase = Purchase.aPurchase()
                    .withPurchaseItemElement(PurchaseItem.aPurchaseItem()
                            .withQuantity(actualCartItem.getQuantity())
                            .withProduct(actualCartItem.getProduct())
                            .withLineAmount(actualCartItem.getLineAmount())
                            .build())
                    .withSubTotal(actualShoppingCart.getSubTotal())
                    .withTotal(actualShoppingCart.getSubTotal())
                    .build();

            customer.addPurchase(expectedPurchase);
            customerRepository.save(customer);

            Set<Purchase> expectedPurchases = customer.getPurchases();
            assertEquals(1, expectedPurchases.size());
        }

        @DisplayName("Should save Payments on an existing Customer")
        @Test
        void testSavePayments() {
            setUpCustomer();
            setUpPayments();

            Set<Payment> expectedPayments = payments;
            assertEquals(2, expectedPayments.size());
        }

        @DisplayName("Should save Orders on an existing Customer based on a Purchase")
        @Test
        void testSaveOrders() {
            setUpCustomer();
            setUpOrders();
            String expectedUsername = customer.getUser().getUsername();
            session.clear();

            Customer expectedCustomer = customerRepository.findByUserUsername(expectedUsername).orElseThrow();

            Set<Orders> expectedOrders = expectedCustomer.getOrders();
            assertEquals(3, expectedOrders.size());
        }

        @DisplayName("Should delete all OrderItem associated to an identifier's Order and all the Orders " +
                "associated to a identifier's Customer")
        @Test
        void testDeleteOrderItemsAndOrders() {
            setUpCustomer();
            setUpOrders();
            Long customerId = customer.getId();
            Set<Orders> actualOrders = orders;

            actualOrders.forEach(order -> orderItemRepository.deleteByOrderId(order.getId()));

            ordersRepository.deleteByCustomerId(customerId);

            List<Orders> unexpectedOrders = ordersRepository.findAllByCustomerId(customerId);
            assertTrue(unexpectedOrders.isEmpty());
        }

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

        @DisplayName("Should delete all PurchaseItems associated to a identifier's Purchase and all the Purchases " +
                "associated to a identifier's Customer")
        @Test
        void testDeletePurchaseItemsAndPurchases() {
            setUpCustomer();
            setUpPurchases();
            Long customerId = customer.getId();
            Set<Purchase> actualPurchases = purchases;

            actualPurchases.forEach(purchase -> {
                Long purchaseId = purchase.getId();
                purchaseItemRepository.deleteByPurchaseId(purchaseId);
                List<PurchaseItem> unexpectedPurchaseItems = purchaseItemRepository.findAllByPurchaseId(purchaseId);
                assertTrue(unexpectedPurchaseItems.isEmpty());
            });

            purchaseRepository.deleteByCustomerId(customerId);

            List<Purchase> unexpectedPurchases = purchaseRepository.findAllByCustomerId(customerId);
            assertTrue(unexpectedPurchases.isEmpty());
        }

        @DisplayName("Should delete all Payments associated to a identifier's Customer")
        @Test
        void testDeletePayments() {
            setUpCustomer();
            setUpPayments();
            Long customerId = customer.getId();

            paymentRepository.deleteByCustomerId(customerId);

            List<Payment> unexpectedPayments = paymentRepository.findAllByCustomerId(customerId);
            assertTrue(unexpectedPayments.isEmpty());
        }

        @Test
        void testDeleteCustomer() {
            setUpCustomer();
            String username = customer.getUser().getUsername();

            customerRepository.delete(customer);

            Optional<Customer> unexpectedCustomer = customerRepository.findByUserUsername(username);
            assertFalse(unexpectedCustomer.isPresent());
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
