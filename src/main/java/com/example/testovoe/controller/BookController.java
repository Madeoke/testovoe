package com.example.testovoe.controller;

import com.example.testovoe.Repo.BookRepository;
import com.example.testovoe.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookRepository repository;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> fooList = (List<Book>) repository.findAll();
        if (fooList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(fooList, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {

        Optional<Book> foo = repository.findById(id);
        return foo.isPresent() ? new ResponseEntity<>(foo.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody @Valid Book foo) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(BookController.class).slash(foo.getId())
                .toUri());
        Book savedBook;
        try {
            savedBook = repository.save(foo);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(savedBook, httpHeaders, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") long id, @RequestBody Book book) {
        boolean isBookPresent = repository.existsById(Long.valueOf(id));

        if (!isBookPresent) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Book updatedBook = repository.save(book);

        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }
}