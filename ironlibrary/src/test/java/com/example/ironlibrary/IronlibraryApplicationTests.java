package com.example.ironlibrary;

import com.example.ironlibrary.models.Author;
import com.example.ironlibrary.models.Book;
import com.example.ironlibrary.repositories.AuthorRepository;
import com.example.ironlibrary.repositories.BookRepository;
import com.example.ironlibrary.repositories.IssueRepository;
import com.example.ironlibrary.repositories.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class IronlibraryApplicationTests {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    StudentRepository studentRepository;

    Book book1;
    Book book2;
    Book book3;

    Author author1;
    Author author2;
    Author author3;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
        //chapterRepository.deleteAll();
    }

    @Test
    void contextLoads() {
        
    }

    @Test
    void shouldAddBook_OK() {
        book1 = new Book("8408075969", "El mundo", "Novela", 2);
        bookRepository.save(book1);
        assertEquals(1, bookRepository.findAll().size());
        author1 = new Author("Juan Jose Millas", "juanjo@gmail.com");
        authorRepository.save(author1);
        assertEquals(1, authorRepository.findAll().size());
        book1.setAuthor(author1);
        bookRepository.save(book1);
        assertEquals("Juan Jose Millas", bookRepository.findById("8408075969").get().getAuthor());
    }
/*
    @Test
    void shouldSearchByTitle_OK() {
        Book book1 = new Book("8408075969", "El mundo", "Novela", 2);
        bookRepository.save(book1);
        Book bookResponse = bookRepository.findByTitle(book1.getTitle()).get();
        assertTrue(book1.equals(bookResponse));
    }

    @Test
    void shouldSearchByCategory_OK() {
        Book book1 = new Book("8408075969", "El mundo", "Novela", 2);
        bookRepository.save(book1);
        assertEquals("Novela", bookRepository.findById("8408075969").get().getCategory());
    }

    @Test
    void shouldSearchByAuthor_OK() {
        Author author1 = new Author("Juan Jose Millas", "juanjo@gmail.com");
        authorRepository.save(author1);
        Author AuthorResponse = authorRepository.findByName(author1.getName()).get();
        assertTrue(author1.equals(AuthorResponse));
    }*/

}
