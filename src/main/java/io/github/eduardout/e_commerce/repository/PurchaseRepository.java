package io.github.eduardout.e_commerce.repository;

import io.github.eduardout.e_commerce.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    @Modifying
    @Query(value = "DELETE FROM Purchase p WHERE p.customer.id = :customerId")
    void deleteByCustomerId(@Param("customerId") Long customerId);
    List<Purchase> findAllByCustomerId(Long customerId);
}
