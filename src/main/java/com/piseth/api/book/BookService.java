package com.piseth.api.book;

import com.github.pagehelper.PageInfo;
import com.piseth.api.book.web.BookDto;

public interface BookService {
    PageInfo<BookDto> findAllBooks(Integer page, Integer limit, String title);
    BookDto findBookById(Integer id);
    BookDto addNewBook(BookDto bookDto);
    BookDto updateBookById(Integer id, BookDto bookDto);
    Integer deleteBookById(Integer id);
    Integer updateIsDeletedBookStatusById(Integer id, Boolean status);
}
