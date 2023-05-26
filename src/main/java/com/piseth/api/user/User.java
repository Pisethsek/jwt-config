package com.piseth.api.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private Integer profile;
    private Boolean isDeleted;

    //auth feature info
    private String email;
    private String password;
    private Boolean isVerified;
    private String verifiedCode;

    // User has roles
    private List<Role> roles;
}
