package io.github.eduardout.e_commerce.repository;

import io.github.eduardout.e_commerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    Optional<User> findById(Long userId);

    @Override
    List<User> findAll();

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
