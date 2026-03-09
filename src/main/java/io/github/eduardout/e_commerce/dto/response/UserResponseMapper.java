package io.github.eduardout.e_commerce.dto.response;

import io.github.eduardout.e_commerce.entity.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserResponseMapper implements Function<User, UserResponse> {
    @Override
    public UserResponse apply(User user) {
        return new UserResponse(
                user.getUsername(),
                user.getRole(),
                user.getEmail()
        );
    }
}
