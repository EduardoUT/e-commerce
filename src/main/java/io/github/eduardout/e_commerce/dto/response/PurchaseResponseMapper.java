package io.github.eduardout.e_commerce.dto.response;

import io.github.eduardout.e_commerce.entity.Customer;
import io.github.eduardout.e_commerce.entity.Purchase;
import io.github.eduardout.e_commerce.entity.User;

import java.util.function.Function;

public class PurchaseResponseMapper implements Function<Purchase, PurchaseResponse> {
    @Override
    public PurchaseResponse apply(Purchase purchase) {
        Customer customer = purchase.getCustomer();
        User user = customer.getUser();
        return new PurchaseResponse(
                purchase.getId(),
                new CustomerWithUserResponse(
                        customer.getId(),
                        new UserResponse(
                                user.getUsername(),
                                null,
                                user.getEmail()
                        )
                ),
                purchase.getPurchaseItems(),
                purchase.getDiscountAmount(),
                purchase.getTotal(),
                purchase.getCreatedAt()
        );
    }
}
