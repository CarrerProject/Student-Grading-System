package com.example.StudentGradingSystemSpring.Repositries;

import com.example.StudentGradingSystemSpring.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
}
