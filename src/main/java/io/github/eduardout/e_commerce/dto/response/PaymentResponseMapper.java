package io.github.eduardout.e_commerce.dto.response;

import io.github.eduardout.e_commerce.entity.Customer;
import io.github.eduardout.e_commerce.entity.Payment;
import io.github.eduardout.e_commerce.entity.User;

import java.util.function.Function;

public class PaymentResponseMapper implements Function<Payment, PaymentResponse> {

    @Override
    public PaymentResponse apply(Payment payment) {
        Customer customer = payment.getCustomer();
        User user = customer.getUser();
        return new PaymentResponse(
                payment.getId(),
                new CustomerWithUserResponse(
                        customer.getId(),
                        new UserResponse(
                                user.getUsername(),
                                null,
                                user.getEmail()
                        )
                ),
                payment.getPaymentStatus(),
                payment.getExternalId(),
                payment.getGatewayName(),
                payment.getAmount(),
                null
        );
    }
}
