/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui.logisticsCoordinator;

import com.ecofoodconnect.models.LogisticsRequest;
import com.ecofoodconnect.models.LogisticsRequestDirectory;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author tanmay
 */
public class ActionButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5)); // Arrange buttons horizontally
    private JButton startTransitButton = new JButton("Start Transit");
    private JButton completeButton = new JButton("Mark as Completed");
    private JLabel viewDetailsLabel = new JLabel("<html><u>View Details</u></html>"); // Styled as a link
    private JTable table;
    private DefaultTableModel tableModel;
    private LogisticsRequestDirectory logisticsRequestDirectory;

    public ActionButtonEditor(JCheckBox checkBox, DefaultTableModel tableModel, LogisticsRequestDirectory logisticsRequestDirectory) {
        this.tableModel = tableModel;
        this.logisticsRequestDirectory = logisticsRequestDirectory;

        // Style buttons
        startTransitButton.setBackground(new Color(70, 130, 180)); // Steel Blue
        startTransitButton.setForeground(Color.WHITE);
        startTransitButton.setFont(new Font("Arial", Font.BOLD, 12));
        startTransitButton.setFocusPainted(false);

        completeButton.setBackground(new Color(50, 205, 50)); // Lime Green
        completeButton.setForeground(Color.WHITE);
        completeButton.setFont(new Font("Arial", Font.BOLD, 12));
        completeButton.setFocusPainted(false);

        // Style "View Details" label
        viewDetailsLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        viewDetailsLabel.setForeground(Color.BLUE);
        viewDetailsLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        viewDetailsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showDetailsDialog();
            }
        });

        // Add button actions
        startTransitButton.addActionListener(e -> handleAction("In Transit"));
        completeButton.addActionListener(e -> handleAction("Completed"));
    }

    private void handleAction(String newStatus) {
        int row = table.getSelectedRow();
        String requestId = (String) tableModel.getValueAt(row, 0); // Assuming "Request ID" is in column 0

        int confirmation = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to update this request to '" + newStatus + "'?",
                "Confirm Action",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            // Update the status in the directory and table
            logisticsRequestDirectory.updateRequestStatus(requestId, newStatus);
            tableModel.setValueAt(newStatus, row, 4); // Assuming "Status" is in column 4
            fireEditingStopped(); // Notify the table to update the cell
        }
    }

    private void showDetailsDialog() {
        int row = table.getSelectedRow();
        String requestId = (String) tableModel.getValueAt(row, 0); // Assuming "Request ID" is in column 0
        LogisticsRequest request = logisticsRequestDirectory.getRequestById(requestId);

        if (request != null) {
            // Create a detailed dialog
            JPanel detailsPanel = new JPanel(new GridLayout(5, 2, 10, 10));
            detailsPanel.add(new JLabel("Request ID:"));
            detailsPanel.add(new JLabel(request.getRequestId()));
            detailsPanel.add(new JLabel("Driver:"));
            detailsPanel.add(new JLabel(request.getDriver()));
            detailsPanel.add(new JLabel("Pickup Date:"));
            detailsPanel.add(new JLabel(request.getPickupDate()));
            detailsPanel.add(new JLabel("Pickup Time:"));
            detailsPanel.add(new JLabel(request.getPickupTime()));
            detailsPanel.add(new JLabel("Status:"));
            detailsPanel.add(new JLabel(request.getStatus()));

            JOptionPane.showMessageDialog(null, detailsPanel, "Logistics Request Details", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Unable to find details for this request.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        panel.removeAll(); // Clear previous components
        this.table = table;

        String status = (String) table.getValueAt(row, 4); // Assuming "Status" is in column 4
        if ("Scheduled".equalsIgnoreCase(status)) {
            panel.add(startTransitButton);
        } else if ("In Transit".equalsIgnoreCase(status)) {
            panel.add(completeButton);
        } else if ("Completed".equalsIgnoreCase(status)) {
            panel.add(viewDetailsLabel); // Add "View Details" for completed requests
        }

        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}