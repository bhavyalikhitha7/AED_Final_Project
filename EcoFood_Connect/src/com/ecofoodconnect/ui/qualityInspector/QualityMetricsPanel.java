/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui.qualityInspector;

import com.ecofoodconnect.models.DonationRequest;
import com.ecofoodconnect.models.DonationRequestDirectory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author tanmay
 */
public class QualityMetricsPanel {
    private DonationRequestDirectory donationRequestDirectory;

    public QualityMetricsPanel(DonationRequestDirectory donationRequestDirectory) {
        this.donationRequestDirectory = donationRequestDirectory;
    }

    public JPanel createPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Header
        JLabel headerLabel = new JLabel("Update Quality Metrics", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(34, 139, 34));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);

        // Table for Pending Requests
        String[] columnNames = {"ID", "Food Type", "Quantity (lbs)", "Expiry Date", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(34, 139, 34));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(40);

        // Populate table
        for (DonationRequest request : donationRequestDirectory.getDonationRequests()) {
            if ("Pending".equalsIgnoreCase(request.getStatus())) {
                tableModel.addRow(new Object[]{
                        request.getId(),
                        request.getFoodType(),
                        request.getQuantity(),
                        request.getExpiryDate(),
                        request.getStatus()
                });
            }
        }

//        JScrollPane scrollPane = new JScrollPane(table);
//        contentPanel.add(scrollPane, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(table);
scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, 270)); // Set desired height
contentPanel.add(scrollPane, BorderLayout.NORTH);


        // Metrics Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Quality Metrics"));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JLabel freshnessLabel = new JLabel("Freshness (1-10):");
        JTextField freshnessField = new JTextField("8");
        setFieldSize(freshnessField);

        JLabel temperatureLabel = new JLabel("Temperature (°F):");
        JTextField temperatureField = new JTextField("35");
        setFieldSize(temperatureField);

        JLabel packagingLabel = new JLabel("Packaging Quality (1-10):");
        JTextField packagingField = new JTextField("9");
        setFieldSize(packagingField);

        JLabel odorLabel = new JLabel("Odor (1-10):");
        JTextField odorField = new JTextField("8");
        setFieldSize(odorField);

        JLabel labelingLabel = new JLabel("Labeling Accuracy (1-10):");
        JTextField labelingField = new JTextField("9");
        setFieldSize(labelingField);

        // Add fields to the form
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(freshnessLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(createFieldPanel(freshnessField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(temperatureLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(createFieldPanel(temperatureField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(packagingLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(createFieldPanel(packagingField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(odorLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(createFieldPanel(odorField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(labelingLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(createFieldPanel(labelingField), gbc);

        contentPanel.add(formPanel, BorderLayout.CENTER);

        JButton saveButton = new JButton("Update Metrics");
        saveButton.setBackground(new Color(50, 205, 50));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 16));
        saveButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(panel, "Please select a request to update metrics.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int freshness = Integer.parseInt(freshnessField.getText());
                double temperature = Double.parseDouble(temperatureField.getText());
                int packaging = Integer.parseInt(packagingField.getText());
                int odor = Integer.parseInt(odorField.getText());
                int labeling = Integer.parseInt(labelingField.getText());

                if (freshness < 7 || packaging < 8 || odor < 7 || labeling < 8 || temperature < 32 || temperature > 40) {
                    JOptionPane.showMessageDialog(panel, "One or more metrics are below required thresholds.", "Validation Failed", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String requestId = (String) tableModel.getValueAt(selectedRow, 0);
                Optional<DonationRequest> request = donationRequestDirectory.getDonationRequests().stream()
                        .filter(r -> r.getId().equals(requestId))
                        .findFirst();

                request.ifPresent(r -> {
                    r.setFreshness(freshness);
                    r.setTemperature(temperature);
                    r.setPackagingQuality(packaging);
                    r.setOdor(odor);
                    r.setLabelingAccuracy(labeling);
                    r.setStatus("Metrics Updated");
                });

                JOptionPane.showMessageDialog(panel, "Quality Metrics Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Please enter valid numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(saveButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private void setFieldSize(JTextField field) {
        field.setPreferredSize(new Dimension(250, 30)); // Fix width
        field.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    private JPanel createFieldPanel(JTextField field) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.add(field, BorderLayout.CENTER);
        return wrapper;
    }
}