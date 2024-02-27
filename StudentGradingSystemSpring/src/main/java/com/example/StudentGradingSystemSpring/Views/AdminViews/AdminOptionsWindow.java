package com.example.StudentGradingSystemSpring.Views.AdminViews;

import com.example.StudentGradingSystemSpring.Views.AdminViews.AddInstructorWindow;
import com.example.StudentGradingSystemSpring.Views.AdminViews.AddStudentWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminOptionsWindow {
    public void showAdminOptionsWindow() {
        JFrame adminFrame = new JFrame("Admin Options");
        adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new GridLayout(2, 1));

        JButton addStudentButton = new JButton("Add Student");
        JButton addInstructorButton = new JButton("Add Instructor");

        adminPanel.add(addStudentButton);
        adminPanel.add(addInstructorButton);

        adminFrame.getContentPane().add(adminPanel);
        adminFrame.setSize(300, 150);
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setVisible(true);

        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddStudentWindow addStudentWindow = new AddStudentWindow();
                addStudentWindow.showAddStudentDialog();
            }
        });

        addInstructorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddInstructorWindow addInstructorWindow = new AddInstructorWindow();
                addInstructorWindow.showAddInstructorDialog();
            }
        });
    }
}
