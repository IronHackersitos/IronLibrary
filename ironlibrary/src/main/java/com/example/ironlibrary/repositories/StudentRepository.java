package com.example.ironlibrary.repositories;

import com.example.ironlibrary.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.*;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

}
