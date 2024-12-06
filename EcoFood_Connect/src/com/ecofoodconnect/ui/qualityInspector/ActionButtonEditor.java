/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui.qualityInspector;

import com.ecofoodconnect.models.DonationRequestDirectory;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author tanmay
 */

public class ActionButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
    private JButton approveButton = new JButton("Approve");
    private JButton rejectButton = new JButton("Reject");
    private JTable table;
    private DefaultTableModel tableModel;
    private DonationRequestDirectory donationRequestDirectory;

    public ActionButtonEditor(JCheckBox checkBox, DefaultTableModel tableModel, DonationRequestDirectory donationRequestDirectory) {
        this.tableModel = tableModel;
        this.donationRequestDirectory = donationRequestDirectory;

        // Style approve button
        approveButton.setBackground(new Color(50, 205, 50)); // Lime Green
        approveButton.setForeground(Color.WHITE);
        approveButton.setFont(new Font("Arial", Font.BOLD, 12));
        approveButton.setFocusPainted(false);

        // Style reject button
        rejectButton.setBackground(Color.RED);
        rejectButton.setForeground(Color.WHITE);
        rejectButton.setFont(new Font("Arial", Font.BOLD, 12));
        rejectButton.setFocusPainted(false);

        // Add button actions
        approveButton.addActionListener(e -> handleAction("Approved"));
        rejectButton.addActionListener(e -> handleAction("Rejected"));
    }

    private void handleAction(String newStatus) {
        int row = table.getSelectedRow();
        String requestId = (String) tableModel.getValueAt(row, 0); // Assuming "Request ID" is in column 0

        if ("Rejected".equals(newStatus)) {
            String reason = JOptionPane.showInputDialog(null, "Enter rejection reason:", "Reject Request", JOptionPane.PLAIN_MESSAGE);
            if (reason == null || reason.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Rejection reason cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        int confirmation = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to " + newStatus.toLowerCase() + " this request?",
                "Confirm Action",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            // Update the status in the directory and table
            donationRequestDirectory.getDonationRequests().stream()
                    .filter(request -> request.getId().equals(requestId))
                    .findFirst()
                    .ifPresent(request -> request.setStatus(newStatus));
            tableModel.setValueAt(newStatus, row, 5); // Assuming "Status" is in column 5
            fireEditingStopped(); // Notify the table to update the cell
        }
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        panel.removeAll(); // Clear previous components
        this.table = table;

        String status = (String) table.getValueAt(row, 5); // Assuming "Status" is in column 5
        if ("Pending".equalsIgnoreCase(status)) {
            panel.add(approveButton);
            panel.add(rejectButton);
        } else {
            panel.add(new JLabel("-")); // Placeholder for non-pending statuses
        }

        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}
