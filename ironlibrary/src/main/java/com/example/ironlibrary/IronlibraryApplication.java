package com.example.ironlibrary;

import com.example.ironlibrary.models.Author;
import com.example.ironlibrary.models.Book;
import com.example.ironlibrary.models.Issue;
import com.example.ironlibrary.models.Student;
import com.example.ironlibrary.repositories.AuthorRepository;
import com.example.ironlibrary.repositories.BookRepository;
import com.example.ironlibrary.repositories.IssueRepository;
import com.example.ironlibrary.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class IronlibraryApplication implements CommandLineRunner {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository  authorRepository;
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    StudentRepository studentRepository;

    public static void main(String[] args) {
        SpringApplication.run(IronlibraryApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Welcome to IRONLIBRARY by IRONHACKERSITOS");
        Scanner input = new Scanner(System.in);

        System.out.println("What do you want to do? =)");
        boolean isRunning = true;
        while (isRunning) {
            showMenu();
            int selection = 0;

            try {
                selection = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.err.println("Enter only integer values");
                input.nextLine();
            }

            switch (selection) {
                case 1:
                    System.out.println("Which is the book's ISBN?");
                    String bookISBN = saveString(input);
                    System.out.println("Which is the book's title?");
                    String bookTitle = saveString(input);
                    System.out.println("Which is the book's category?");
                    String bookCategory = saveString(input);
                    System.out.println("Which is the book's quantity?");
                    Integer bookQuantity = saveInt(input);
                    Book book = new Book(bookISBN, bookTitle, bookCategory, bookQuantity);
                    System.out.println("Who is the book's author?");
                    String bookAuthor = saveString(input);
                    System.out.println("Which is the author's email?");
                    String bookAuthorEmail = saveString(input);
                    Author author = new Author(bookAuthor, bookAuthorEmail);
                    book.setAuthor(author);
                    authorRepository.save(author);
                    bookRepository.save(book);
                    break;
                case 2:
                    System.out.println("Which title are you looking for?");
                    bookTitle = saveString(input);
                    if(bookRepository.findByTitle(bookTitle).isPresent()) {
                        Book bookDB = bookRepository.findByTitle(bookTitle).get();
                        System.out.printf(String.format("%-20s%-20s%-20s%-20s\n", "Book ISBN","Book Title","Category","No of Books"));
                        System.out.printf(bookDB.toString() + "\n");
                    }
                    else System.err.println("That book doesn't exist in our DB");
                    break;
                case 3:
                    System.out.println("Which category are you looking for?");
                    bookCategory = saveString(input);
                    if(bookRepository.findByCategory(bookCategory).isEmpty() == false) {
                        List<Book> books = bookRepository.findByCategory(bookCategory);
                        System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s\n","Book ISBN","Book Title","Category","No of Books","Author Name","Author email\n");
                        for(Book bookItem : books){
                            System.out.printf(bookItem.toString());
                            System.out.println(bookItem.getAuthor().toString());
                        }
                    }
                    else System.err.println("That category doesn't exist in our DB or there are no books in it.");
                    break;
                case 4:
                    System.out.println("Which author are you looking for?");
                    bookAuthor = saveString(input);
                    if(authorRepository.findByName(bookAuthor).isEmpty() == false) {
                        List<Book> books = bookRepository.findByAuthor(authorRepository.findByName(bookAuthor).get());
                        System.out.println(books.toString());
                    }
                    else System.err.println("That author doesn't exist in our DB.");
                    break;
                case 5:
                    System.out.println("Here's a list with all our books and their author");
                    //System.out.println("Book ISBN          Book Title          Category          No of Books          Author Name          Author email");
                    List<Book> books = bookRepository.findAll();
                    for(Book bookItem : books){

                        System.out.printf(bookItem.toString());
                        System.out.printf(bookItem.getAuthor().toString());
                    }
                    break;
                case 6:
                    System.out.println("Which is book's ISBN?");
                    bookISBN = saveString(input);
                    System.out.println("Which is the student's usn?");
                    String studentUsn = saveString(input);
                    System.out.println("Which is the student's name");
                    String studentName = saveString(input);
                    if(bookRepository.findById(bookISBN).isPresent()){
                        book = bookRepository.findById(bookISBN).get();
                        Student student = new Student(studentUsn, studentName);
                        studentRepository.save(student);
                        Issue issue = new Issue(LocalDate.now(), LocalDate.now().plusDays(7), student, book);
                        issueRepository.save(issue);
                    }
                    else System.err.println("The book doesn't exist in our DB.");
                    break;
                case 7:
                    System.out.println("Which students USN do you want to look up?");
                    studentUsn = saveString(input);
                    if(studentRepository.findById(studentUsn).isPresent()){
                        List<Issue> issuesStudent = issueRepository.findByStudentUsn(studentUsn);
                        for (Issue issue : issuesStudent){
                            issue.getBook().getTitle().toString();
                            System.out.println("\n");
                            issue.getStudent().toString();
                            System.out.println("\n");
                            issue.getReturnDate().toString();
                        }
                    }
                    else System.err.println("That student doesn't exist in our DB");
                    break;
                case 8:
                    System.out.println("C ya!!!");
                    isRunning = false;
                    break;
                default:
                    System.err.println("Enter only integer values");

            }
        }
    }

    //MENU
    private static void showMenu() {
        //System.out.println("Select an option:");
        System.out.println("1. Add a book to library");
        System.out.println("2. Search book by title");
        System.out.println("3. Search book by category");
        System.out.println("4. Search book by author");
        System.out.println("5. List all books along with author ");
        System.out.println("6. Issue book to student");
        System.out.println("7. List books by usn");
        System.out.println("8. Exit");
    }

    //INPUT SAVING
    public static String saveString(Scanner input) {
        String name = "";
        while (name == null || name == "") {
            System.out.println("Enter a word/sentence");
            if (input.hasNextLine())
                name = input.nextLine();
        }
        return name;
    }

    public static int saveInt(Scanner input) {
        int num = -1;
        while (num <= 0) {
            System.out.println("Enter a positive number");
            try {
                num = Integer.valueOf(input.nextLine());
            } catch (NumberFormatException e) {
                num = -1;
            }
            if (num <= 0) {
                System.err.println("Enter a positive number");
            }
        }
        return num;
    }

    public static double saveDouble(Scanner input) {
        int num = -1;
        while (num <= 0) {
            System.out.println("Enter a positive number");
            try {
                num = Integer.valueOf(input.nextLine());
            } catch (NumberFormatException e) {
                num = -1;
            }
            if (num <= 0) {
                System.err.println("Enter a positive number");
            }
        }
        return num;
    }


}
