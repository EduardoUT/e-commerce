package io.github.eduardout.e_commerce.entity;

import io.github.eduardout.e_commerce.entity.data.ProductCategoryTestDataLoader;
import io.github.eduardout.e_commerce.entity.data.ProductTestDataLoader;
import io.github.eduardout.e_commerce.entity.data.ShoppingCartTestDataLoader;
import io.github.eduardout.e_commerce.entity.data.builder.CartItems;
import io.github.eduardout.e_commerce.repository.CartItemRepository;
import io.github.eduardout.e_commerce.repository.ProductCategoryRepository;
import io.github.eduardout.e_commerce.repository.ProductRepository;
import io.github.eduardout.e_commerce.repository.ShoppingCartRepository;
import org.hibernate.AssertionFailure;
import org.junit.jupiter.api.BeforeEach;
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
class CartItemTest {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductRepository productRepository;
    private ShoppingCart shoppingCart;
    private final Class<AssertionFailure> assertionFailureClass = AssertionFailure.class;
    private final Class<DataIntegrityViolationException> dataIntegrityViolationExceptionClass =
            DataIntegrityViolationException.class;

    @BeforeEach
    void setUp() {
        setUpShoppingCart();
    }

    private void setUpShoppingCart() {
        ProductCategoryTestDataLoader productCategoryTestDataLoader = new ProductCategoryTestDataLoader(productCategoryRepository);
        ProductTestDataLoader productTestDataLoader = new ProductTestDataLoader(
                productRepository,
                productCategoryTestDataLoader.setUp()
        );

        Set<Product> products = productTestDataLoader.setUp();
        ShoppingCartTestDataLoader shoppingCartTestDataLoader = new ShoppingCartTestDataLoader(
                shoppingCartRepository,
                products
        );

        shoppingCart = shoppingCartTestDataLoader.setUp()
                .stream()
                .findFirst()
                .orElseThrow();
    }

    @Nested
    class TestNullability {
        @Test
        void testNullShoppingCart() {
            Product product = productRepository.findByName("Xiaomi Redmi 9").orElseThrow();

            CartItem unexpectedCartItem = CartItems.aCartItem()
                    .withShoppingCart(null)
                    .withProduct(product)
                    .build();

            assertThrows(assertionFailureClass, () -> cartItemRepository.save(unexpectedCartItem));
        }

        @Test
        void testNullProduct() {
            ShoppingCart actualShoppingCart = shoppingCart;

            CartItem unexpectedCartItem = CartItems.aCartItem()
                    .withShoppingCart(actualShoppingCart)
                    .withProduct(null)
                    .build();

            assertThrows(assertionFailureClass, () -> cartItemRepository.save(unexpectedCartItem));
        }

        @Test
        void testNullQuantity() {
            ShoppingCart actualShoppingCart = shoppingCart;
            Product product = productRepository.findByName("Xiaomi Redmi 9").orElseThrow();

            CartItem unexpectedCartItem = CartItems.aCartItem()
                    .withShoppingCart(actualShoppingCart)
                    .withProduct(product)
                    .withQuantity(null)
                    .build();

            assertThrows(dataIntegrityViolationExceptionClass, () -> cartItemRepository.saveAndFlush(unexpectedCartItem));
        }

        @Test
        void testNullLineAmount() {
            ShoppingCart actualShoppingCart = shoppingCart;
            Product product = productRepository.findByName("Xiaomi Redmi 9").orElseThrow();

            CartItem unexpectedCartItem = CartItems.aCartItem()
                    .withShoppingCart(actualShoppingCart)
                    .withProduct(product)
                    .withLineAmount(null)
                    .build();

            assertThrows(dataIntegrityViolationExceptionClass, () -> cartItemRepository.saveAndFlush(unexpectedCartItem));
        }
    }

    @Nested
    class TestCrudOperations {
        @Test
        void testSaveCartItem() {
            ShoppingCart actualShoppingCart = shoppingCart;
            Product product = productRepository.findByName("Xiaomi Redmi 9").orElseThrow();

            Integer quantity = 2;
            BigDecimal calculation = product.getSellPrice().multiply(new BigDecimal(quantity.toString()));
            CartItem actualCartItem = CartItem.aCartItem()
                    .withShoppingCart(actualShoppingCart)
                    .withProduct(product)
                    .withQuantity(quantity)
                    .withLineAmount(calculation)
                    .build();
            CartItem expectedCartItem = cartItemRepository.save(actualCartItem);
            assertAll(
                    () -> assertNotNull(expectedCartItem),
                    () -> assertNotNull(expectedCartItem.getShoppingCart().getId()),
                    () -> assertNotNull(expectedCartItem.getProduct().getId())
            );
        }

        @Test
        void testUpdateCartItem() {
            ShoppingCart actualShoppingCart = shoppingCart;
            CartItem actualCartItem = actualShoppingCart.getCartItems().stream().findFirst().orElseThrow();

            Integer unexpectedQuantity = actualCartItem.getQuantity();
            BigDecimal unexpectedLineAmount = actualCartItem.getLineAmount();

            Integer quantity = 2;
            BigDecimal calculation = actualCartItem.getProduct().getSellPrice().multiply(new BigDecimal(quantity.toString()));

            actualCartItem.setQuantity(quantity);
            actualCartItem.setLineAmount(calculation);

            CartItem expectedCartItem = cartItemRepository.save(actualCartItem);

            assertAll(
                    () -> assertNotNull(expectedCartItem),
                    () -> assertNotEquals(unexpectedQuantity, expectedCartItem.getQuantity()),
                    () -> assertNotEquals(unexpectedLineAmount, expectedCartItem.getLineAmount())
            );
        }

        @Test
        void testFindAllByShoppingCartId() {
            ShoppingCart actualShoppingCart = shoppingCart;
            List<CartItem> expectedShoppingCart = cartItemRepository.findAllByShoppingCartId(actualShoppingCart.getId());
            assertFalse(expectedShoppingCart.isEmpty());
        }

        @Test
        void testDeleteByShoppingCartId() {
            ShoppingCart actualShoppingCart = shoppingCart;
            Long shoppingCartId = actualShoppingCart.getId();
            Set<CartItem> cartItems = actualShoppingCart.getCartItems();

            cartItems.forEach(cartItem -> cartItemRepository.deleteByShoppingCartId(shoppingCartId));

            List<CartItem> unexpectedCartItems = cartItemRepository.findAllByShoppingCartId(shoppingCartId);
            assertTrue(unexpectedCartItems.isEmpty());
        }
    }
}