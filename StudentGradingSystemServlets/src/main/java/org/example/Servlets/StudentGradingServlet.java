// StudentServlet
package org.example.Servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
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

@WebServlet("/student/getGrades")
public class StudentGradingServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long studentId = Long.parseLong(request.getParameter("studentId"));

            StudentService studentService = new StudentService();
            List<Grade> grades = studentService.getStudentGrades(studentId);

            // Convert grades to JSON and send the response
            ObjectMapper mapper = new ObjectMapper();
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(mapper.writeValueAsString(grades));
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Internal Server Error");
        }
    }
}
