package com.example.ironlibrary.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
// @Table(name = "books")
public class Book {
    @Id
    private String isbn;

    private String title;
    private String category;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name="author_id")
    private Author author;

    @OneToOne(mappedBy="book")
    private Issue issue;

    public Book(){}

    public Book(String isbn, String title, String category, int quantity) {
        this.isbn = isbn;
        this.title = title;
        this.category = category;
        this.quantity = quantity;
    }

    public Book(String isbn, String title, String category, Integer quantity, Author author) {
        this.isbn = isbn;
        this.title = title;
        this.category = category;
        this.quantity = quantity;
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    @Override
    public String toString() {
        return String.format("%-20s%-20s%-20s%-20d", isbn, title, category, quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return Objects.equals(getIsbn(), book.getIsbn()) && Objects.equals(getTitle(), book.getTitle()) && Objects.equals(getCategory(), book.getCategory()) && Objects.equals(getQuantity(), book.getQuantity()) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIsbn(), getTitle(), getCategory(), getQuantity(), getAuthor(), getIssue());
    }
}


