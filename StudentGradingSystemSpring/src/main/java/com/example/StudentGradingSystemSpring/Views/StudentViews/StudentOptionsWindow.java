package com.example.StudentGradingSystemSpring.Views.StudentViews;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentOptionsWindow {
    public void showStudentOptionsWindow() {
        JFrame studentOptionsFrame = new JFrame("Student Options");
        studentOptionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel studentOptionsPanel = new JPanel();
        studentOptionsPanel.setLayout(new GridLayout(2, 1));

        JButton viewGradesButton = new JButton("View Grades");
        JButton calculateAverageButton = new JButton("Calculate Average");

        studentOptionsPanel.add(viewGradesButton);
        studentOptionsPanel.add(calculateAverageButton);

        studentOptionsFrame.getContentPane().add(studentOptionsPanel);
        studentOptionsFrame.setSize(300, 150);
        studentOptionsFrame.setLocationRelativeTo(null);
        studentOptionsFrame.setVisible(true);

        viewGradesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewGradesWindow viewGradesWindow = new ViewGradesWindow();
                viewGradesWindow.showViewGradesDialog();
            }
        });

        calculateAverageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CalculateAverageGradeWindow calculateAverageGradeWindow = new CalculateAverageGradeWindow();
                calculateAverageGradeWindow.showCalculateAverageGradeDialog();
            }
        });
    }
}
