/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui;

import com.ecofoodconnect.models.DonationRequest;
import com.ecofoodconnect.services.AuthService;
import com.ecofoodconnect.ui.wasteManagement.ActionButtonEditor;
import com.ecofoodconnect.ui.wasteManagement.ActionButtonRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author tanmay
 */
public class WasteManagementDashboard extends BaseDashboard {
    public WasteManagementDashboard(JFrame previousScreen) {
        super("Waste Management Operator Dashboard");
//        setPreviousScreen(previousScreen); // Optional if you need a back button

        JTabbedPane tabbedPane = new JTabbedPane();

        // Add tabs
        tabbedPane.addTab("Process Waste Requests", createWasteDisposalTab());
        tabbedPane.addTab("Environmental Impact Metrics", createEnvironmentalImpactTab());

        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createWasteDisposalTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel headerLabel = new JLabel("Process Waste Disposal Requests", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(139, 69, 19));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Food Type", "Quantity (lbs)", "Rejection Reason", "Status", "Actions"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(245, 245, 245) : Color.WHITE);
                } else {
                    c.setBackground(new Color(173, 216, 230));
                }
                return c;
            }
        };

        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(139, 69, 19));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(40);

        // Load all rejected donation requests as "Pending Disposal"
        for (DonationRequest request : AuthService.getDonationRequestDirectory().getDonationRequests()) {
            if ("Rejected".equalsIgnoreCase(request.getStatus())) {
                tableModel.addRow(new Object[]{
                        request.getId(),
                        request.getFoodType(),
                        request.getQuantity(),
                        request.getRejectionReason(),
                        "Pending Disposal", // Dynamically display as "Pending Disposal"
                        "Dispose"
                });
            }
        }

        // Add action buttons
        table.getColumnModel().getColumn(5).setCellRenderer(new ActionButtonRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new ActionButtonEditor(new JCheckBox(), tableModel, AuthService.getDonationRequestDirectory()));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
    
    private JPanel createEnvironmentalImpactTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel headerLabel = new JLabel("Environmental Impact Metrics", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(34, 139, 34));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        JPanel metricsPanel = new JPanel(new GridBagLayout());
        metricsPanel.setBackground(Color.WHITE);
        metricsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel wasteQuantityLabel = new JLabel("Total Waste Quantity (lbs):");
        JLabel recyclingRateLabel = new JLabel("Recycling Rate (%):");
        JLabel impactScoreLabel = new JLabel("Environmental Impact Score:");

        JTextField wasteQuantityField = new JTextField();
        JTextField recyclingRateField = new JTextField();
        JTextField impactScoreField = new JTextField();

        setFieldSize(wasteQuantityField);
        setFieldSize(recyclingRateField);
        setFieldSize(impactScoreField);

        wasteQuantityField.setEditable(false);
        recyclingRateField.setEditable(false);
        impactScoreField.setEditable(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        metricsPanel.add(wasteQuantityLabel, gbc);
        gbc.gridx = 1;
        metricsPanel.add(createFieldPanel(wasteQuantityField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        metricsPanel.add(recyclingRateLabel, gbc);
        gbc.gridx = 1;
        metricsPanel.add(createFieldPanel(recyclingRateField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        metricsPanel.add(impactScoreLabel, gbc);
        gbc.gridx = 1;
        metricsPanel.add(createFieldPanel(impactScoreField), gbc);

        JButton calculateButton = new JButton("Calculate Metrics");
        calculateButton.setBackground(new Color(50, 205, 50));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFont(new Font("Arial", Font.BOLD, 16));

        calculateButton.addActionListener(e -> {
            // Calculate metrics based on disposed requests
            double totalWaste = 0;
            double totalRecyclable = 0;

            for (DonationRequest request : AuthService.getDonationRequestDirectory().getDonationRequests()) {
                if ("Disposed".equalsIgnoreCase(request.getStatus())) {
                    totalWaste += request.getQuantity();
                    totalRecyclable += request.getQuantity() * getRecyclingPercentage(request.getFoodType());
                }
            }

            double recyclingRate = totalWaste > 0 ? (totalRecyclable / totalWaste) * 100 : 0;
            double impactScore = calculateImpactScore(totalWaste, recyclingRate);

            wasteQuantityField.setText(String.format("%.2f lbs", totalWaste));
            recyclingRateField.setText(String.format("%.2f %%", recyclingRate));
            impactScoreField.setText(String.format("%.2f", impactScore));
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(calculateButton);

        panel.add(metricsPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private double getRecyclingPercentage(String foodType) {
        return switch (foodType) {
            case "Canned" -> 0.9; // 90% recyclable
            case "Fresh Produce" -> 0.5; // 50% recyclable
            case "Frozen", "Beverages" -> 0.7; // 70% recyclable
            default -> 0.2; // Default 20% recyclable
        };
    }

    private double calculateImpactScore(double totalWaste, double recyclingRate) {
        if (totalWaste == 0) {
            return 100; // Perfect score for zero waste
        }
        return 100 - (recyclingRate / 2) - (totalWaste / 10); // Lower waste and higher recycling = better score
    }

    private void setFieldSize(JTextField field) {
        field.setPreferredSize(new Dimension(250, 30));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    private JPanel createFieldPanel(JTextField field) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.add(field, BorderLayout.CENTER);
        return wrapper;
    }
}