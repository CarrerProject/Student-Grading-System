package com.example.StudentGradingSystemSpring.Views.AdminViews;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddStudentWindow {
    private static final String API_BASE_URL = "http://localhost:8080";

    public void showAddStudentDialog() {
        JFrame studentFrame = new JFrame("Add Student");
        studentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel studentPanel = new JPanel();
        studentPanel.setLayout(new GridLayout(3, 2));

        JTextField nameField = new JTextField();
        JButton addButton = new JButton("Add");

        studentPanel.add(new JLabel("Name:"));
        studentPanel.add(nameField);
        studentPanel.add(new JLabel(""));
        studentPanel.add(addButton);

        studentFrame.getContentPane().add(studentPanel);
        studentFrame.setSize(300, 150);
        studentFrame.setLocationRelativeTo(null);
        studentFrame.setVisible(true);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentName = nameField.getText();

                String jsonPayload = "{\"stdName\":\"" + studentName + "\"}";

                try {
                    URL url = new URL(API_BASE_URL + "/student/addStudent");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);

                    try (OutputStream os = connection.getOutputStream()) {
                        byte[] input = jsonPayload.getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    int responseCode = connection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        System.out.println("Student added successfully!");
                    } else {
                        System.out.println("Failed to add student. Response code: " + responseCode);
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                studentFrame.dispose();
            }
        });
    }
}
