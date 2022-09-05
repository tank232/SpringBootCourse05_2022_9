package com.av.controller;

import com.av.db.Book;
import com.av.dto.BookDto;
import com.av.exeptions.NotFoundException;
import com.av.repository.BookRepository;
import com.av.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookRepository bookRepository;

    @GetMapping(value = "/book/all", produces = "application/json; charset=UTF-8")
    public List<BookDto> getBooks() {
        return bookRepository.findAll().stream().map(BookDto::toDto).collect(Collectors.toList());
    }


    @GetMapping(value = "/book/{title}", produces = "application/json; charset=UTF-8")
    public BookDto getBook(@PathVariable("title") String title) {
        Optional<Book> bookByTitle = bookRepository.findBookByTitle(title);
        if(!bookByTitle.isPresent())
        {
            new NotFoundException(String.format("Не найдена книга {}",title));
        }
        return bookByTitle.map(BookDto::toDto).get();
    }

    @PostMapping(value = "/book/save", produces = "application/json; charset=UTF-8")
    public BookDto saveBook(BookDto bookDto) {
        Book book = bookRepository.save(BookDto.toBook(bookDto));
        return BookDto.toDto(book);
    }



    @DeleteMapping(value = "/book/save", produces = "application/json; charset=UTF-8")
    public void deketeBook(BookDto bookDto) {
        bookRepository.delete(BookDto.toBook(bookDto));
    }
}
