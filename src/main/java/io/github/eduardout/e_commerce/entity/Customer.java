package io.github.eduardout.e_commerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = {"purchases", "payments", "orders"})
public class Customer implements Identifiable<Long> {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    @Getter
    @Setter
    @Embedded
    @Column(nullable = false, unique = true)
    private PersonalData personalData;
    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name = "customer_address",
            joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "address_id"))
    private Address address;
    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name = "customer_fiscal_data",
            joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "fiscal_data_id"))
    private FiscalData fiscalData;
    @Getter
    @OneToMany(mappedBy = "customer", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Purchase> purchases = new ArrayList<>();
    @Getter
    @OneToMany(mappedBy = "customer", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Payment> payments = new ArrayList<>();
    @Getter
    @OneToMany(mappedBy = "customer", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Orders> orders = new ArrayList<>();
    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "customer_shopping_cart",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "shopping_cart_id"))
    private ShoppingCart shoppingCart;

    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
        purchase.setCustomer(this);
    }

    public void removePurchase(Purchase purchase) {
        purchases.remove(purchase);
        purchase.setCustomer(null);
    }

    public void addPayment(Payment payment) {
        payments.add(payment);
        payment.setCustomer(this);
    }

    public void removePayment(Payment payment) {
        payments.remove(payment);
        payment.setCustomer(null);
    }

    public void addOrders(Orders orders) {
        this.orders.add(orders);
        orders.setCustomer(this);
    }

    public void removeOrder(Orders orders) {
        this.orders.remove(orders);
        orders.setCustomer(null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if(!(obj instanceof Customer other)) {
            return false;
        }
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public static CustomerBuilder aCustomer() {
        return new CustomerBuilder();
    }

    public static class CustomerBuilder {
        private User user;
        private PersonalData personalData;
        private Address address;
        private FiscalData fiscalData;
        private ShoppingCart shoppingCart;
        private final List<Purchase> purchases = new ArrayList<>();
        private final List<Payment> payments = new ArrayList<>();
        private final List<Orders> orders = new ArrayList<>();

        private CustomerBuilder() {
        }

        public CustomerBuilder withUser(User user) {
            this.user = user;
            return this;
        }

        public CustomerBuilder withPersonalData(PersonalData personalData) {
            this.personalData = personalData;
            return this;
        }

        public CustomerBuilder withAddress(Address address) {
            this.address = address;
            return this;
        }

        public CustomerBuilder withFiscalData(FiscalData fiscalData) {
            this.fiscalData = fiscalData;
            return this;
        }

        public CustomerBuilder withPurchaseElement(Purchase purchase) {
            this.purchases.add(purchase);
            return this;
        }

        public CustomerBuilder withPaymentElement(Payment payment) {
            this.payments.add(payment);
            return this;
        }

        public CustomerBuilder withOrderElement(Orders orders) {
            this.orders.add(orders);
            return this;
        }

        public CustomerBuilder withShoppingCart(ShoppingCart shoppingCart) {
            this.shoppingCart = shoppingCart;
            return this;
        }

        public Customer build() {
            Customer customer = new Customer();
            customer.setUser(user);
            customer.setAddress(address);
            customer.setPersonalData(personalData);
            customer.setFiscalData(fiscalData);
            customer.setShoppingCart(shoppingCart);

            this.purchases.forEach(customer::addPurchase);
            this.payments.forEach(customer::addPayment);
            this.orders.forEach(customer::addOrders);
            return customer;
        }
    }
}
