package org.example.Services;

import org.example.Models.Grade;
import org.example.Repositories.GradeRepo;

import java.sql.SQLException;

public class GradeService {
    private final GradeRepo gradeRepo = new GradeRepo();

    public GradeService() throws SQLException {
    }

//    public boolean addGrade(Grade grade) throws SQLException {
//        return gradeRepo.addGrade(grade);
//    }
}