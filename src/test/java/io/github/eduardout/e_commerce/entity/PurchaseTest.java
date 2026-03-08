package io.github.eduardout.e_commerce.entity;

import io.github.eduardout.e_commerce.entity.data.ProductCategoryTestDataLoader;
import io.github.eduardout.e_commerce.entity.data.CustomerTestDataLoader;
import io.github.eduardout.e_commerce.entity.data.ProductTestDataLoader;
import io.github.eduardout.e_commerce.entity.data.PurchaseTestDataLoader;
import io.github.eduardout.e_commerce.repository.*;
import org.hibernate.Session;
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
class PurchaseTest {
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private PurchaseItemRepository purchaseItemRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private Session session;
    private Set<Purchase> purchases;
    private Set<Product> products;
    private Customer customer;

    private void setUpProducts() {
        ProductCategoryTestDataLoader productCategoryTestDataLoader = new ProductCategoryTestDataLoader(productCategoryRepository);
        ProductTestDataLoader productTestDataLoader = new ProductTestDataLoader(
                productRepository, productCategoryTestDataLoader.setUp()
        );
        products = productTestDataLoader.setUp();
    }

    private void setUpCustomer() {
        CustomerTestDataLoader customerTestDataLoader = new CustomerTestDataLoader(customerRepository);
        customer = customerTestDataLoader.setUp()
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private void setUpPurchases() {
        setUpProducts();
        setUpCustomer();
        PurchaseTestDataLoader purchaseTestDataLoader = new PurchaseTestDataLoader(
                purchaseRepository, products, customer
        );
        purchases = purchaseTestDataLoader.setUp();
    }

    @Nested
    class TestNullability {
        @DisplayName("When a Customer's persistent Purchase its associated to other Customer or is null, " +
                "its relationship and data will not be modified")
        @Test
        void testNullCustomer() {
            setUpPurchases();
            Purchase actualPurchase = purchases.stream().findFirst().orElseThrow();
            Long actualPurchaseId = actualPurchase.getId();
            Long actualCustomerId = actualPurchase.getCustomer().getId();

            actualPurchase.setCustomer(null);

            Purchase expectedPurchase = purchaseRepository.saveAndFlush(actualPurchase);
            Long generatedId = expectedPurchase.getId();
            session.clear();

            Purchase unmodifiedPurchase = purchaseRepository.findById(generatedId).orElseThrow();
            assertAll(
                    () -> assertNotNull(unmodifiedPurchase),
                    () -> assertEquals(unmodifiedPurchase.getId(), actualPurchaseId),
                    () -> assertEquals(unmodifiedPurchase.getCustomer().getId(), actualCustomerId)
            );
        }

        @Test
        void testNullSubTotal() {
            setUpPurchases();
            Purchase actualPurchase = purchases.stream().findFirst().orElseThrow();

            actualPurchase.setSubTotal(null);

            assertThrows(DataIntegrityViolationException.class, () -> purchaseRepository.saveAndFlush(actualPurchase));
        }

        @Test
        void testNullTotal() {
            setUpPurchases();
            Purchase actualPurchase = purchases.stream().findFirst().orElseThrow();

            actualPurchase.setTotal(null);

            assertThrows(DataIntegrityViolationException.class, () -> purchaseRepository.saveAndFlush(actualPurchase));
        }
    }

    @Nested
    class TestCrudOperations {
        @Test
        void testSavePurchase() {
            setUpProducts();
            setUpCustomer();

            Product product = products.stream()
                    .filter(p -> p.getName().equals("Xiaomi Redmi 9"))
                    .findFirst()
                    .orElseThrow();

            Integer quantity = 2;
            BigDecimal calculation = product.getSellPrice().multiply(new BigDecimal(quantity.toString()));

            Purchase purchase = Purchase.aPurchase()
                    .withCustomer(customer)
                    .withPurchaseItemElement(PurchaseItem.aPurchaseItem()
                            .withProduct(product)
                            .withQuantity(quantity)
                            .withLineAmount(calculation)
                            .build())
                    .withSubTotal(calculation)
                    .withTotal(calculation)
                    .build();

            Purchase expectedPurchase = purchaseRepository.save(purchase);
            assertAll(
                    () -> assertNotNull(expectedPurchase),
                    () -> assertNotNull(expectedPurchase.getId())
            );
        }

        @Test
        void testUpdatePurchase() {
            setUpPurchases();
            Set<Purchase> actualPurchases = purchases;
            Purchase actualPurchase = actualPurchases.stream().findFirst().orElseThrow();
            BigDecimal actualSubTotal = actualPurchase.getSubTotal();
            BigDecimal actualTotal = actualPurchase.getTotal();

            Product product = products
                    .stream()
                    .filter(p -> p.getName().equals("Xiaomi Redmi 9"))
                    .findFirst()
                    .orElseThrow();

            PurchaseItem purchaseItem = PurchaseItem.aPurchaseItem()
                    .withPurchase(actualPurchase)
                    .withProduct(product)
                    .withQuantity(1)
                    .withLineAmount(product.getSellPrice())
                    .build();

            actualPurchase.addPurchaseItem(purchaseItem);

            BigDecimal grandTotal = actualPurchase.getPurchaseItems()
                    .stream()
                    .map(PurchaseItem::getLineAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            actualPurchase.setSubTotal(grandTotal);
            actualPurchase.setTotal(grandTotal);

            Purchase expectedPurchase = purchaseRepository.saveAndFlush(actualPurchase);
            assertAll(
                    () -> assertNotNull(actualPurchase),
                    () -> assertEquals(2, actualPurchase.getPurchaseItems().size()),
                    () -> assertNotEquals(actualSubTotal, expectedPurchase.getSubTotal()),
                    () -> assertNotEquals(actualTotal, expectedPurchase.getTotal())
            );
        }

        @Test
        void testDeletePurchase() {
            setUpPurchases();
            Purchase actualPurchase = purchases.stream().findFirst().orElseThrow();
            Long purchaseId = actualPurchase.getId();

            purchaseItemRepository.deleteByPurchaseId(purchaseId);

            List<PurchaseItem> unexpectedPurchaseItems = purchaseItemRepository.findAllByPurchaseId(purchaseId);
            assertTrue(unexpectedPurchaseItems.isEmpty());

            purchaseRepository.delete(actualPurchase);

            Optional<Purchase> unexpectedPurchase = purchaseRepository.findById(purchaseId);
            assertFalse(unexpectedPurchase.isPresent());
        }

        @Test
        void testDeletePurchasesByCustomerId() {
            setUpPurchases();
            Set<Purchase> actualPurchases = purchases;
            Long customerId = customer.getId();

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

        @Test
        void findAllByPurchaseId() {
            setUpPurchases();
            session.clear();

            List<Purchase> expectedPurchases = purchaseRepository.findAllByCustomerId(customer.getId());
            assertFalse(expectedPurchases.isEmpty());
        }
    }
}