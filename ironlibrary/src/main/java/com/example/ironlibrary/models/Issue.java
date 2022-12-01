package com.example.ironlibrary.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer issueId;

    private LocalDate issueDate;
    private LocalDate returnDate;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
   
    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public Issue() {
    }

    public Issue(LocalDate issueDate, LocalDate returnDate, Student issueStudent, Book issueBook) {
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.student = issueStudent;
        this.book = issueBook;
    }

    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return returnDate.toString();
    }
}
