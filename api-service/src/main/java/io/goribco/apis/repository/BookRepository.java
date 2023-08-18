package io.goribco.apis.repository;

import io.goribco.apis.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface BookRepository extends MongoRepository<Book, String> {
    Book getById(String id);
}