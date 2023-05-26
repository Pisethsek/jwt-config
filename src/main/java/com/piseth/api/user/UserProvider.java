package com.piseth.api.user;

import org.apache.ibatis.jdbc.SQL;

public class UserProvider {

    public String buildSelectSql(){
        return new SQL(){{
            SELECT("*");
            FROM("users");
            WHERE("is_deleted = FALSE");
        }}.toString();
    }

    public String buildSelectByIdSql(){
        return new SQL(){{
            SELECT("*");
            FROM("users");
            WHERE("id = #{id}", "is_deleted = FALSE");
        }}.toString();
    }

    public String buildInsertSql(){
        return new SQL(){{
            INSERT_INTO("users");
            VALUES("username", "#{user.username}");
            VALUES("profile", "#{user.profile}");
            VALUES("is_deleted", "#{user.isDeleted}");
            VALUES("email", "#{user.email}");
            VALUES("password", "#{user.password}");
            VALUES("is_verified", "#{user.isVerified}");
            VALUES("verified_code", "#{user.verifiedCode}");
        }}.toString();
    }

    public String buildUpdateSql(){
        return new SQL(){{
            UPDATE("users");
            SET("username = #{user.username}");
            SET("profile = #{user.profile}");
            SET("is_deleted = #{user.isDeleted}");
            SET("email = #{user.email}");
            SET("password = #{user.password}");
            SET("is_verified = #{user.isVerified}");
            SET("verified_code = #{user.verifiedCode}");
            WHERE("id = #{user.id}");
        }}.toString();
    }

    public String buildDeleteSql(){
        return new SQL(){{
            UPDATE("users");
            SET("is_deleted = TRUE");
            WHERE("id = #{id}");
        }}.toString();
    }

}
