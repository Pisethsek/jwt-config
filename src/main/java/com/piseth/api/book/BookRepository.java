package com.piseth.api.book;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Mapper
public interface BookRepository {

    @SelectProvider(type = BookProvider.class, method = "buildSelectSql")
    @Results(id = "resultMapBook", value = {
            @Result(column = "is_deleted", property = "isDeleted")
    })
    List<Book> select(@Param("title") String title);

    @SelectProvider(type = BookProvider.class, method = "buildSelectByIdSql")
    @ResultMap("resultMapBook")
    Optional<Book> selectById(@Param("id") Integer id);

    @InsertProvider(type = BookProvider.class, method = "buildInsertSql")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insert(@Param("book") Book book);

    @UpdateProvider(type = BookProvider.class, method = "buildUpdateSql")
    void update(@Param("book") Book book);

    @DeleteProvider(type = BookProvider.class, method = "buildDeleteSql")
    void delete(@Param("id") Integer id);

    @UpdateProvider(type = BookProvider.class, method = "buildDeleteByStatusSql")
    void updateIsDeletedById(@Param("id") Integer id, @Param("status") Boolean status);

    @Select("SELECT EXISTS(SELECT * FROM books WHERE id = #{id})")
    Boolean isExists(@Param("id") Integer id);

}
