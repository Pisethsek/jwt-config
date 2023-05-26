package com.piseth.api.auth.web;

import com.piseth.api.user.validator.email.EmailUnique;
import com.piseth.api.user.validator.password.Password;
import com.piseth.api.user.validator.password.PasswordMatch;
import com.piseth.api.user.validator.role.RoleIdConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@PasswordMatch(message = "Your Password Is Not Match", password = "password", confirmedPassword = "confirmedPassword")
public record RegisterDto(@NotBlank(message = "email is required")
                          @EmailUnique
                          @Email
                          String email,

                          @NotBlank(message = "password is required")
                          @Password
                          String password,

                          @NotBlank(message = "confirm password is required")
                          @Password
                          String confirmedPassword,

                          @NotNull(message = "roles are required")
                          @RoleIdConstraint
                          List<Integer> roleIds) {
}
