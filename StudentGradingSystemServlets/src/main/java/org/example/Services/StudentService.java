// StudentService
package org.example.Services;

import org.example.Models.Grade;
import org.example.Repositories.StudentRepo;

import java.sql.SQLException;
import java.util.List;

public class StudentService {
    private final StudentRepo studentRepo = new StudentRepo();

    public StudentService() throws SQLException {
    }

    public List<Grade> getStudentGrades(long studentId) throws SQLException {
        return studentRepo.getStudentGrades(studentId);
    }
}
