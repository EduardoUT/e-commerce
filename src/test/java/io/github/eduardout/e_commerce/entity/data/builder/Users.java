package io.github.eduardout.e_commerce.entity.data.builder;

import io.github.eduardout.e_commerce.entity.User;

public class Users {
    public static User.UserBuilder anUser() {
        return User.anUser()
                .withUsername("Test_username")
                .withPassword("Pi97st9Z#uk^Eb#")
                .withRole("USER")
                .withEmail("test_username@gmail.com");
    }
}
