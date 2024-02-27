package com.example.StudentGradingSystemSpring.Controllers;

import com.example.StudentGradingSystemSpring.Models.Grades;
import com.example.StudentGradingSystemSpring.Models.Student;
import com.example.StudentGradingSystemSpring.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping("/addStudent")
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        Student addedStudent = studentService.addStudent(student);
        if (addedStudent != null) {
            return new ResponseEntity<>(addedStudent, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getAllGrades")
    public ResponseEntity<List<Grades>> getAllGrades(@RequestParam Long stdId){
        List<Grades> grades = studentService.getAllGrades(stdId);
        if (grades != null)
            return new ResponseEntity<>(grades,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getAllGradesForStudent")
    public ResponseEntity<List<Map<String, Object>>> getAllGradesForStudent(@RequestParam Long stdId) {
        List<Map<String, Object>> courseGrades = studentService.getAllGradesForStudent(stdId);

        if (!courseGrades.isEmpty()) {
            return new ResponseEntity<>(courseGrades, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/calculateAverageGrade")
    public ResponseEntity<Double> calculateAverageGradeForStudent(@RequestParam Long stdId) {
        double averageGrade = studentService.calculateAverageGradeForStudent(stdId);

        return new ResponseEntity<>(averageGrade, HttpStatus.OK);
    }
}
