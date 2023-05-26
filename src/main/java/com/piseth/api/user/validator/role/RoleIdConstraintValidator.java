package com.piseth.api.user.validator.role;

import com.piseth.api.user.UserRepository;
import com.piseth.api.user.validator.role.RoleIdConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RoleIdConstraintValidator implements ConstraintValidator<RoleIdConstraint, List<Integer>> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(List<Integer> roleIds, ConstraintValidatorContext context) {

        for(Integer roleId : roleIds){
            if(!userRepository.checkRoleId(roleId)){
                return false;
            }
        }
        return true;
    }

}
