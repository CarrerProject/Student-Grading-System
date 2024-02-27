package org.example.Servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Models.Course;
import org.example.Services.InstructorService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/instructor/assignCourse")
public class InstructorCourses extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Extract parameters from request
            String courseIdParam = request.getParameter("courseId");
            String instructorIdParam = request.getParameter("instructorId");

            // Validate parameters
            if (courseIdParam == null || instructorIdParam == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Missing required parameters");
                return;
            }

            // Parse parameters
            long courseId = Long.parseLong(courseIdParam);
            long instructorId = Long.parseLong(instructorIdParam);

            InstructorService instructorService = new InstructorService();
            boolean success = instructorService.assignCourse(instructorId, courseId);

            // Send response
            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Course assigned successfully");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Failed to assign course");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Internal Server Error");
        }
    }

}
