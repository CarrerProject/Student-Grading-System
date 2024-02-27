package com.example.StudentGradingSystemSpring.Controllers;

import com.example.StudentGradingSystemSpring.Models.Admin;
import com.example.StudentGradingSystemSpring.Models.Instructor;
import com.example.StudentGradingSystemSpring.Models.Student;
import com.example.StudentGradingSystemSpring.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @PostMapping("/addAdmin")
    public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin){
        Admin addedAdmin = adminService.addAdmin(admin);
        if (addedAdmin != null)
            return new ResponseEntity<>(addedAdmin,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addInstructor")
    public ResponseEntity<Instructor> addInstructor(@RequestBody Instructor instructor){
        Instructor addedInstructor = adminService.addInstructor(instructor);
        if (addedInstructor != null)
            return new ResponseEntity<>(addedInstructor, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/addStudent")
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        Student addedStudent = adminService.addStudent(student);
        if (addedStudent != null)
            return new ResponseEntity<>(addedStudent, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
