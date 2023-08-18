package io.goribco.apis.model;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.List;

@Data
@Document(collection = "books")
public class Book {
    private static final List<Book> books = Arrays.asList(
            new Book("book-1", "Harry Potter and the Philosopher's Stone", 223, "author-1"),
            new Book("book-2", "Moby Dick", 635, "author-2"),
            new Book("book-3", "Interview with the vampire", 371, "author-3")
    );
    private final String name;
    private final int pageCount;
    @Getter
    private final String authorId;
    @Getter
    @Id
    private String id;

    public Book(String id, String name, int pageCount, String authorId) {
        this.id = id;
        this.name = name;
        this.pageCount = pageCount;
        this.authorId = authorId;
    }

    public static Book getById(String id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst().orElse(null);
    }

}