package com.example.StudentGradingSystemSpring.Views.InstructorViews;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class AssignGradeWindow {
    private JTextField instructorIdField;
    private JTextField stdIdField;
    private JTextField courseIdField;
    private JTextField gradeValueField;

    public void showAssignGradeDialog() {
        JFrame assignGradeFrame = new JFrame("Assign Grade");
        assignGradeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel assignGradePanel = new JPanel();
        assignGradePanel.setLayout(new GridLayout(5, 2));

        instructorIdField = new JTextField();
        stdIdField = new JTextField();
        courseIdField = new JTextField();
        gradeValueField = new JTextField();
        JButton assignButton = new JButton("Assign");

        assignGradePanel.add(new JLabel("Instructor ID:"));
        assignGradePanel.add(instructorIdField);
        assignGradePanel.add(new JLabel("Student ID:"));
        assignGradePanel.add(stdIdField);
        assignGradePanel.add(new JLabel("Course ID:"));
        assignGradePanel.add(courseIdField);
        assignGradePanel.add(new JLabel("Grade Value:"));
        assignGradePanel.add(gradeValueField);
        assignGradePanel.add(new JLabel("")); // Empty space
        assignGradePanel.add(assignButton);

        assignGradeFrame.getContentPane().add(assignGradePanel);
        assignGradeFrame.setSize(300, 200);
        assignGradeFrame.setLocationRelativeTo(null);
        assignGradeFrame.setVisible(true);

        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Long instructorId = Long.parseLong(instructorIdField.getText());
                Long stdId = Long.parseLong(stdIdField.getText());
                Long courseId = Long.parseLong(courseIdField.getText());
                int gradeValue = Integer.parseInt(gradeValueField.getText());

                RestTemplate restTemplate = new RestTemplate();

                String assignGradeUrl = "http://localhost:8080/instructor/assignStudentGrade";

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

                String urlParameters = String.format("?instructorId=%d&stdId=%d&courseId=%d&gradeValue=%d",
                        instructorId, stdId, courseId, gradeValue);

                ResponseEntity<String> responseEntity = restTemplate.exchange(assignGradeUrl + urlParameters, HttpMethod.POST, requestEntity, String.class);

                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    JOptionPane.showMessageDialog(null, "Grade assigned successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to assign grade.");
                }
                assignGradeFrame.dispose();
            }
        });
    }
}
