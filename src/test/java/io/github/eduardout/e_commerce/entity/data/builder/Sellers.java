package io.github.eduardout.e_commerce.entity.data.builder;

import io.github.eduardout.e_commerce.entity.*;

public class Sellers {
    public static Seller.SellerBuilder aSeller() {
        return Seller.aSeller()
                .withUser(Users.anUser().build())
                .withPersonalData(PersonalDatas.aPersonalData().build())
                .withAddress(Addresses.anAddress().build())
                .withFiscalData(FiscalDatas.aFiscalData().build());
    }
}
