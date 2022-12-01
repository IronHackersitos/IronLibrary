package com.example.ironlibrary.models;


import jakarta.persistence.GenerationType;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name="Authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;
    private String name;
    private String email;
    @OneToMany(mappedBy="author")
    private List<Book> authorBook;
    public Author(){}

    public Author(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Book> getAuthorBook() {
        return authorBook;
    }

    public void setAuthorBook(List<Book> authorBook) {
        this.authorBook = authorBook;
    }

    @Override
    public String toString() {
        return  String.format("%-30s%-30s%\n", name, email);
    }

   /* @Override
    public String toString() {
        return "Author{" +
                "authorBook=" + authorBook +
                '}';
    }*/
}
