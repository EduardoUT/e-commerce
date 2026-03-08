package io.github.eduardout.e_commerce.entity;

import io.github.eduardout.e_commerce.entity.data.ProductCategoryTestDataLoader;
import io.github.eduardout.e_commerce.entity.data.ProductTestDataLoader;
import io.github.eduardout.e_commerce.entity.data.ShoppingCartTestDataLoader;
import io.github.eduardout.e_commerce.entity.data.builder.ShoppingCarts;
import io.github.eduardout.e_commerce.repository.*;
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
class ShoppingCartTest {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductRepository productRepository;
    private ShoppingCart shoppingCart;
    private Set<Product> products;

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
        private final Class<DataIntegrityViolationException> dataIntegrityViolationExceptionClass =
                DataIntegrityViolationException.class;

        @Test
        void testNullSubTotal() {
            ShoppingCart unexpectedShoppingCart = ShoppingCarts.aShoppingCart()
                    .withSubTotal(null)
                    .build();
            assertThrows(dataIntegrityViolationExceptionClass, () -> shoppingCartRepository.save(unexpectedShoppingCart));
        }

        @Test
        void testNullTotal() {
            ShoppingCart unexpectedShoppingCart = ShoppingCarts.aShoppingCart()
                    .withTotal(null)
                    .build();
            assertThrows(dataIntegrityViolationExceptionClass, () -> shoppingCartRepository.save(unexpectedShoppingCart));
        }
    }

    @Nested
    class TestCrudOperations {
        @BeforeEach
        void beforeEach() {
            setUpShoppingCart();
        }

        @Test
        void testSaveShoppingCart() {
            ShoppingCart expectedShoppingCart = shoppingCart;
            assertAll(
                    () -> assertNotNull(expectedShoppingCart),
                    () -> assertNotNull(expectedShoppingCart.getId())
            );
        }

        @Test
        void testUpdateShoppingCart() {
            ShoppingCart actualShoppingCart = shoppingCart;

            BigDecimal subTotal = actualShoppingCart.getSubTotal();
            BigDecimal discount = subTotal.multiply(new BigDecimal(".2"));
            BigDecimal subTotalWithDiscount = subTotal.subtract(discount);
            actualShoppingCart.setSubTotal(subTotalWithDiscount);

            ShoppingCart expectedShoppingCart = shoppingCartRepository.save(actualShoppingCart);

            assertAll(
                    () -> assertNotNull(expectedShoppingCart),
                    () -> assertNotEquals(subTotal, subTotalWithDiscount)
            );
        }

        @Test
        void testDeleteByShoppingCartId() {
            ShoppingCart actualShoppingCart = shoppingCart;

            Long shoppingCartId = actualShoppingCart.getId();
            cartItemRepository.deleteByShoppingCartId(shoppingCartId);
            List<CartItem> unexpectedCartItems = cartItemRepository.findAllByShoppingCartId(shoppingCartId);
            assertTrue(unexpectedCartItems.isEmpty());
            shoppingCartRepository.deleteShoppingCartById(shoppingCartId);
            Optional<ShoppingCart> unexpectedShoppingCart = shoppingCartRepository.findByShoppingCartId(shoppingCartId);
            assertFalse(unexpectedShoppingCart.isPresent());
        }

        @Test
        void testFindByShoppingCartId() {
            ShoppingCart actualShoppingCart = shoppingCart;
            Long expectedShoppingCartId = actualShoppingCart.getId();
            Optional<ShoppingCart> expectedShoppingCart = shoppingCartRepository.findByShoppingCartId(expectedShoppingCartId);
            assertTrue(expectedShoppingCart.isPresent());
        }
    }
}