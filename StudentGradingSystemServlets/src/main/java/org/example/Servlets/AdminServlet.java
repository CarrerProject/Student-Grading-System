package org.example.Servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Models.Admin;
import org.example.Services.AdminService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/addAdmin")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Debugging: Log the entire request body
            BufferedReader reader = request.getReader();
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            System.out.println("Request Body: " + stringBuilder.toString());

            // Parse JSON using Jackson
            ObjectMapper mapper = new ObjectMapper();
            Admin admin = mapper.readValue(stringBuilder.toString(), Admin.class);

            // Add admin to repository
            AdminService adminService = new AdminService();
            Admin addedAdmin = adminService.addAdmin(admin);

            // Send response
            if (addedAdmin != null) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Admin " + addedAdmin.getAdminName() + " added successfully");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Failed to add admin");
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Internal Server Error");
        }
    }
}
