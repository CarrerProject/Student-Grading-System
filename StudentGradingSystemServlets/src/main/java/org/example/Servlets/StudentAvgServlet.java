package org.example.Servlets;

import org.example.Models.Grade;
import org.example.Services.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/student/calculateAvg")
public class StudentAvgServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Extract parameters from request
            long studentId = Long.parseLong(request.getParameter("studentId"));

            // Call your service or repository method to get student grades
            StudentService studentService = new StudentService();
            List<Grade> grades = studentService.getStudentGrades(studentId);

            // Calculate average
            double avg = calculateAverage(grades);

            // Send response
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Average Grade: " + avg);
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Internal Server Error");
        }
    }

    private double calculateAverage(List<Grade> grades) {
        if (grades.isEmpty()) {
            return 0.0;
        }

        int sum = 0;
        for (Grade grade : grades) {
            sum += grade.getGradeValue();
        }

        return (double) sum / grades.size();
    }
}
