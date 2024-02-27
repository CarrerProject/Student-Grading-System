package com.example.StudentGradingSystemSpring.Repositries;

import com.example.StudentGradingSystemSpring.Models.Grades;
import com.example.StudentGradingSystemSpring.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepo extends JpaRepository<Grades, Long> {
    List<Grades> findByStudent(Student student);
}
