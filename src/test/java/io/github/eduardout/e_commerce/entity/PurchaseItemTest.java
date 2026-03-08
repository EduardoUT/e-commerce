package io.github.eduardout.e_commerce.entity;

import io.github.eduardout.e_commerce.entity.data.*;
import io.github.eduardout.e_commerce.repository.*;
import org.hibernate.AssertionFailure;
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
class PurchaseItemTest {
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private PurchaseItemRepository purchaseItemRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private Session session;
    private Integer quantity;
    private Customer customer;
    private Set<Product> products;
    private Set<Purchase> purchases;

    @BeforeEach
    void setUp() {
        quantity = 3;
        setUpPurchases();
    }


    private void setUpCustomer() {
        TestDataLoader<Customer> customerTestDataLoader = new CustomerTestDataLoader(customerRepository);
        customer = customerTestDataLoader.setUp()
                .stream()
                .findFirst()
                .orElseThrow();
    }

    private void setUpProducts() {
        CategoryTestDataLoader categoryTestDataLoader = new CategoryTestDataLoader(productCategoryRepository);
        ProductTestDataLoader productTestDataLoader = new ProductTestDataLoader(productRepository, categoryTestDataLoader.setUp());
        products = productTestDataLoader.setUp();
    }


    private void setUpPurchases() {
        setUpProducts();
        setUpCustomer();
        PurchaseTestDataLoader purchaseTestDataLoader = new PurchaseTestDataLoader(purchaseRepository, products, customer);
        purchases = purchaseTestDataLoader.setUp();
    }

    private Purchase findFirstPurchase(Set<Purchase> purchases) {
        return purchases.stream().findFirst().orElseThrow();
    }

    private PurchaseItem findFirstPurchaseItem(Purchase purchase) {
        return purchase.getPurchaseItems().stream().findFirst().orElseThrow();
    }

    @Nested
    class TestNullability {
        @Test
        void testNullPurchaseIdentifierOnCompositeKey() {
            Purchase actualPurchase = findFirstPurchase(purchases);
            PurchaseItem actualPurchaseItem = findFirstPurchaseItem(actualPurchase);
            BigDecimal calculation = actualPurchaseItem.getProduct().getSellPrice().multiply(new BigDecimal(quantity.toString()));

            PurchaseItem unexpectedPurchaseItem = PurchaseItem.aPurchaseItem()
                    .withPurchase(null)
                    .withProduct(actualPurchaseItem.getProduct())
                    .withQuantity(quantity)
                    .withLineAmount(calculation)
                    .build();

            assertThrows(AssertionFailure.class, () -> purchaseItemRepository.save(unexpectedPurchaseItem));
        }

        @Test
        void testNullProductIdentifierOnCompositeKey() {
            Purchase actualPurchase = findFirstPurchase(purchases);
            PurchaseItem actualPurchaseItem = findFirstPurchaseItem(actualPurchase);
            BigDecimal calculation = actualPurchaseItem.getProduct().getSellPrice().multiply(new BigDecimal(quantity.toString()));

            PurchaseItem unexpectedPurchaseItem = PurchaseItem.aPurchaseItem()
                    .withPurchase(actualPurchase)
                    .withProduct(null)
                    .withQuantity(quantity)
                    .withLineAmount(calculation)
                    .build();

            assertThrows(AssertionFailure.class, () -> purchaseItemRepository.save(unexpectedPurchaseItem));
        }

        @Test
        void testNullQuantity() {
            Purchase actualPurchase = findFirstPurchase(purchases);
            PurchaseItem actualPurchaseItem = findFirstPurchaseItem(actualPurchase);
            BigDecimal calculation = actualPurchaseItem.getProduct().getSellPrice().multiply(new BigDecimal(Integer.toString(quantity)));

            PurchaseItem unexpectedPurchaseItem = PurchaseItem.aPurchaseItem()
                    .withPurchase(actualPurchaseItem.getPurchase())
                    .withProduct(actualPurchaseItem.getProduct())
                    .withQuantity(null)
                    .withLineAmount(calculation)
                    .build();

            assertThrows(DataIntegrityViolationException.class, () -> purchaseItemRepository.saveAndFlush(unexpectedPurchaseItem));
        }

        @Test
        void testNullLineAmount() {
            Purchase actualPurchase = findFirstPurchase(purchases);
            PurchaseItem actualPurchaseItem = findFirstPurchaseItem(actualPurchase);

            PurchaseItem unexpectedPurchaseItem = PurchaseItem.aPurchaseItem()
                    .withPurchase(actualPurchaseItem.getPurchase())
                    .withProduct(actualPurchaseItem.getProduct())
                    .withQuantity(quantity)
                    .withLineAmount(null)
                    .build();

            assertThrows(DataIntegrityViolationException.class, () -> purchaseItemRepository.saveAndFlush(unexpectedPurchaseItem));
        }
    }

    @Nested
    class TestCrudOperations {
        @Test
        void testSavePurchaseItem() {
            Purchase actualPurchase = findFirstPurchase(purchases);
            Product product = products
                    .stream()
                    .filter(p -> p.getName().equals("Pringles"))
                    .findFirst()
                    .orElseThrow();

            PurchaseItem actualPurchaseItem = PurchaseItem.aPurchaseItem()
                    .withPurchase(actualPurchase)
                    .withProduct(product)
                    .withQuantity(quantity)
                    .withLineAmount(product.getSellPrice().multiply(new BigDecimal(quantity.toString())))
                    .build();

            PurchaseItem expectedPurchaseItem = purchaseItemRepository.save(actualPurchaseItem);

            assertAll(
                    () -> assertNotNull(expectedPurchaseItem),
                    () -> assertNotNull(expectedPurchaseItem.getPurchase().getId()),
                    () -> assertNotNull(expectedPurchaseItem.getProduct().getId())
            );
        }

        @Test
        void testUpdatePurchaseItem() {
            Purchase actualPurchase = findFirstPurchase(purchases);
            PurchaseItem actualPurchaseItem = findFirstPurchaseItem(actualPurchase);

            Integer unexpectedQuantity = actualPurchaseItem.getQuantity();
            BigDecimal unexpectedLineAmount = actualPurchaseItem.getLineAmount();

            actualPurchaseItem.setQuantity(quantity);
            actualPurchaseItem.setLineAmount(actualPurchaseItem.getLineAmount().multiply(new BigDecimal(quantity.toString())));

            PurchaseItem expectedPurchaseItem = purchaseItemRepository.save(actualPurchaseItem);
            assertAll(
                    () -> assertNotNull(expectedPurchaseItem),
                    () -> assertNotEquals(unexpectedQuantity, expectedPurchaseItem.getQuantity()),
                    () -> assertNotEquals(unexpectedLineAmount, expectedPurchaseItem.getLineAmount())
            );
        }

        @Test
        void testFindAllByPurchaseId() {
            Purchase actualPurchase = findFirstPurchase(purchases);
            List<PurchaseItem> expectedPurchaseItem = purchaseItemRepository.findAllByPurchaseId(actualPurchase.getId());
            assertFalse(expectedPurchaseItem.isEmpty());
        }

        @Test
        void testDeleteByPurchaseId() {
            Purchase actualPurchase = findFirstPurchase(purchases);
            PurchaseItem actualPurchaseItem = findFirstPurchaseItem(actualPurchase);
            Long actualPurchaseId = actualPurchaseItem.getPurchase().getId();
            Long actualProductId = actualPurchaseItem.getProduct().getId();

            PurchaseItemId actualPurchaseItemId = new PurchaseItemId(actualPurchaseId, actualProductId);

            purchaseItemRepository.deleteByPurchaseId(actualPurchase.getId());
            session.clear();

            Optional<PurchaseItem> unexpectedPurchaseItem = purchaseItemRepository.findById(actualPurchaseItemId);
            assertFalse(unexpectedPurchaseItem.isPresent());
        }
    }
}