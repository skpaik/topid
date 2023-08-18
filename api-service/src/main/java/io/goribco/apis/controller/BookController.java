package io.goribco.apis.controller;

import io.goribco.apis.configs.AppConstants;
import io.goribco.apis.configs.security.annotate.IsUser;
import io.goribco.apis.model.Book;
import io.goribco.apis.repository.AuthorRepository;
import io.goribco.apis.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = AppConstants.crossOriginUrl, allowedHeaders = "*")
public class BookController {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;

    @QueryMapping
    //@CrossOrigin(origins = "http://localhost:3000")
    @IsUser
    public Book bookById(@Argument String id) {
        return Book.getById(id);
        // return bookRepository.getById(id);
    }

    @PostMapping("/api/books")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        try {
            Book _book = bookRepository.save(new Book(book.getId(), book.getName(), book.getPageCount(), book.getAuthorId()));
            return new ResponseEntity<>(_book, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}