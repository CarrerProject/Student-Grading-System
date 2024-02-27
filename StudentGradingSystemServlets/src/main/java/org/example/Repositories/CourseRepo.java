package org.example.Repositories;

import org.example.ConnectionProvider;
import org.example.Models.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseRepo {
    private Connection connection = ConnectionProvider.getConnection();

    public CourseRepo() throws SQLException {
    }

    public Course save(Course course) throws SQLException {
        String sql = "INSERT INTO course (courseName) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, course.getCourseName());
            preparedStatement.executeUpdate();

            // Retrieve the auto-generated courseId
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    course.setCourseId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return course;
    }
}
