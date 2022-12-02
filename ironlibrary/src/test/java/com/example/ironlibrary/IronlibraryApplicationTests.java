package com.example.ironlibrary;

import com.example.ironlibrary.models.Author;
import com.example.ironlibrary.models.Book;
import com.example.ironlibrary.models.Issue;
import com.example.ironlibrary.models.Student;
import com.example.ironlibrary.repositories.AuthorRepository;
import com.example.ironlibrary.repositories.BookRepository;
import com.example.ironlibrary.repositories.IssueRepository;
import com.example.ironlibrary.repositories.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    Book book4;

    Author author1;
    Author author2;
    Author author3;
    
    Student student1;
    Issue issue1;
    Issue issue2;

    @BeforeEach
    void setUp() {
        //Creating authors without books
        author1 = new Author("Juan Jose Millas", "juanjo@gmail.com");
        author2 = new Author("Gustave Flaubert", "gustavito@gmail.com");
        author3 = new Author("Franz Kafka", "escarabajito@gmail.com");
        authorRepository.save(author1);
        authorRepository.save(author2);
        authorRepository.save(author3);
        //Creating books and assigning them to authors
        book1 = new Book("8408075969", "El mundo", "Novela", 2, author1);
        book2 = new Book("5408072269", "Madame Bovary", "Novela", 3, author2);
        book3 = new Book("7408075969", "La metamorfosis", "Terror", 1, author3);
        book4 = new Book("1408225969", "El desaparecido", "Novela", 5, author3);
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);
        //Creating student
        student1 = new Student("123456", "Maria");
        studentRepository.save(student1);
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DATE, 7);
        Date returnDate = cal.getTime();
        issue1 = new Issue(now, returnDate, student1, book1);
        issue2 = new Issue(now, returnDate, student1, book2);
        issueRepository.save(issue1);
        issueRepository.save(issue2);
    }

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        studentRepository.deleteAll();
        issueRepository.deleteAll();
    }

    @Test
    void contextLoads() {}

    @Test
    void shouldAddBook_OK() {
        assertEquals(4, bookRepository.findAll().size());
        assertEquals(3, authorRepository.findAll().size());
        assertEquals("Juan Jose Millas", bookRepository.findById("8408075969").get().getAuthor().getName());
        assertEquals("Gustave Flaubert", bookRepository.findById("5408072269").get().getAuthor().getName());
        assertEquals("Franz Kafka", bookRepository.findById("7408075969").get().getAuthor().getName());
    }

    @Test
    void shouldSearchByTitle_OK() {
        Book bookResponse = bookRepository.findByTitle("El mundo").get();
        assertTrue(book1.equals(bookResponse));
    }

    @Test
    void shouldSearchByCategory_OK() {
        List<Book> categoryBooks = bookRepository.findByCategory("Novela");
        assertEquals(3, categoryBooks.size());
    }

    @Test
    void shouldSearchByAuthor_OK() {
        List<Book> authorBooks = bookRepository.findByAuthor(author3);
        assertEquals(2,authorBooks.size());
    }
    
    @Test
    void shouldListAllBooks_OK(){
        List<Book> booksDB = bookRepository.findAll();
        assertEquals(4, booksDB.size());
    }
    
   @Test
    void shouldIssueBookToStudent_OK(){
        Student studentDB = studentRepository.findById("123456").get();
        assertTrue(student1.equals(studentDB));
        //List<Issue> issuesStudent = issueRepository.findByStudentUsn("123456");
        //assertEquals(2, issuesStudent.size());
        assertTrue(student1.getUsn().equals(issue1.getStudent().getUsn()));
        assertTrue(student1.getUsn().equals(issue2.getStudent().getUsn()));
    }
    
    @Test
    void shouldListAllStudentBooks_OK(){
        List<Issue> issuesStudent = issueRepository.findByStudentUsn("123456");
        assertEquals(2, issuesStudent.size());
    }


}
