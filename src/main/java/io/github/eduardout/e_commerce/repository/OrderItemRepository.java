package io.github.eduardout.e_commerce.repository;

import io.github.eduardout.e_commerce.entity.OrderItem;
import io.github.eduardout.e_commerce.entity.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
    @Modifying
    @Query(value = "DELETE FROM order_item oi WHERE oi.orders.id = :orderId")
    void deleteByOrderId(@Param("orderId") Long orderId);

    @Query(value = "SELECT oi FROM order_item oi WHERE oi.orders.id = :orderId")
    List<OrderItem> findAllByOrderId(@Param("orderId") Long orderId);
}
