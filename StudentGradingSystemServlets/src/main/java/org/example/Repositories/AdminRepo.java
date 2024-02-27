package org.example.Repositories;

import org.example.ConnectionProvider;
import org.example.Models.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRepo {
    private Connection connection = ConnectionProvider.getConnection();

    public AdminRepo() throws SQLException {
    }

    public Admin save(Admin admin) throws SQLException {
        String sql = "INSERT INTO admin (adminName) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, admin.getAdminName());
            preparedStatement.executeUpdate();

            // Retrieve the auto-generated adminId
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    admin.setAdminId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return admin;
    }
}
