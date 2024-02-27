package com.example.StudentGradingSystemSpring.Views;

import com.example.StudentGradingSystemSpring.Views.AdminViews.AdminOptionsWindow;
import com.example.StudentGradingSystemSpring.Views.InstructorViews.InstructorOptionsWindow;
import com.example.StudentGradingSystemSpring.Views.StudentViews.StudentOptionsWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowMainGUI();
        });
    }

    private static void createAndShowMainGUI() {
        JFrame frame = new JFrame("Main GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));

        JButton adminButton = new JButton("Admin");
        JButton instructorButton = new JButton("Instructor");
        JButton studentButton = new JButton("Student");

        mainPanel.add(adminButton);
        mainPanel.add(instructorButton);
        mainPanel.add(studentButton);

        frame.getContentPane().add(mainPanel);
        frame.setSize(300, 150);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminOptionsWindow adminOptionsWindow = new AdminOptionsWindow();
                adminOptionsWindow.showAdminOptionsWindow();
            }
        });

        instructorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InstructorOptionsWindow instructorOptionsWindow = new InstructorOptionsWindow();
                instructorOptionsWindow.showInstructorOptionsWindow();
            }
        });

        studentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentOptionsWindow studentOptionsWindow = new StudentOptionsWindow();
                studentOptionsWindow.showStudentOptionsWindow();
            }
        });
    }
}
