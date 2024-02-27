package com.example.StudentGradingSystemSpring.Services;

import com.example.StudentGradingSystemSpring.Models.Courses;
import com.example.StudentGradingSystemSpring.Models.Grades;
import com.example.StudentGradingSystemSpring.Models.Instructor;
import com.example.StudentGradingSystemSpring.Models.Student;
import com.example.StudentGradingSystemSpring.Repositries.CourseRepo;
import com.example.StudentGradingSystemSpring.Repositries.GradeRepo;
import com.example.StudentGradingSystemSpring.Repositries.InstructorRepo;
import com.example.StudentGradingSystemSpring.Repositries.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {

    private final InstructorRepo instructorRepo;
    private final CourseRepo courseRepo;
    private final StudentRepo studentRepo;
    private final GradeRepo gradeRepo;


    @Autowired
    public InstructorService(InstructorRepo instructorRepo, CourseRepo courseRepo,
                             StudentRepo studentRepo, GradeRepo gradeRepo){
        this.instructorRepo = instructorRepo;
        this.courseRepo = courseRepo;
        this.studentRepo = studentRepo;
        this.gradeRepo = gradeRepo;
    }

    public Instructor addInstructor(Instructor instructor) {
        return instructorRepo.save(instructor);
    }

    public Instructor assignCourseForInstructor(Long instructorId, Long courseId) {
        Instructor instructor = instructorRepo.findById(instructorId).orElse(null);
        Courses course = courseRepo.findById(courseId).orElse(null);

        instructor.getCourses().add(course);
        return instructorRepo.save(instructor);
    }

    public List<Courses> getAllCoursesForSpecificInstructor(Long instructorId) {
        Instructor instructor = instructorRepo.findById(instructorId).orElse(null);

        if (instructor != null)
            return instructor.getCourses();
        return null;
    }


    public Grades assignStudentGrade(Long instructorId, Long stdId, Long courseId, int gradeValue) {
        Instructor instructor = instructorRepo.findById(instructorId).orElse(null);
        Student student = studentRepo.findById(stdId).orElse(null);
        Courses course = courseRepo.findById(courseId).orElse(null);

        if (instructor != null && student != null && course != null) {
            Grades grade = new Grades();
            grade.setGrade(gradeValue);
            grade.setInstructor(instructor);
            grade.setStudent(student);
            grade.setCourse(course);

            return gradeRepo.save(grade);
    }
        return null;
    }
}
