package com.piseth.api.book;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.piseth.api.book.web.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final BookMapStruct bookMapStruct;

    @Override
    public PageInfo<BookDto> findAllBooks(Integer page, Integer limit, String title) {
        PageInfo<Book> bookPageInfo = PageHelper.startPage(page, limit).doSelectPageInfo(() -> bookRepository.select(title));
        return bookMapStruct.toBookDtoList(bookPageInfo);
    }

    @Override
    public BookDto findBookById(Integer id) {
        Book book = bookRepository.selectById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Book With %s Is Not Found!!!", id)));
        return bookMapStruct.toBookDto(book);
    }

    @Override
    public BookDto addNewBook(BookDto bookDto) {
        Book book = bookMapStruct.fromBookDto(bookDto);
        book.setCreated(Date.valueOf(LocalDateTime.now().toLocalDate()));
        book.setIsDeleted(false);
        bookRepository.insert(book);
        return this.findBookById(book.getId());
    }

    @Override
    public BookDto updateBookById(Integer id, BookDto bookDto) {
        if (bookRepository.isExists(id)){
            Book book = bookMapStruct.fromBookDto(bookDto);
            book.setId(id);
            bookRepository.update(book);
            return this.findBookById(id);
        }
         throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                 String.format("Book With %s Is Not Found!!!", id));
    }

    @Override
    public Integer deleteBookById(Integer id) {
        Boolean isFound = bookRepository.isExists(id);
        if (isFound){
            bookRepository.delete(id);
            return id;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Book With %s Is Not Found!!!", id));
    }

    @Override
    public Integer updateIsDeletedBookStatusById(Integer id, Boolean status) {
        Boolean isFound = bookRepository.isExists(id);
        if (isFound){
            bookRepository.updateIsDeletedById(id, status);
            return id;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Book With %s Is Not Found!!!", id));
    }

}
