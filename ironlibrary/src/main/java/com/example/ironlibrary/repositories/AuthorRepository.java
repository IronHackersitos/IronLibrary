package com.example.ironlibrary.repositories;

import com.example.ironlibrary.models.Author;
import com.example.ironlibrary.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);  
}
