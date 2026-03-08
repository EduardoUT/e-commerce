package io.github.eduardout.e_commerce.entity.data.builder;

import io.github.eduardout.e_commerce.entity.Customer;

import static io.github.eduardout.e_commerce.entity.data.builder.Addresses.anAddress;
import static io.github.eduardout.e_commerce.entity.data.builder.FiscalDatas.aFiscalData;
import static io.github.eduardout.e_commerce.entity.data.builder.PersonalDatas.aPersonalData;
import static io.github.eduardout.e_commerce.entity.data.builder.Users.anUser;

public class Customers {
    public static Customer.CustomerBuilder aCustomer() {
        return Customer.aCustomer()
                .withUser(anUser().build())
                .withPersonalData(aPersonalData().build())
                .withAddress(anAddress().build())
                .withFiscalData(aFiscalData().build());
    }
}
