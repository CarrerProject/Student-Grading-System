package com.example.StudentGradingSystemSpring.Services;

import com.example.StudentGradingSystemSpring.Models.Grades;
import com.example.StudentGradingSystemSpring.Models.Student;
import com.example.StudentGradingSystemSpring.Repositries.GradeRepo;
import com.example.StudentGradingSystemSpring.Repositries.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepo studentRepo;
    private final GradeRepo gradeRepo;

    @Autowired
    public StudentService(StudentRepo studentRepo, GradeRepo gradeRepo){
        this.studentRepo = studentRepo;
        this.gradeRepo = gradeRepo;
    }

    public Student addStudent(Student student) {
        return studentRepo.save(student);
    }

    public List<Grades> getAllGrades(Long stdId) {
        Student student = studentRepo.findById(stdId).orElse(null);

        if (student != null)
            return gradeRepo.findByStudent(student);
        return Collections.emptyList();
    }

    public List<Map<String, Object>> getAllGradesForStudent(Long studentId) {
        Student student = studentRepo.findById(studentId).orElse(null);

        if (student != null) {
            List<Grades> grades = gradeRepo.findByStudent(student);
            Set<String> uniqueCourseNames = new HashSet<>();

            return grades.stream()
                    .filter(grade -> uniqueCourseNames.add(grade.getCourse().getCourseName())) // Filter to get unique courses
                    .map(grade -> {
                        Map<String, Object> courseGradeMap = new HashMap<>();
                        courseGradeMap.put("grade", grade.getGrade());
                        courseGradeMap.put("courseName", grade.getCourse().getCourseName());
                        return courseGradeMap;
                    })
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public double calculateAverageGradeForStudent(Long stdId) {
        Student student = studentRepo.findById(stdId).orElse(null);

        if (student != null) {
            List<Grades> grades = gradeRepo.findByStudent(student);

            if (!grades.isEmpty()) {
                int totalGrades = grades.stream().mapToInt(Grades::getGrade).sum();
                return (double) totalGrades / grades.size();
            }
        }
        return 0.0;
    }
}

