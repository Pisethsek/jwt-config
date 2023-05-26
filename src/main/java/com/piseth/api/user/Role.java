package com.piseth.api.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role implements GrantedAuthority {

    private Integer id;
    private String name;

    @Override
    public String getAuthority() {
        return "ROLE_" + name; // ROLE_CUSTOMER
    }

}
