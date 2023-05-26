package com.piseth.api.book.web;

import com.piseth.api.book.BookService;
import com.piseth.base.BaseRest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Slf4j
public class BookRestController {

    private final BookService bookService;

    //find all books using pagination
    @GetMapping
    public BaseRest<?> findAllBooks(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                    @RequestParam(name = "limit", required = false, defaultValue = "2") Integer limit,
                                    @RequestParam(name = "title", required = false, defaultValue = "") String title){
        var books = bookService.findAllBooks(page, limit, title);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("All Books Has Been Retrieved")
                .timestamp(LocalDateTime.now())
                .data(books)
                .build();
    }

    //find book by id
    @GetMapping("/{id}")
    public BaseRest<?> findBookById(@PathVariable("id") Integer id){
        var book = bookService.findBookById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Book Has Been Found")
                .timestamp(LocalDateTime.now())
                .data(book)
                .build();
    }

    //add new book
    @PostMapping
    public BaseRest<?> addNewBook(@RequestBody @Valid BookDto bookDto){
        var book = bookService.addNewBook(bookDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("New Book Has Been Created")
                .timestamp(LocalDateTime.now())
                .data(book)
                .build();
    }

    //update book by id
    @PutMapping("/{id}")
    public BaseRest<?> updateBookById(@PathVariable("id") Integer id, @RequestBody BookDto body){
        var bookId = bookService.updateBookById(id, body);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Book Has Been Updated")
                .timestamp(LocalDateTime.now())
                .data(bookId)
                .build();
    }

    //delete book by id
    @DeleteMapping("/{id}")
    public BaseRest<?> deleteBookById(@PathVariable("id") Integer id){
        var bookId = bookService.deleteBookById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Book Has Been Deleted")
                .timestamp(LocalDateTime.now())
                .data(bookId)
                .build();
    }

    // delete book by update status
    @PutMapping("/{id}/is-deleted")
    public BaseRest<?> updateIsDeletedBookStatusById(@PathVariable("id") Integer id, @RequestBody IsDeletedDto dto){
        var bookId = bookService.updateIsDeletedBookStatusById(id, dto.status());
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Book Has Been Deleted")
                .timestamp(LocalDateTime.now())
                .data(bookId)
                .build();
    }

}
