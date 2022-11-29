package com.example.ironlibrary.models;

import jakarta.persistence.*;

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
}


