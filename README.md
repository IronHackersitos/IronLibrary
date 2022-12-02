# IronLibrary
This project's main goal is to create a programme for a library system that can manage books, authors, issues and students. It's required to generate a Database with the necessary tables according to the previous categories.

## Main technologies used
- Java
- Unit testing
- Spring
- SpringBoot
- JPA

## Class diagram

![image](https://user-images.githubusercontent.com/97543256/205185215-e33afe52-76d3-4117-b2e8-58fe05b20d31.png)


## Required Methods
- Add a book: This action is responsible of adding a book and its author in the system. The user will be prompted to enter the details of both the book and the author in the following format
- Search book by title: This action is responsible for searching a book by title.
- Search book by category: This action is responsible for searching a book by category.
- Search book by author: This action is responsible for searching a book by author name.
- List all books along with author: This action is responsible for listing all the books available and there corresponding authors.
- Issue book to student: This action is responsible for creating a student and issuing him/her the specified book. The date issued represent the current date, and the return date should be after 7 days.
- List books by usn: This action is responsible for listing all books rented by the specified student.


## Process
- Definition of class diagram in order to divide tables between members of the team. Creation of basic structure.
- Creation of database with Spring and confirmation of this creation with MySQL.
- Development of main with all necessary methods.
- Unit testing of those methods and main debugging.
