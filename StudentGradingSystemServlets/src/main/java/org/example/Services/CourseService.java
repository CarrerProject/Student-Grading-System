package org.example.Services;

import org.example.Models.Course;
import org.example.Repositories.CourseRepo;

import java.sql.SQLException;

public class CourseService {
    private final CourseRepo courseRepo = new CourseRepo();

    public CourseService() throws SQLException {
    }

    public Course addCourse(Course course) throws SQLException {
        return courseRepo.save(course);
    }
}
