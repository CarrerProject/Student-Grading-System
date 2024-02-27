package org.example.Repositories;

import org.example.ConnectionProvider;
import org.example.Models.Grade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GradeRepo {
    private Connection connection = ConnectionProvider.getConnection();

    public GradeRepo() throws SQLException {
    }

    public long addGrade(Grade grade) throws SQLException {
        String sql = "INSERT INTO grades (gradeValue) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, grade.getGradeValue());
            preparedStatement.executeUpdate();

            // Retrieve the auto-generated gradeId
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long gradeId = generatedKeys.getLong(1);
                    grade.setGradeId(gradeId);
                    return gradeId;
                }
            }
        }
        return -1;  // Return -1 in case of an error
    }

    public Grade getGradeByValue(int gradeValue) {
        String sql = "SELECT * FROM grades WHERE gradeValue = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, gradeValue);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    long gradeId = resultSet.getLong("gradeId");
                    return new Grade(gradeId, gradeValue);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
