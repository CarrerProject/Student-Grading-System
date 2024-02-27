package com.example.StudentGradingSystemSpring.Services;
import com.example.StudentGradingSystemSpring.Models.Courses;
import com.example.StudentGradingSystemSpring.Repositries.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CourseService {

    private final CourseRepo courseRepo;
    @Autowired
    public CourseService(CourseRepo courseRepo){
        this.courseRepo = courseRepo;
    }

    public Courses addCourse(Courses course) {
        return courseRepo.save(course);
    }
}
