package com.example.ironlibrary.models;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(mappedBy = "student")
    private List<Student> studentList;
    
    @OneToMany(mappedBy = "author")
    private List<Author> authorList;
    
    public Library(){}

    public Library(List<Student> studentList, List<Author> authorList) {
        this.studentList = studentList;
        this.authorList = authorList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }
}
