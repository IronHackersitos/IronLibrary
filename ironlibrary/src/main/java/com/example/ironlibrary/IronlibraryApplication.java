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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootApplication
public class IronlibraryApplication implements CommandLineRunner {
//Uncomment line underneath and comment the one above in order to make the tests work    
//public class IronlibraryApplication {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    StudentRepository studentRepository;

    public static void main(String[] args) {
        SpringApplication.run(IronlibraryApplication.class, args);
    }

    //Comment all in: public void run(String... args) throws Exception, in order to make the tests work
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
                    if (authorRepository.findByName(bookAuthor).isPresent()) {
                        book.setAuthor(authorRepository.findByName(bookAuthor).get());
                    } else {
                        book.setAuthor(authorRepository.save(new Author(bookAuthor, bookAuthorEmail)));
                    }
                    bookRepository.save(book);
                    break;
                case 2:
                    System.out.println("Which title are you looking for?");
                    bookTitle = saveString(input);
                    if (bookRepository.findByTitle(bookTitle).isPresent()) {
                        Book bookDB = bookRepository.findByTitle(bookTitle).get();
                        System.out.printf(String.format("%-20s%-20s%-20s%-20s\n", "Book ISBN", "Book Title", "Category", "No of Books"));
                        System.out.printf(bookDB.toString() + "\n");
                    } else System.err.println("That book doesn't exist in our DB");
                    break;
                case 3:
                    System.out.println("Which category are you looking for?");
                    bookCategory = saveString(input);
                    if (bookRepository.findByCategory(bookCategory).isEmpty() == false) {
                        List<Book> books = bookRepository.findByCategory(bookCategory);
                        System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s\n", "Book ISBN", "Book Title", "Category", "No of Books", "Author Name", "Author email");
                        for (Book bookItem : books) {
                            System.out.printf(bookItem.toString() + bookItem.getAuthor().toString() + "\n");
                        }
                    } else System.err.println("That category doesn't exist in our DB or there are no books in it.");
                    break;
                case 4:
                    System.out.println("Which author are you looking for?");
                    bookAuthor = saveString(input);
                    if (authorRepository.findByName(bookAuthor).isEmpty() == false) {
                        List<Book> books = bookRepository.findByAuthor(authorRepository.findByName(bookAuthor).get());
                        System.out.printf(String.format("%-20s%-20s%-20s%-20s\n", "Book ISBN", "Book Title", "Category", "No of Books"));
                        for (Book bookItem : books) {
                            System.out.printf(bookItem.toString() + "\n");
                        }
                    } else System.err.println("That author doesn't exist in our DB.");
                    break;
                case 5:
                    System.out.println("These are all our books and their author\n");
                    List<Book> books = bookRepository.findAll();
                    System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s\n", "Book ISBN", "Book Title", "Category", "No of Books", "Author Name", "Author email");
                    for (Book bookItem : books) {
                        System.out.printf(bookItem.toString() + bookItem.getAuthor().toString() + "\n");
                    }
                    break;
                case 6:
                    System.out.println("Which is book's ISBN?");
                    bookISBN = saveString(input);
                    //Checking the book exists in our DB
                    if (bookRepository.findById(bookISBN).isPresent() == false) {
                        System.err.println("The book doesn't exist in our DB.");
                        break;
                    } else if (bookRepository.findById(bookISBN).get().getIssue() != null) {
                        System.err.println("This book has already been issued, please enter another ISBN.");
                        break;
                    }
                    //Finding book and setting possible return date
                    book = bookRepository.findById(bookISBN).get();
                    Date now = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(now);
                    cal.add(Calendar.DATE, 7);
                    Date returnDate = cal.getTime();
                    SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
                    String dateStr = formatter.format(returnDate);

                    //Checking the student already exists in our DB
                    System.out.println("Which is the student's usn?");
                    String studentUsn = saveString(input);
                    if (studentRepository.findById(studentUsn).isPresent()) {
                        //Creating Issue
                        Issue issue = new Issue(now, returnDate, studentRepository.findById(studentUsn).get(), book);
                        issueRepository.save(issue);
                        book.setIssue(issue);
                        bookRepository.save(book);
                        System.out.println("Book issued. Return date: " + dateStr);
                        break;

                    } else {
                        System.out.println("Student isn't saved in our DB. One will be created with previous USN");
                        System.out.println("Enter name:");
                        String studentName = saveString(input);
                        Student student = new Student(studentUsn, studentName);
                        studentRepository.save(student);
                        //Creating Issue
                        Issue issue = new Issue(now, returnDate, student, book);
                        issueRepository.save(issue);
                        book.setIssue(issue);
                        bookRepository.save(book);
                        System.out.println("Book issued. Return date: " + dateStr);
                        break;
                    }
                case 7:
                    System.out.println("Which students USN do you want to look up?");
                    studentUsn = saveString(input);
                    if (studentRepository.findById(studentUsn).isPresent()) {
                        List<Issue> issuesStudent = issueRepository.findByStudentUsn(studentUsn);
                        System.out.printf(String.format("%-20s%-20s%-20s\n", "Book Title", "Student Name", "Return Date"));
                        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
                        for (Issue issue : issuesStudent) {
                            dateStr = formatter.format(issue.getReturnDate());
                            System.out.printf("%-20s%-20s%-20s\n", issue.getBook().getTitle(), issue.getStudent().getName(), dateStr);
                        }
                    } else System.err.println("That student doesn't exist in our DB");
                    break;
                case 8:
                    //Checking if there are any issues at all
                    List<Issue> issues = issueRepository.findAll();
                    if (issues.size() == 0) {
                        System.err.println("There are no issues yet.");
                        break;
                    }
                    //Setting date and formatting to a string only with day month and year (no time) with current date
                    now = new Date();
                    formatter = new SimpleDateFormat("MMM dd yyyy");
                    String dateStr1 = formatter.format(now);
                    //Going through the issues list and comparing the formatted returnDate to the one of today. Saving each book in a hashmap.
                    Map<Integer, Book> allBooksDueToday = new HashMap<>();
                    for (Issue issue : issues) {
                        Date returnDateDB = issue.getReturnDate();
                        String dateStr2 = formatter.format(returnDateDB);
                        if (dateStr1.equals(dateStr2)) {
                            allBooksDueToday.put(issue.getIssueId(), issue.getBook());
                        }
                    }
                    //After saving books due to today we can check if there is none or print them if the hashmap has books
                    if (allBooksDueToday.size() == 0) {
                        System.err.println("There are no books due today.");
                        break;
                    }
                    System.out.printf(String.format("%-20s%-20s%-20s%-20s\n", "Book ISBN", "Book Title", "Category", "No of Books"));
                    for (Book bookItem : allBooksDueToday.values()) {
                        System.out.printf(bookItem.toString() + "\n");
                    }
                    break;
                case 9:
                    System.out.println("C ya!!!");
                    isRunning = false;
                    break;
            }
        }

    }

    //MENU
    private static void showMenu() {
        System.out.println("1. Add a book to library");
        System.out.println("2. Search book by title");
        System.out.println("3. Search book by category");
        System.out.println("4. Search book by author");
        System.out.println("5. List all books along with author ");
        System.out.println("6. Issue book to student");
        System.out.println("7. List books by usn");
        System.out.println("8. List books due today");
        System.out.println("9. Exit");
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
