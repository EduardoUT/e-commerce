package io.github.eduardout.e_commerce.entity;

import io.github.eduardout.e_commerce.entity.data.UserTestDataLoader;
import io.github.eduardout.e_commerce.entity.data.builder.Users;
import io.github.eduardout.e_commerce.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserTest {
    @Autowired
    private UserRepository userRepository;

    private void setUpUser() {
        UserTestDataLoader userTestDataLoader = new UserTestDataLoader(userRepository);
        userTestDataLoader.setUp();
    }

    @Nested
    class TestNullability {
        @Test
        void testNullUsername() {
            User unexpectedUser = Users.anUser()
                    .withUsername(null)
                    .build();
            assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(unexpectedUser));
        }

        @Test
        void testNullPassword() {
            User unexpectedUser = Users.anUser()
                    .withPassword(null)
                    .build();
            assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(unexpectedUser));
        }

        @Test
        void testNullRole() {
            User unexpectedUser = Users.anUser()
                    .withRole(null)
                    .build();
            assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(unexpectedUser));
        }

        @Test
        void testNullEmail() {
            User unexpectedUser = Users.anUser()
                    .withEmail(null)
                    .build();
            assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(unexpectedUser));
        }
    }

    @Nested
    class TestCrudOperations {
        @Test
        void testFindByUsername() {
            setUpUser();
            Optional<User> expectedUser = userRepository.findByUsername("testUserOne");
            assertTrue(expectedUser.isPresent());
        }

        @Test
        void testFindByEmail() {
            setUpUser();
            Optional<User> expectedUser = userRepository.findByEmail("test_email_two@gmail.com");
            assertTrue(expectedUser.isPresent());
        }
    }

    @Nested
    class TestUnicity {
        @BeforeEach
        void beforeEach() {
            setUpUser();
        }

        @DisplayName("Should restrict a unique username per User")
        @Test
        void testUniqueUsername() {
            User storedUser = userRepository.findByUsername("testUserOne")
                    .orElseThrow(() -> new IllegalStateException(("User not found")));

            User user = User.anUser()
                    .withUsername(storedUser.getUsername())
                    .withPassword("RL5d*H4aa4z#tUp")
                    .withRole("USER")
                    .withEmail("test_email_three@gmail.com")
                    .build();

            assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user));
        }

        @DisplayName("Should restrict a unique email per User")
        @Test
        void testUniqueEmail() {
            User storedUser = userRepository.findByEmail("test_email_two@gmail.com")
                    .orElseThrow(() -> new IllegalStateException("User not found"));

            User user = User.anUser()
                    .withUsername("testUserThree")
                    .withPassword("8dqSU!pzyS9s34^")
                    .withRole("USER")
                    .withEmail(storedUser.getEmail())
                    .build();

            assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user));
        }
    }
}