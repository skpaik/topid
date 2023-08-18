package io.goribco.apis.repository;

import io.goribco.apis.model.Author;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface AuthorRepository extends MongoRepository<Author, String> {
    Author getById(String id);
}