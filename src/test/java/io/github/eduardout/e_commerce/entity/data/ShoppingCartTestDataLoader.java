package io.github.eduardout.e_commerce.entity.data;

import io.github.eduardout.e_commerce.entity.CartItem;
import io.github.eduardout.e_commerce.entity.Product;
import io.github.eduardout.e_commerce.entity.ShoppingCart;
import io.github.eduardout.e_commerce.repository.ShoppingCartRepository;

import java.math.BigDecimal;
import java.util.*;

import static io.github.eduardout.e_commerce.entity.ShoppingCart.aShoppingCart;
import static io.github.eduardout.e_commerce.util.Calculation.DEFAULT_AMOUNT;

public class ShoppingCartTestDataLoader extends TestDataLoader<ShoppingCart> {
    private final List<Product> products;

    public ShoppingCartTestDataLoader(ShoppingCartRepository shoppingCartRepository, List<Product> products) {
        super(shoppingCartRepository);
        validateEntities(products);
        this.products = products;
    }

    @Override
    protected void setDefaultTestEntities() {
        addEntity(buildShoppingCartWithProducts(aShoppingCart()));
    }

    private ShoppingCart buildShoppingCartWithProducts(ShoppingCart.ShoppingCartBuilder shoppingCartBuilder) {
        BigDecimal subTotal = new BigDecimal("0.00");
        Integer quantity = 5;
        for (Product product : products) {
            BigDecimal calculation = product.getSellPrice().multiply(new BigDecimal(quantity.toString()));
            shoppingCartBuilder
                    .withCartItemElement(CartItem.aCartItem()
                            .withProduct(product)
                            .withQuantity(quantity)
                            .withLineAmount(calculation)
                            .build());
            subTotal = subTotal.add(calculation);
        }
        shoppingCartBuilder.withDiscountAmount(DEFAULT_AMOUNT);
        shoppingCartBuilder.withSubTotal(subTotal);
        shoppingCartBuilder.withTotal(subTotal);
        return shoppingCartBuilder.build();
    }
}
