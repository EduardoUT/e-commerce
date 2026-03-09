package io.github.eduardout.e_commerce.dto.request;

import jakarta.validation.constraints.*;

public record UserCreationRequest(
        @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters size")
        @NotBlank(message = "Username is mandatory")
        String username,
        @NotBlank(message = "Role is mandatory")
        String role,
        @NotBlank(message = "Password is mandatory")
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.*\\s).{14,19}$",
                message = """
                        Password must commit the following requirements:
                        - Must be at least 15 characters and max 20.
                        - At least 1 lowercase and 1 uppercase character.
                        - At least 1 numeric digit.
                        - At least 1 special character.
                        """)
        String password,
        @NotBlank(message = "Password confirmation is mandatory")
        String passwordConfirmation,

        @Email(regexp = "^[a-z](\\.?[a-z\\d_\\-]){5,63}@[a-z]([_\\-\\.]?([a-z]\\d?)?[a-z]){0,254}$",
                message = """
                         Email must commit the following requirements:
                         - Username (before) @ must be at least 6 characters and max 30.
                         - Domain must be max 62 characters.
                         - Cannot contain sequential dots
                         - Cannot contain sequential underscores
                         - Cannot contain sequential hyphen
                        """)
        @NotBlank(message = "Email cannot be empty")
        String email
) {
    public UserCreationRequest {
        if ((password != null && passwordConfirmation != null)
                && !(password.equals(passwordConfirmation))) {
            throw new IllegalArgumentException("Password and password confirmation don't match");
        }
    }
}
