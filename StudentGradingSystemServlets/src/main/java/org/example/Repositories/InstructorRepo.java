package org.example.Repositories;

import org.example.ConnectionProvider;
import org.example.Models.Grade;
import org.example.Models.Instructor;
import org.example.Services.GradeService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InstructorRepo {
    private Connection connection = ConnectionProvider.getConnection();
    private GradeService gradeService = new GradeService();
    private GradeRepo gradeRepo = new GradeRepo();

    public InstructorRepo() throws SQLException {
    }

    public Instructor save(Instructor instructor) throws SQLException {
        String sql = "INSERT INTO instructor (instructorName) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, instructor.getInstructorName());
            preparedStatement.executeUpdate();

            // Retrieve the auto-generated instructorId
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    instructor.setInstructorId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return instructor;
    }

    public boolean assignCourse(long instructorId, long courseId) throws SQLException {
        String sql = "INSERT INTO instructor_courses (instructorId, courseId) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, instructorId);
            preparedStatement.setLong(2, courseId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean assignGradeToStudent(long instructorId, long studentId, long courseId, int gradeValue) throws SQLException {
        // Check if a record already exists
        if (gradeExists(instructorId, studentId, courseId)) {
            return false;  // Record already exists, return false
        }
            Grade grade = new Grade();
            grade.setGradeValue(gradeValue);

            gradeRepo.addGrade(grade);
        // If the record does not exist, insert a new grade
        String sql = "INSERT INTO student_grades (instructorId, studentId, courseId, gradeValue, gradeId) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, instructorId);
            preparedStatement.setLong(2, studentId);
            preparedStatement.setLong(3, courseId);
            preparedStatement.setInt(4, gradeValue);
            preparedStatement.setLong(5,grade.getGradeId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Helper method to check if a grade already exists for the given instructor, student, and course
    private boolean gradeExists(long instructorId, long studentId, long courseId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM student_grades WHERE instructorId = ? AND studentId = ? AND courseId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, instructorId);
            preparedStatement.setLong(2, studentId);
            preparedStatement.setLong(3, courseId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;  // Return true if the count is greater than 0 (record exists)
                }
            }
        }
        return false;  // Default to false in case of an error
    }
}
