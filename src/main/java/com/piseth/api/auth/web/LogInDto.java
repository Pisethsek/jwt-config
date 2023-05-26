package com.piseth.api.auth.web;

import com.piseth.api.user.validator.password.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LogInDto(@Email
                       @NotBlank
                       String email,
                       @Password
                       @NotBlank
                       String password) {
}
