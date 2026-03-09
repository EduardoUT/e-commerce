package io.github.eduardout.e_commerce.repository;

import io.github.eduardout.e_commerce.entity.Customer;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@NullMarked
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Override
    @EntityGraph(attributePaths = {"user", "personalData", "address"})
    Optional<Customer> findById(Long id);

    @EntityGraph(attributePaths = {"user", "personalData", "address"})
    Optional<Customer> findByUserUsername(String username);

    @Override
    @EntityGraph(attributePaths = {"user", "personalData", "address"})
    List<Customer> findAll();

    @Query(value = """
             SELECT DISTINCT c FROM Customer c
             LEFT JOIN FETCH c.shoppingCart sc
             LEFT JOIN FETCH sc.cartItems
            """)
    List<Customer> findAllWithShoppingCart();
}
