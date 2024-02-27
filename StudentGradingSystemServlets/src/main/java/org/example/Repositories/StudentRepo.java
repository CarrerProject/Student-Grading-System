package org.example.Repositories;

import org.example.ConnectionProvider;
import org.example.Models.Grade;
import org.example.Models.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentRepo {
    private Connection connection = ConnectionProvider.getConnection();

    public StudentRepo() throws SQLException {
    }

    public Student save(Student student) {
        String sql = "INSERT INTO student (studentName) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, student.getStudentName());
            preparedStatement.executeUpdate();

            // Retrieve the auto-generated stdId
            // Add similar logic as in AdminRepo if stdId is auto-generated
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
        return student;
    }

    public List<Grade> getStudentGrades(long studentId) throws SQLException {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT student_grades.courseId, student_grades.gradeValue, course.courseName " +
                "FROM student_grades " +
                "JOIN course ON student_grades.courseId = course.courseId " +
                "WHERE student_grades.studentId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, studentId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
//                    long courseId = resultSet.getLong("courseId");
                    int gradeValue = resultSet.getInt("gradeValue");
                    String courseName = resultSet.getString("courseName");
                    grades.add(new Grade(courseName, gradeValue));
                }
            }
        }
        return grades;
    }
}
