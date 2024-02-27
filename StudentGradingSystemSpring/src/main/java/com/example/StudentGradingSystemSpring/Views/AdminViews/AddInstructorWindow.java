package com.example.StudentGradingSystemSpring.Views.AdminViews;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddInstructorWindow {
    private static final String API_BASE_URL = "http://localhost:8080"; // Replace with your Spring Boot backend URL

    public void showAddInstructorDialog() {
        JFrame instructorFrame = new JFrame("Add Instructor");
        instructorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel instructorPanel = new JPanel();
        instructorPanel.setLayout(new GridLayout(3, 2));

        JTextField nameField = new JTextField();
        JButton addButton = new JButton("Add");

        instructorPanel.add(new JLabel("Name:"));
        instructorPanel.add(nameField);
        instructorPanel.add(new JLabel("")); // Empty space
        instructorPanel.add(addButton);

        instructorFrame.getContentPane().add(instructorPanel);
        instructorFrame.setSize(300, 150);
        instructorFrame.setLocationRelativeTo(null);
        instructorFrame.setVisible(true);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String instructorName = nameField.getText();

                String jsonPayload = "{\"instructorName\":\"" + instructorName + "\"}";

                try {
                    URL url = new URL(API_BASE_URL + "/instructor/addInstructor");
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
                        System.out.println("Instructor added successfully!");
                    } else {
                        System.out.println("Failed to add instructor. Response code: " + responseCode);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                instructorFrame.dispose();
            }
        });
    }
}
