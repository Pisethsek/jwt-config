package com.piseth.api.user.validator.email;

import com.piseth.api.user.UserRepository;
import com.piseth.api.user.validator.email.EmailUnique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailUniqueConstraintsValidator implements ConstraintValidator<EmailUnique, String> {

    private final UserRepository userRepository;

    public EmailUniqueConstraintsValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !userRepository.existsByEmail(email);
    }

    @Override
    public void initialize(EmailUnique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

}
