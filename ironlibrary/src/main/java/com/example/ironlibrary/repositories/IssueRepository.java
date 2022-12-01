package com.example.ironlibrary.repositories;

import com.example.ironlibrary.models.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue,Integer>{
    List<Issue> findByStudentUsn(String usn);
}
