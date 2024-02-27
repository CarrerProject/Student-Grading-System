package org.example.Services;

import org.example.Models.Admin;
import org.example.Models.Instructor;
import org.example.Models.Student;
import org.example.Repositories.AdminRepo;
import org.example.Repositories.InstructorRepo;
import org.example.Repositories.StudentRepo;

import java.sql.SQLException;

public class AdminService {
    private final AdminRepo adminRepo = new AdminRepo();
    private final StudentRepo studentRepo = new StudentRepo();
    private final InstructorRepo instructorRepo = new InstructorRepo();

    public AdminService() throws SQLException {
    }

    public Admin addAdmin(Admin admin) throws SQLException {
        // Assuming that the save method in AdminRepo returns the added admin
        return adminRepo.save(admin);
    }

    public Student addStudent(Student student) throws SQLException {
        return studentRepo.save(student);
    }

    public Instructor addInstructor(Instructor instructor) throws SQLException {
        return instructorRepo.save(instructor);
    }

}
