package com.example.StudentGradingSystemSpring.Repositries;

import com.example.StudentGradingSystemSpring.Models.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo extends JpaRepository<Courses, Long> {
}
