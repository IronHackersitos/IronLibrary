package com.example.ironlibrary;

import com.example.ironlibrary.models.*;
import com.example.ironlibrary.repositories.*;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest

public class AllTest {


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
        //Creating dates
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DATE, 7);
        Date returnDate = cal.getTime();
        //Creating issues 
        issue1 = new Issue(now, returnDate, student1, book1);
        issue2 = new Issue(now, returnDate, student1, book2);
        issueRepository.save(issue1);
        issueRepository.save(issue2);
    }

    @AfterEach
    void tearDown() {
        issueRepository.deleteAll();
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        studentRepository.deleteAll();
    }


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
        assertEquals(2, authorBooks.size());
    }

    @Test
    void shouldListAllBooks_OK() {
        List<Book> booksDB = bookRepository.findAll();
        assertEquals(4, booksDB.size());
    }

    @Test
    void shouldIssueBookToStudent_OK() {
        Student studentDB = studentRepository.findById("123456").get();
        assertTrue(student1.equals(studentDB));
        assertTrue(student1.getUsn().equals(issue1.getStudent().getUsn()));
        assertTrue(student1.getUsn().equals(issue2.getStudent().getUsn()));
    }

    @Test
    void shouldListAllStudentBooks_OK() {
        List<Issue> issuesStudent = issueRepository.findByStudentUsn("123456");
        assertEquals(2, issuesStudent.size());
    }

    @Test
    void shouldListAllBooksDueToday_OK() {
        //Setting a string with the fake return date of today + 7 days
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DATE, 7);
        Date returnDate = cal.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
        String dateStr = formatter.format(returnDate);
        //Getting all issues and saving in a hashMap the Books of the ones with matching returnDate to the one settled above
        List<Issue> issues = issueRepository.findAll();
        Map<Integer, Book> allBooksDueToday = new HashMap<>();
        for (Issue issue : issues) {
            Date returnDateDB = issue.getReturnDate();
            String dateStrDB = formatter.format(returnDateDB);
            if (dateStr.equals(dateStrDB)) {
                allBooksDueToday.put(issue.getIssueId(), issue.getBook());
            }
        }
        //List should have 2 as the day the tests are run, it's always going to be the same the books are issued.
        assertEquals(2, allBooksDueToday.size());
    }
}


