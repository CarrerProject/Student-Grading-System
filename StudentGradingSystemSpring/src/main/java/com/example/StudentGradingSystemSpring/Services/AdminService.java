package com.example.StudentGradingSystemSpring.Services;

import com.example.StudentGradingSystemSpring.Models.Admin;
import com.example.StudentGradingSystemSpring.Models.Instructor;
import com.example.StudentGradingSystemSpring.Models.Student;
import com.example.StudentGradingSystemSpring.Repositries.AdminRepo;
import com.example.StudentGradingSystemSpring.Repositries.InstructorRepo;
import com.example.StudentGradingSystemSpring.Repositries.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final InstructorRepo instructorRepo;
    private final AdminRepo adminRepo;
    private final StudentRepo studentRepo;

    @Autowired
    public AdminService(InstructorRepo instructorRepo, AdminRepo adminRepo, StudentRepo studentRepo) {
        this.instructorRepo = instructorRepo;
        this.adminRepo = adminRepo;
        this.studentRepo = studentRepo;
    }
    public Instructor addInstructor(Instructor instructor) {
        return instructorRepo.save(instructor);
    }

    public Admin addAdmin(Admin admin) {
        return adminRepo.save(admin);
    }

    public Student addStudent(Student student) {
        return studentRepo.save(student);
    }
}
