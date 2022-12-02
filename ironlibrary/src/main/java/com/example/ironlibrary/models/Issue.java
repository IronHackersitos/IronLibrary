package com.example.ironlibrary.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer issueId;

    private Date issueDate;
    private Date returnDate;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
   
    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public Issue() {
    }

    public Issue(Date issueDate, Date returnDate, Student issueStudent, Book issueBook) {
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

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Issue issue)) return false;
        return Objects.equals(getIssueId(), issue.getIssueId()) && Objects.equals(getIssueDate(), issue.getIssueDate()) && Objects.equals(getReturnDate(), issue.getReturnDate()) && Objects.equals(getStudent(), issue.getStudent()) && Objects.equals(getBook(), issue.getBook());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIssueId(), getIssueDate(), getReturnDate(), getStudent(), getBook());
    }
}
