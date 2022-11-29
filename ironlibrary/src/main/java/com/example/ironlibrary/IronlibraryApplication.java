package com.example.ironlibrary;

import com.example.ironlibrary.models.Book;
import com.example.ironlibrary.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class IronlibraryApplication implements CommandLineRunner {

    @Autowired
    BookRepository bookRepository;

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
                    bookRepository.save(book);
                    break;
                case 2:
                    System.out.println("Which title are you looking for?");
                    bookTitle = saveString(input);
                    if(bookRepository.findByTitle(bookTitle).isPresent()) {
                        Book bookDB = bookRepository.findByTitle(bookTitle).get();
                        System.out.println(bookDB.toString());
                    }
                    else System.out.println("That doesn't exist in our DB");
                    break;
                case 3:
                    System.out.println("Which category are you looking for?");
                    bookCategory = saveString(input);
                    if(bookRepository.findByCategory(bookCategory).isEmpty() == false) {
                        List<Book> books = bookRepository.findByCategory(bookCategory);
                        System.out.println(books.toString());
                    }
                    else System.out.println("That category doesn't exist or there are no books in it.");
                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:

                    break;
                case 7:

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
