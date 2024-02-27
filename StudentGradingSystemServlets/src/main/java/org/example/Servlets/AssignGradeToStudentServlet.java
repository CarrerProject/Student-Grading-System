package org.example.Servlets;

import org.example.Models.Grade;
import org.example.Services.InstructorService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/instructor/assignGrade")
public class AssignGradeToStudentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long instructorId = Long.parseLong(request.getParameter("instructorId"));
            long studentId = Long.parseLong(request.getParameter("studentId"));
            long courseId = Long.parseLong(request.getParameter("courseId"));
            int gradeValue = Integer.parseInt(request.getParameter("gradeValue"));

            InstructorService instructorService = new InstructorService();
            boolean success = instructorService.assignGradeToStudent(instructorId, studentId, courseId, gradeValue);

            // Send response
            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Grade assigned successfully");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Failed to assign grade");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Internal Server Error");
        }
    }
}
