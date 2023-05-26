package com.piseth.api.auth;

import com.piseth.api.user.Role;
import com.piseth.api.user.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Mapper
public interface AuthRepository {

    @InsertProvider(type = AuthProvider.class, method = "buildRegisterSql")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    Boolean register(@Param("u") User u);

    @InsertProvider(type = AuthProvider.class, method = "buildCreateUserRoleSql")
    void createUserRoles(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    @Select("SELECT * FROM users WHERE email = #{email} AND is_deleted = FALSE")
    @Results(id = "authResultMap", value = {
//            @Result(column = "id", property = "id"),
            @Result(column = "is_verified", property = "isVerified"),
            @Result(column = "verifiedCode", property = "verifiedCode"),
            @Result(column = "id", property = "roles", many = @Many(select = "loadUserRoles"))
    })
    Optional<User> selectByEmail(@Param("email") String email);


    // for security
    @Select("SELECT * FROM users WHERE email = #{email} AND is_deleted = FALSE AND is_verified = TRUE")
    @ResultMap("authResultMap")
    Optional<User> loadUserByEmail(@Param("email") String email);

    @SelectProvider(type = AuthProvider.class, method = "buildLoadUserRolesSql")
    List<Role> loadUserRoles(@Param("id") Integer id);


    @SelectProvider(type = AuthProvider.class, method = "buildSelectByEmailAndVerifiedCodeSql")
    @ResultMap(value = "authResultMap")
    Optional<User> selectByEmailAndVerifiedCode(
            @Param("email") String email,
            @Param("verifiedCode") String verifiedCode
    );

    @UpdateProvider(type = AuthProvider.class, method = "buildUpdateIsVerifiedStatusSql")
    void updateIsVerifiedStatus(
            @Param("email") String email,
            @Param("verifiedCode") String verifiedCode
    );

    @UpdateProvider(type = AuthProvider.class, method = "buildUpdateVerifiedCodeSql")
    boolean updateVerifiedCode(@Param("email") String email, @Param("verifiedCode") String verifiedCode);

}
