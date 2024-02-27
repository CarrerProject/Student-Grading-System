package com.example.StudentGradingSystemSpring.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "grades")
public class Grades {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long gradeId;

    private int gradeValue;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Courses course;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    public void setGrade(int gradeValue) {
        this.gradeValue = gradeValue;
    }

    public int getGrade() {
        return gradeValue;
    }
}
