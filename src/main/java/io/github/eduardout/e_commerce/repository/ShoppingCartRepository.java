package io.github.eduardout.e_commerce.repository;

import io.github.eduardout.e_commerce.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    /**
     * To delete a ShoppingCart entity this method should be invoked first as is managed from a join table as an
     * strategy to optional relationship between a Customer and a ShoppingCart to prevent NULL foreign keys.
     *
     * @param shoppingCartId Identifier of the ShoppingCart entity
     */
    @Modifying
    @Query(value = "DELETE FROM customer_shopping_cart WHERE shopping_cart_id = ?1", nativeQuery = true)
    void deleteCustomerShoppingCartAssociation(Long shoppingCartId);

    @Modifying(flushAutomatically = true)
    @Query(value = "DELETE FROM shopping_cart sc WHERE sc.id = :shoppingCartId")
    void deleteShoppingCartById(@Param("shoppingCartId") Long shoppingCartId);

    @Query(value = "SELECT sc FROM shopping_cart sc WHERE sc.id = :id")
    Optional<ShoppingCart> findByShoppingCartId(@Param("id") Long id);
}
