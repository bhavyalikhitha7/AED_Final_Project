/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui.logisticsCoordinator;

import com.ecofoodconnect.models.LogisticsRequest;
import com.ecofoodconnect.models.LogisticsRequestDirectory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author tanmay
 */
public class AssignDriversPanel {
    private LogisticsRequestDirectory logisticsRequestDirectory;

    public AssignDriversPanel(LogisticsRequestDirectory logisticsRequestDirectory) {
        this.logisticsRequestDirectory = logisticsRequestDirectory;
    }

    public JPanel createPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Header
        JLabel headerLabel = new JLabel("Assign Drivers and Schedule Pickups", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(34, 139, 34));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        // Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fields
        JLabel driverLabel = new JLabel("Driver:");
        JComboBox<String> driverDropdown = new JComboBox<>(new String[]{"Driver A", "Driver B", "Driver C"}); // Replace with dynamic data
        JLabel requestLabel = new JLabel("Donation Request ID:");
        JComboBox<String> requestDropdown = new JComboBox<>(getPendingLogisticsRequestIds()); // Fetch pending requests
        JLabel dateLabel = new JLabel("Pickup Date:");
        JTextField dateField = new JTextField("MM/DD/YYYY");
        JLabel timeLabel = new JLabel("Pickup Time:");
        JTextField timeField = new JTextField("HH:MM AM/PM");

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(driverLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(driverDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(requestLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(requestDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(dateLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(dateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(timeLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(timeField, gbc);

        panel.add(formPanel, BorderLayout.CENTER);

        // Assign Button
        JButton assignButton = new JButton("Assign Driver");
        assignButton.setBackground(new Color(50, 205, 50));
        assignButton.setForeground(Color.WHITE);
        assignButton.setFont(new Font("Arial", Font.BOLD, 14));
        assignButton.addActionListener(e -> {
            String driver = (String) driverDropdown.getSelectedItem();
            String requestId = (String) requestDropdown.getSelectedItem();
            String date = dateField.getText();
            String time = timeField.getText();

            if (driver.isEmpty() || requestId.isEmpty() || date.isEmpty() || time.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                logisticsRequestDirectory.addRequest(new LogisticsRequest(requestId, driver, date, time, "Scheduled"));
                JOptionPane.showMessageDialog(panel, "Driver Assigned Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Reset Fields
                driverDropdown.setSelectedIndex(0);
                requestDropdown.setSelectedIndex(0);
                dateField.setText("MM/DD/YYYY");
                timeField.setText("HH:MM AM/PM");
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(assignButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private String[] getPendingLogisticsRequestIds() {
        // Fetch requests with status "Scheduled" dynamically
        return logisticsRequestDirectory.getRequests().stream()
                .filter(req -> "Scheduled".equalsIgnoreCase(req.getStatus()))
                .map(LogisticsRequest::getRequestId)
                .toArray(String[]::new);
    }
}