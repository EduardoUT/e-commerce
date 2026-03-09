package io.github.eduardout.e_commerce.repository;

import io.github.eduardout.e_commerce.entity.Seller;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    @Override
    @NullMarked
    @EntityGraph(attributePaths = {"user", "personalData", "address", "fiscalData"})
    Optional<Seller> findById(Long id);

    @EntityGraph(attributePaths = {"user", "personalData", "address", "fiscalData"})
    Optional<Seller> findByUserUsername(String username);
}
