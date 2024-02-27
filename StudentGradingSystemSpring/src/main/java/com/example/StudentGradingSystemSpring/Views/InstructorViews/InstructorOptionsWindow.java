package com.example.StudentGradingSystemSpring.Views.InstructorViews;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstructorOptionsWindow {
    public void showInstructorOptionsWindow() {
        JFrame instructorFrame = new JFrame("Instructor Options");
        instructorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel instructorPanel = new JPanel();
        instructorPanel.setLayout(new GridLayout(2, 1));

        JButton assignCourseButton = new JButton("Assign Course");
        JButton assignGradeButton = new JButton("Assign Grade");

        instructorPanel.add(assignCourseButton);
        instructorPanel.add(assignGradeButton);

        instructorFrame.getContentPane().add(instructorPanel);
        instructorFrame.setSize(300, 150);
        instructorFrame.setLocationRelativeTo(null);
        instructorFrame.setVisible(true);

        assignCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AssignCourseWindow assignCourseWindow = new AssignCourseWindow();
                assignCourseWindow.showAssignCourseDialog();
            }
        });

        assignGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AssignGradeWindow assignGradeWindow = new AssignGradeWindow();
                assignGradeWindow.showAssignGradeDialog();
            }
        });
    }
}
