package com.example.StudentGradingSystemSpring.Controllers;

import com.example.StudentGradingSystemSpring.Models.Courses;
import com.example.StudentGradingSystemSpring.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @PostMapping("/addCourse")
    public ResponseEntity<Courses> addCourse(@RequestBody Courses course){
        Courses addCourse = courseService.addCourse(course);
        if (addCourse != null)
            return new ResponseEntity<>(addCourse, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
