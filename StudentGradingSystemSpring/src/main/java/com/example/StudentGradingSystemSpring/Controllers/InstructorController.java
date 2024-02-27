package com.example.StudentGradingSystemSpring.Controllers;

import com.example.StudentGradingSystemSpring.Models.Courses;
import com.example.StudentGradingSystemSpring.Models.Grades;
import com.example.StudentGradingSystemSpring.Models.Instructor;
import com.example.StudentGradingSystemSpring.Services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    private final InstructorService instructorService;
    @Autowired
    public InstructorController(InstructorService instructorService){
        this.instructorService = instructorService;
    }

    @PostMapping("/addInstructor")
    public ResponseEntity<Instructor> addInstructor(@RequestBody Instructor instructor){
        Instructor addedInstructor = instructorService.addInstructor(instructor);
        if (addedInstructor != null)
            return new ResponseEntity<>(addedInstructor, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/assignCourseForInstructor")
    public ResponseEntity<Instructor> assignCourseForInstructor(
            @RequestParam Long instructorId, @RequestParam Long courseId){
        Instructor instructor = instructorService.assignCourseForInstructor(instructorId,courseId);
        if (instructor != null)
            return new ResponseEntity<>(instructor, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/assignStudentGrade")
    public ResponseEntity<Grades> assignStudentGrade(
            @RequestParam Long instructorId,
            @RequestParam Long stdId,
            @RequestParam Long courseId,
            @RequestParam int gradeValue) {

        try {
            Grades addedGrade = instructorService.assignStudentGrade(instructorId, stdId, courseId, gradeValue);

            if (addedGrade != null) {
                return new ResponseEntity<>(addedGrade, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllCoursesForSpecificInstructor")
    public ResponseEntity<List<Courses>> getAllCoursesForSpecificInstructor(@RequestParam Long instructorId){
        List<Courses> courses = instructorService.getAllCoursesForSpecificInstructor(instructorId);

        if (courses != null)
            return new ResponseEntity<>(courses, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
