package com.example.StudentGradingSystemSpring.Repositries;

import com.example.StudentGradingSystemSpring.Models.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepo extends JpaRepository<Instructor, Long> {
}
