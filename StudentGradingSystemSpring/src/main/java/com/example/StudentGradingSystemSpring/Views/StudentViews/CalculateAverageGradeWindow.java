package com.example.StudentGradingSystemSpring.Views.StudentViews;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class CalculateAverageGradeWindow {
    private JTextField stdIdField;

    public void showCalculateAverageGradeDialog() {
        JFrame calculateAverageFrame = new JFrame("Calculate Average Grade");
        calculateAverageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel calculateAveragePanel = new JPanel();
        calculateAveragePanel.setLayout(new GridLayout(2, 2));

        stdIdField = new JTextField();
        JButton calculateAverageButton = new JButton("Calculate Average");

        calculateAveragePanel.add(new JLabel("Student ID:"));
        calculateAveragePanel.add(stdIdField);
        calculateAveragePanel.add(new JLabel("")); // Empty space
        calculateAveragePanel.add(calculateAverageButton);

        calculateAverageFrame.getContentPane().add(calculateAveragePanel);
        calculateAverageFrame.setSize(300, 150);
        calculateAverageFrame.setLocationRelativeTo(null);
        calculateAverageFrame.setVisible(true);

        calculateAverageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Long stdId = Long.parseLong(stdIdField.getText());

                RestTemplate restTemplate = new RestTemplate();

                String calculateAverageUrl = "http://localhost:8080/student/calculateAverageGrade";

                String urlParameters = String.format("?stdId=%d", stdId);

                ResponseEntity<Double> responseEntity = restTemplate.exchange(calculateAverageUrl + urlParameters, HttpMethod.GET, null, Double.class);

                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    JOptionPane.showMessageDialog(null, "Average Grade: " + responseEntity.getBody(), "Average Grade", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to calculate average grade.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                calculateAverageFrame.dispose();
            }
        });
    }
}
