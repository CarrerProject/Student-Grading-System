package org.example.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grade {
    private long gradeId;
    private long courseId;
    private String courseName;
    private int gradeValue;

    public Grade(long courseId, String courseName, int gradeValue) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.gradeValue = gradeValue;
    }
    public Grade(long courseId, int gradeValue) {
        this.courseId = courseId;
        this.gradeValue = gradeValue;
    }

    public Grade(String courseName, int gradeValue) {
        this.courseName = courseName;
        this.gradeValue = gradeValue;
    }
}