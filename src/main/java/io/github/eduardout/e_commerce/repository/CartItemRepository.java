package io.github.eduardout.e_commerce.repository;

import io.github.eduardout.e_commerce.entity.CartItem;
import io.github.eduardout.e_commerce.entity.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
    @Modifying
    @Query(value = "DELETE FROM cart_item ci WHERE ci.shoppingCart.id = :shoppingCartId")
    void deleteByShoppingCartId(@Param("shoppingCartId") Long shoppingCartId);
    @Query(value = "SELECT ci FROM cart_item ci WHERE ci.shoppingCart.id = :shoppingCartId")
    List<CartItem> findAllByShoppingCartId(@Param("shoppingCartId") Long shoppingCartId);
}
