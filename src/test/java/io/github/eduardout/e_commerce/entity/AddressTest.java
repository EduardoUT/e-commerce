package io.github.eduardout.e_commerce.entity;

import io.github.eduardout.e_commerce.entity.data.builder.Addresses;
import io.github.eduardout.e_commerce.repository.AddressRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AddressTest {
    @Autowired
    private AddressRepository addressRepository;

    @Nested
    class TestNullability {
        private final Class<DataIntegrityViolationException> dataIntegrityViolationExceptionClass =
                DataIntegrityViolationException.class;
        private Address unexpectedAddress;

        @Test
        void testNullStreet() {
            unexpectedAddress = Addresses.anAddress()
                    .withStreet(null)
                    .build();
            assertThrows(dataIntegrityViolationExceptionClass, () -> addressRepository.save(unexpectedAddress));
        }

        @Test
        void testNullNeighborhood() {
            unexpectedAddress = Addresses.anAddress()
                    .withNeighborhood(null)
                    .build();
            assertThrows(dataIntegrityViolationExceptionClass, () -> addressRepository.save(unexpectedAddress));
        }

        @Test
        void testNullMunicipality() {
            unexpectedAddress = Addresses.anAddress()
                    .withMunicipality(null)
                    .build();
            assertThrows(dataIntegrityViolationExceptionClass, () -> addressRepository.save(unexpectedAddress));
        }

        @Test
        void testNullState() {
            unexpectedAddress = Addresses.anAddress()
                    .withState(null)
                    .build();
            assertThrows(dataIntegrityViolationExceptionClass, () -> addressRepository.save(unexpectedAddress));
        }

        @Test
        void testNullZipCode() {
            unexpectedAddress = Addresses.anAddress()
                    .withZipCode(null)
                    .build();
            assertThrows(dataIntegrityViolationExceptionClass, () -> addressRepository.save(unexpectedAddress));
        }

        @Test
        void testNullExternalNumber() {
            unexpectedAddress = Addresses.anAddress()
                    .withExternalNumber(null)
                    .build();
            assertThrows(dataIntegrityViolationExceptionClass, () -> addressRepository.save(unexpectedAddress));
        }
    }
}