package com.example.StudentGradingSystemSpring.Views.InstructorViews;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class AssignCourseWindow {
    private JTextField instructorIdField;
    private JTextField courseIdField;

    public void showAssignCourseDialog() {
        JFrame assignCourseFrame = new JFrame("Assign Course");
        assignCourseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel assignCoursePanel = new JPanel();
        assignCoursePanel.setLayout(new GridLayout(3, 2));

        instructorIdField = new JTextField();
        courseIdField = new JTextField();
        JButton assignButton = new JButton("Assign");

        assignCoursePanel.add(new JLabel("Instructor ID:"));
        assignCoursePanel.add(instructorIdField);
        assignCoursePanel.add(new JLabel("Course ID:"));
        assignCoursePanel.add(courseIdField);
        assignCoursePanel.add(new JLabel(""));
        assignCoursePanel.add(assignButton);

        assignCourseFrame.getContentPane().add(assignCoursePanel);
        assignCourseFrame.setSize(300, 150);
        assignCourseFrame.setLocationRelativeTo(null);
        assignCourseFrame.setVisible(true);

        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Long instructorId = Long.parseLong(instructorIdField.getText());
                Long courseId = Long.parseLong(courseIdField.getText());

                RestTemplate restTemplate = new RestTemplate();

                String assignCourseUrl = "http://localhost:8080/instructor/assignCourseForInstructor";

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

                String urlParameters = String.format("?instructorId=%d&courseId=%d", instructorId, courseId);

                ResponseEntity<String> responseEntity = restTemplate.exchange(assignCourseUrl + urlParameters, HttpMethod.POST, requestEntity, String.class);

                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    JOptionPane.showMessageDialog(null, "Course assigned successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to assign course.");
                }

                assignCourseFrame.dispose();
            }
        });
    }
}
