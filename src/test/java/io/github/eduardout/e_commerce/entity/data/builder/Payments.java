package io.github.eduardout.e_commerce.entity.data.builder;

import io.github.eduardout.e_commerce.entity.Payment;
import io.github.eduardout.e_commerce.util.PaymentStatus;

import java.math.BigDecimal;

public class Payments {
    public static Payment.PaymentBuilder aPayment() {
        return Payment.aPayment()
                .withExternalId("stripe_4564645622231")
                .withPaymentStatus(PaymentStatus.PENDING)
                .withAmount(new BigDecimal("200.00"));
    }
}
