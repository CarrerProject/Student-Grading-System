package org.example.Services;

import org.example.Repositories.InstructorRepo;

import java.sql.SQLException;

public class InstructorService {
    private final InstructorRepo instructorRepo = new InstructorRepo();

    public InstructorService() throws SQLException {
    }

    public boolean assignCourse(long instructorId, long courseId) throws SQLException {
        return instructorRepo.assignCourse(instructorId, courseId);
    }

    public boolean assignGradeToStudent(long instructorId, long studentId, long courseId, int grade) throws SQLException {
        return instructorRepo.assignGradeToStudent(instructorId, studentId, courseId, grade);
    }
}
