package io.github.eduardout.e_commerce.repository;

import io.github.eduardout.e_commerce.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
