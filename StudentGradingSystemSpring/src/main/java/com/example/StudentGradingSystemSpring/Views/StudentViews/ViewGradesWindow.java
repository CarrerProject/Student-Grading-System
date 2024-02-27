package com.example.StudentGradingSystemSpring.Views.StudentViews;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class ViewGradesWindow {
    private JTextField stdIdField;

    public void showViewGradesDialog() {
        JFrame viewGradesFrame = new JFrame("View Grades");
        viewGradesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel viewGradesPanel = new JPanel();
        viewGradesPanel.setLayout(new GridLayout(2, 2));

        stdIdField = new JTextField();
        JButton viewGradesButton = new JButton("View Grades");

        viewGradesPanel.add(new JLabel("Student ID:"));
        viewGradesPanel.add(stdIdField);
        viewGradesPanel.add(new JLabel(""));
        viewGradesPanel.add(viewGradesButton);

        viewGradesFrame.getContentPane().add(viewGradesPanel);
        viewGradesFrame.setSize(300, 150);
        viewGradesFrame.setLocationRelativeTo(null);
        viewGradesFrame.setVisible(true);

        viewGradesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Long stdId = Long.parseLong(stdIdField.getText());
                RestTemplate restTemplate = new RestTemplate();

                String viewGradesUrl = "http://localhost:8080/student/getAllGradesForStudent";
                String urlParameters = String.format("?stdId=%d", stdId);
                ResponseEntity<String> responseEntity = restTemplate.exchange(viewGradesUrl + urlParameters, HttpMethod.GET, null, String.class);

                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    JOptionPane.showMessageDialog(null, responseEntity.getBody(), "Grades for Student ID: " + stdId, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to retrieve grades.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                viewGradesFrame.dispose();
            }
        });
    }
}
