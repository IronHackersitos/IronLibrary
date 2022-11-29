package com.example.ironlibrary.models;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Student {
    @Id
    private String usn;
    private String name;

    @OneToOne (mappedBy = "issue")
    private List<Issue> issue;


    public Student() {

    }


    public Student(String usn, String name) {
        this.usn = usn;
        this.name = name;
    }

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


