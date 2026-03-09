package io.github.eduardout.e_commerce.repository;

import io.github.eduardout.e_commerce.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    @Modifying
    @Query(value = "DELETE FROM Orders o WHERE o.customer.id = :customerId")
    void deleteByCustomerId(@Param("customerId") Long id);
    List<Orders> findAllByCustomerId(Long id);
    List<Orders> findAllByCustomerUserUsername(String username);
}
