package com.piseth.api.book;

import org.apache.ibatis.jdbc.SQL;

public class BookProvider {

    static private final String table_name = "books";

    public String buildSelectSql(){
        return new SQL(){{
            SELECT("*");
            FROM(table_name);
            WHERE("title ilike '%' || #{title} || '%' ", "is_deleted = FALSE");
            ORDER_BY("id DESC");
        }}.toString();
    }

    public String buildSelectByIdSql(){
        return new SQL(){{
            SELECT("*");
            FROM(table_name);
            WHERE("id = #{id}","is_deleted = FALSE");
        }}.toString();
    }

    public String buildInsertSql(){
        return new SQL(){{
            INSERT_INTO(table_name);
            VALUES("title", "#{book.title}");
            VALUES("image", "#{book.image}");
            VALUES("description", "#{book.description}");
            VALUES("created", "#{book.created}");
            VALUES("is_deleted", "#{book.isDeleted}");
        }}.toString();
    }

    public String buildUpdateSql(){
        return new SQL(){{
            UPDATE(table_name);
            SET("title = #{book.title}");
            SET("description = #{book.description}");
            SET("image = #{book.image}");
            WHERE("id = #{book.id}");
        }}.toString();
    }

    public String buildDeleteSql(){
        return new SQL(){{
            DELETE_FROM(table_name);
            WHERE("id = #{id}");
        }}.toString();
    }

    public String buildDeleteByStatusSql(){
        return new SQL(){{
            UPDATE(table_name);
            SET("is_deleted = #{status}");
            WHERE("id = #{id}");
        }}.toString();
    }

}
