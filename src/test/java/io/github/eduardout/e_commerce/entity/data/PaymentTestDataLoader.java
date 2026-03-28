package io.github.eduardout.e_commerce.entity.data;

import io.github.eduardout.e_commerce.entity.Customer;
import io.github.eduardout.e_commerce.entity.Payment;
import io.github.eduardout.e_commerce.entity.data.builder.Payments;
import io.github.eduardout.e_commerce.repository.PaymentRepository;
import io.github.eduardout.e_commerce.util.PaymentStatus;

import java.math.BigDecimal;
import java.util.*;

public class PaymentTestDataLoader extends TestDataLoader<Payment> {
    private final Customer customer;

    public PaymentTestDataLoader(PaymentRepository paymentRepository, Customer customer) {
        super(paymentRepository);
        validateEntity(customer);
        this.customer = customer;
    }

    public static Payment getRandomPayment(Set<Payment> payments) {
        List<Payment> paymentList = payments.stream().toList();
        Random random = new Random();
        int randomIdx = random.nextInt(0, paymentList.size());
        return paymentList.get(randomIdx);
    }

    @Override
    protected void setDefaultTestEntities() {
        addEntity(Payments.aPayment()
                .withCustomer(customer)
                .withPaymentStatus(PaymentStatus.PENDING)
                .withExternalId("st_dnjaknjkas4654654")
                .withGatewayName("stripe")
                .withAmount(new BigDecimal("600.00"))
                .build());
        addEntity(Payments.aPayment()
                .withCustomer(customer)
                .withPaymentStatus(PaymentStatus.PENDING)
                .withExternalId("st_ashkjhkasjk445456989")
                .withGatewayName("stripe")
                .withAmount(new BigDecimal("350.00"))
                .build());
    }
}
