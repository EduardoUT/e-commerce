package io.github.eduardout.e_commerce.entity.data;

import io.github.eduardout.e_commerce.entity.*;
import io.github.eduardout.e_commerce.repository.PurchaseRepository;

import java.math.BigDecimal;
import java.util.*;

public class PurchaseTestDataLoader extends TestDataLoader<Purchase> {

    private final List<Product> products;
    private final Customer customer;

    public PurchaseTestDataLoader(PurchaseRepository purchaseRepository, List<Product> products, Customer customer) {
        super(purchaseRepository);
        validateEntities(products);
        validateEntity(customer);
        this.customer = customer;
        this.products = products;
    }

    @Override
    protected void setDefaultTestEntities() {
        List<Purchase> purchases = products.stream()
                .map(product -> {
                    Integer quantity = 5;
                    BigDecimal calculation = product.getSellPrice().multiply(new BigDecimal(quantity.toString()));
                    return Purchase.aPurchase()
                            .withCustomer(customer)
                            .withPurchaseItemElement(PurchaseItem.aPurchaseItem()
                                    .withProduct(product)
                                    .withQuantity(quantity)
                                    .withLineAmount(calculation)
                                    .build())
                            .withSubTotal(calculation)
                            .withTotal(calculation)
                            .build();
                }).toList();
        addEntities(purchases);
    }

    public List<PurchaseItem> getPurchaseItems(List<Purchase> purchases) {
        validateEntities(purchases);
        return purchases
                .stream()
                .flatMap(purchase -> purchase.getPurchaseItems().stream())
                .toList();
    }
}
