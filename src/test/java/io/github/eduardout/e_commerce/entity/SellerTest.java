package io.github.eduardout.e_commerce.entity;

import io.github.eduardout.e_commerce.entity.data.SellerTestDataLoader;
import io.github.eduardout.e_commerce.entity.data.builder.Sellers;
import io.github.eduardout.e_commerce.repository.SellerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SellerTest {
    @Autowired
    private SellerRepository sellerRepository;
    private Seller seller;
    private final Class<DataIntegrityViolationException> dataIntegrityViolationExceptionClass =
            DataIntegrityViolationException.class;

    private void setUpTestSeller() {
        SellerTestDataLoader sellerTestDataLoader = new SellerTestDataLoader(sellerRepository);
        seller = sellerTestDataLoader.setUp()
                .stream()
                .findFirst()
                .orElseThrow();
    }

    @Nested
    class TestNullability {
        private Seller unexpectedSeller;

        @Test
        void testNullUser() {
            unexpectedSeller = Sellers.aSeller()
                    .withUser(null)
                    .build();
            assertThrows(dataIntegrityViolationExceptionClass, () -> sellerRepository.save(unexpectedSeller));
        }

        @Test
        void testNullPersonalData() {
            unexpectedSeller = Sellers.aSeller()
                    .withPersonalData(null)
                    .build();
            assertThrows(dataIntegrityViolationExceptionClass, () -> sellerRepository.save(unexpectedSeller));
        }

        @Test
        void testNullAddress() {
            unexpectedSeller = Sellers.aSeller()
                    .withAddress(null)
                    .build();
            assertThrows(dataIntegrityViolationExceptionClass, () -> sellerRepository.save(unexpectedSeller));
        }
    }

    @Nested
    class TestCrudOperations {
        @Test
        void testSaveSeller() {
            Seller expectedSeller = Seller.aSeller()
                    .withUser(User.anUser()
                            .withUsername("paquidermus")
                            .withPassword("ueP;}>_Az9Cl9%5")
                            .withRole("USER")
                            .withEmail("paquidermus@gmail.com")
                            .build())
                    .withPersonalData(PersonalData.aPersonalData()
                            .withFirstName("Paco")
                            .withMaternalSurname("López")
                            .withPaternalSurname("Gómez")
                            .withBirthDate(LocalDate.of(1983, 2, 12))
                            .withPhoneNumber("55-4567587106")
                            .build())
                    .withAddress(Address.anAddress()
                            .withStreet("St Cosmic street")
                            .withNeighborhood("Los Santos")
                            .withMunicipality("Los Angeles")
                            .withState("California")
                            .withZipCode("D-45656")
                            .withExternalNumber(23)
                            .withInternalNumber(2)
                            .build())
                    .withFiscalData(FiscalData.aFiscalData()
                            .withRfc("DHJK45656DSE5D")
                            .withRegimeTax("45")
                            .withFiscalAddress("Ave Coin Street #12")
                            .build())
                    .build();

            sellerRepository.save(expectedSeller);

            assertAll(
                    () -> assertNotNull(expectedSeller),
                    () -> assertNotNull(expectedSeller.getId())
            );
        }

        @Test
        void testUpdateSeller() {
            setUpTestSeller();
            Seller actualSeller = seller;
            User actualUser = actualSeller.getUser();
            String unexpectedUsername = actualUser.getUsername();
            String unexpectedEmail = actualUser.getEmail();

            actualUser.setUsername("seller_user");
            actualUser.setEmail("seller_andrews@gmail.com");

            PersonalData actualPersonalData = actualSeller.getPersonalData();
            String unexpectedPhoneNumber = actualPersonalData.getPhoneNumber();
            actualPersonalData.setPhoneNumber("55-12-78-56-445");

            Address actualAddress = actualSeller.getAddress();
            String unexpectedStreet = actualAddress.getStreet();
            String unexpectedNeighborhood = actualAddress.getNeighborhood();

            actualAddress.setStreet("Main Street");
            actualAddress.setNeighborhood("Constitution");

            FiscalData actualFiscalData = actualSeller.getFiscalData();
            String unexpectedRegimeTax = actualFiscalData.getRegimeTax();

            actualFiscalData.setRegimeTax("27");

            Seller expectedSeller = sellerRepository.save(actualSeller);
            User expectedUser = expectedSeller.getUser();

            PersonalData expectedPersonalData = expectedSeller.getPersonalData();
            Address expectedAddress = expectedSeller.getAddress();
            FiscalData expectedFiscalData = expectedSeller.getFiscalData();

            assertAll(
                    () -> assertNotNull(actualSeller),
                    () -> assertNotEquals(unexpectedUsername, expectedUser.getUsername()),
                    () -> assertNotEquals(unexpectedEmail, expectedUser.getEmail()),
                    () -> assertNotEquals(unexpectedPhoneNumber, expectedPersonalData.getPhoneNumber()),
                    () -> assertNotEquals(unexpectedStreet, expectedAddress.getStreet()),
                    () -> assertNotEquals(unexpectedNeighborhood, expectedAddress.getNeighborhood()),
                    () -> assertNotEquals(unexpectedRegimeTax, expectedFiscalData.getRegimeTax())
            );
        }

        @Test
        void testFindByUsername() {
            setUpTestSeller();

            Optional<Seller> expectedSeller = sellerRepository.findByUserUsername("master_seller");
            assertTrue(expectedSeller.isPresent());
        }
    }

    @Nested
    class TestUnicity {
        @DisplayName("A Seller should be associated with a unique user identifier")
        @Test
        void testUniqueUser() {
            setUpTestSeller();
            Seller actualSeller = seller;

            Seller unexpectedSeller = Sellers.aSeller()
                    .withUser(actualSeller.getUser())
                    .build();

            assertThrows(dataIntegrityViolationExceptionClass, () -> sellerRepository.save(unexpectedSeller));
        }

        @DisplayName("A Seller should be associated with a unique phone number")
        @Test
        void testUniquePersonalDataByPhoneNumber() {
            setUpTestSeller();
            Seller actualSeller = seller;

            Seller unexpectedSeller = Sellers.aSeller()
                    .withPersonalData(actualSeller.getPersonalData())
                    .build();

            assertThrows(dataIntegrityViolationExceptionClass, () -> sellerRepository.save(unexpectedSeller));
        }

        @DisplayName("A Seller should be associated with a unique address identifier")
        @Test
        void testUniqueAddress() {
            setUpTestSeller();
            Seller actualSeller = seller;
            Seller unexpectedSeller = Sellers.aSeller()
                    .withAddress(actualSeller.getAddress())
                    .build();

            assertThrows(dataIntegrityViolationExceptionClass, () -> sellerRepository.save(unexpectedSeller));
        }

        @DisplayName("A Seller should be associated with a unique fiscal data identifier")
        @Test
        void testUniqueFiscalData() {
            setUpTestSeller();
            Seller actualSeller = seller;

            Seller unexpectedSeller = Sellers.aSeller()
                    .withFiscalData(actualSeller.getFiscalData())
                    .build();

            assertThrows(dataIntegrityViolationExceptionClass, () -> sellerRepository.save(unexpectedSeller));
        }
    }
}