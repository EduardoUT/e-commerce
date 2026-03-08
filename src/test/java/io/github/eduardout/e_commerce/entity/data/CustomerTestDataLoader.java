package io.github.eduardout.e_commerce.entity.data;

import io.github.eduardout.e_commerce.entity.*;
import io.github.eduardout.e_commerce.repository.CustomerRepository;

import java.time.LocalDate;

import static io.github.eduardout.e_commerce.entity.data.builder.Customers.aCustomer;
import static io.github.eduardout.e_commerce.entity.data.builder.PersonalDatas.aPersonalData;
import static io.github.eduardout.e_commerce.entity.data.builder.Users.anUser;

public class CustomerTestDataLoader extends TestDataLoader<Customer> {

    public CustomerTestDataLoader(CustomerRepository customerRepository) {
        super(customerRepository);
    }

    @Override
    protected void setDefaultTestEntities() {
        addEntity(aCustomer()
                .withUser(anUser()
                        .withUsername("userCustomerOne")
                        .withPassword("@s7D$q6CS7c&TpF")
                        .withEmail("user_customer_one@gmail.com")
                        .build())
                .withPersonalData(aPersonalData()
                        .withFirstName("Sara")
                        .withPaternalSurname("Oxford")
                        .withMaternalSurname("Mathews")
                        .withBirthDate(LocalDate.of(1980, 9, 23))
                        .withPhoneNumber("55-45646545")
                        .build())
                .withAddress(Address.anAddress()
                        .withStreet("Ildefonso")
                        .withNeighborhood("St Ciro")
                        .withMunicipality("El Condado")
                        .withState("Jalisco")
                        .withZipCode("45666")
                        .withExternalNumber(50)
                        .withInternalNumber(null)
                        .build())
                .withFiscalData(FiscalData.aFiscalData()
                        .withRfc("FHGH45645SD56")
                        .withRegimeTax("59")
                        .withFiscalAddress("St Ethiene #27")
                        .build())
                .build());
        addEntity(aCustomer()
                .withUser(anUser()
                        .withUsername("johndoe")
                        .withPassword("W2X4DoRnQB7&kg$")
                        .withEmail("john_doe@gmail.com")
                        .build())
                .withPersonalData(aPersonalData()
                        .withFirstName("Alfred")
                        .withPaternalSurname("Kingston")
                        .withMaternalSurname("Johnson")
                        .withBirthDate(LocalDate.of(1979, 5, 15))
                        .withPhoneNumber("55-46545464")
                        .build())
                .withAddress(null)
                .withFiscalData(FiscalData.aFiscalData()
                        .withRfc("GHJJ4566SA411")
                        .withRegimeTax("20")
                        .withFiscalAddress("St Francisco #100, Cal")
                        .build())
                .build());
    }
}
