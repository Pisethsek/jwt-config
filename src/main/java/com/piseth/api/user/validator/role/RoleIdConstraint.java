package com.piseth.api.user.validator.role;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RoleIdConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface RoleIdConstraint {

    String message() default "Role Id Is Not Exist!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
