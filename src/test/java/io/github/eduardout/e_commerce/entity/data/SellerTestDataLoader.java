package io.github.eduardout.e_commerce.entity.data;

import io.github.eduardout.e_commerce.entity.Seller;
import io.github.eduardout.e_commerce.entity.data.builder.Addresses;
import io.github.eduardout.e_commerce.entity.data.builder.FiscalDatas;
import io.github.eduardout.e_commerce.repository.SellerRepository;

import java.time.LocalDate;
import java.util.Objects;

import static io.github.eduardout.e_commerce.entity.data.builder.PersonalDatas.aPersonalData;
import static io.github.eduardout.e_commerce.entity.data.builder.Users.anUser;

public class SellerTestDataLoader extends TestDataLoader<Seller> {
    public final SellerRepository sellerRepository;

    public SellerTestDataLoader(SellerRepository sellerRepository) {
        super(sellerRepository);
        Objects.requireNonNull(sellerRepository, "SellerRepository cannot be null");
        this.sellerRepository = sellerRepository;
    }

    @Override
    protected void setDefaultTestEntities() {
        addEntity(Seller.aSeller()
                .withUser(anUser()
                        .withUsername("master_seller")
                        .withPassword("%6D9@Kw&7Fypn&C")
                        .withRole("ADMIN")
                        .withEmail("andre_andrews@gmail.com")
                        .build())
                .withPersonalData(aPersonalData()
                        .withFirstName("Andre")
                        .withPaternalSurname("Stantman")
                        .withMaternalSurname("Kingston")
                        .withBirthDate(LocalDate.of(1985, 11, 23))
                        .withPhoneNumber("5548848944")
                        .build())
                .withAddress(Addresses.anAddress()
                        .withStreet("Link street")
                        .withNeighborhood("Callable")
                        .withMunicipality("San Francisco")
                        .withState("California")
                        .withZipCode("A-4556")
                        .withExternalNumber(12)
                        .withInternalNumber(null)
                        .build())
                .withFiscalData(FiscalDatas.aFiscalData()
                        .withRfc("SRG456654SD15")
                        .withRegimeTax("56")
                        .withFiscalAddress("Johnson Q street #120")
                        .build())
                .build());
    }
}
