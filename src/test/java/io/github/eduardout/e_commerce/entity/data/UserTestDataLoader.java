package io.github.eduardout.e_commerce.entity.data;

import io.github.eduardout.e_commerce.entity.User;
import io.github.eduardout.e_commerce.repository.UserRepository;

public class UserTestDataLoader extends TestDataLoader<User> {

    public UserTestDataLoader(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    protected void setDefaultTestEntities() {
        addEntity(User.anUser()
                .withUsername("testUserOne")
                .withPassword("M^nbmSs65fcDiiB")
                .withRole("USER")
                .withEmail("test_email_one@gmail.com")
                .build());
        addEntity(User.anUser()
                .withUsername("testUserTwo")
                .withPassword("GXnLBq^5#4GUR26")
                .withRole("USER")
                .withEmail("test_email_two@gmail.com")
                .build());
    }
}
