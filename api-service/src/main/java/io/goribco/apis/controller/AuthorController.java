package io.goribco.apis.controller;

import io.goribco.apis.model.Author;
import io.goribco.apis.model.Book;
import io.goribco.apis.repository.AuthorRepository;
import io.goribco.apis.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;

    @SchemaMapping
    public Author author(Book book) {
        return Author.getById(book.getAuthorId());
        //return authorRepository.getById(bookRepository.getById(book.getId()).getAuthorId());
    }

    @PostMapping("/api/authors")
    public ResponseEntity<Author> createTutorial(@RequestBody Author author) {
        try {
            Author _author = authorRepository.save(new Author(author.getId(), author.getFirstName(), author.getLastName()));
            return new ResponseEntity<>(_author, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}