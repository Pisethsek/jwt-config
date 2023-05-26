package com.piseth.api.user;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Mapper
public interface UserRepository {

    @SelectProvider(type = UserProvider.class, method = "buildSelectSql")
    List<User> select();

    @SelectProvider(type = UserProvider.class, method = "buildSelectByIdSql")
    Optional<User> selectById(@Param("id") Integer id);

    @InsertProvider(type = UserProvider.class, method = "buildInsertSql")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insert(@Param("user") User user);

    @UpdateProvider(type = UserProvider.class, method = "buildUpdateSql")
    void update(@Param("user") User user);

    @UpdateProvider(type = UserProvider.class, method = "buildDeleteSql")
    void delete(@Param("id") Integer id);

    @Select("SELECT EXISTS(SELECT * FROM users WHERE id = #{id})")
    Boolean isExist(@Param("id") Integer id);

    @Select("SELECT EXISTS(SELECT * FROM users WHERE email = #{email})")
    Boolean existsByEmail(@Param("email") String email);


    @Select("SELECT EXISTS(SELECT * FROM roles WHERE id = #{roleId})")
    Boolean checkRoleId(@Param("roleId") Integer roleId);

}
