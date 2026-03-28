package io.github.eduardout.e_commerce.repository;

import io.github.eduardout.e_commerce.entity.Customer;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@NullMarked
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = """
            SELECT c FROM Customer c
            WHERE c.id = :id
            """)
    @EntityGraph(attributePaths = {"user"})
    Optional<Customer> findByIdWithUser(@Param("id") Long id);

    Optional<Customer> findByUserUsername(String username);

    @Override
    @EntityGraph(attributePaths = {"user", "personalData", "address"})
    List<Customer> findAll();
}
