package com.piseth.api.book;

import com.github.pagehelper.PageInfo;
import com.piseth.api.book.web.BookDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapStruct {//book

    PageInfo<BookDto> toBookDtoList(PageInfo<Book> bookPageInfo);

    BookDto toBookDto(Book book);

    Book fromBookDto(BookDto bookDto);

}
