/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui.foodBankManager;

import com.ecofoodconnect.models.DonationRequest;
import com.ecofoodconnect.models.DonationRequestDirectory;
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
    private JButton approveButton = new JButton("Approve");
    private JButton rejectButton = new JButton("Reject");
    private JLabel placeholderLabel = new JLabel("-");
    private JTable table;
    private DefaultTableModel tableModel;
    private DonationRequestDirectory donationRequestDirectory;
    private LogisticsRequestDirectory logisticsRequestDirectory;

    public ActionButtonEditor(JCheckBox checkBox, DefaultTableModel tableModel, DonationRequestDirectory donationRequestDirectory) {
        this.tableModel = tableModel;
        this.donationRequestDirectory = donationRequestDirectory;

        // Style approve button
        approveButton.setBackground(new Color(50, 205, 50));
        approveButton.setForeground(Color.WHITE);
        approveButton.setFont(new Font("Arial", Font.BOLD, 14));
        approveButton.setFocusPainted(false);

        // Style reject button
        rejectButton.setBackground(Color.RED);
        rejectButton.setForeground(Color.WHITE);
        rejectButton.setFont(new Font("Arial", Font.BOLD, 14));
        rejectButton.setFocusPainted(false);

        // Style placeholder label
        placeholderLabel.setFont(new Font("Arial", Font.BOLD, 14));
        placeholderLabel.setForeground(Color.GRAY);

        // Approve button action
        approveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                String requestId = (String) tableModel.getValueAt(row, 0);
                confirmAndUpdateStatus(row, requestId, "Approved");
            }
        });

        // Reject button action
        rejectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                String requestId = (String) tableModel.getValueAt(row, 0);
                confirmAndUpdateStatus(row, requestId, "Rejected");
            }
        });
    }
    
    public ActionButtonEditor(JCheckBox checkBox, DefaultTableModel tableModel, LogisticsRequestDirectory logisticsRequestDirectory) {
        this.tableModel = tableModel;
        this.logisticsRequestDirectory = logisticsRequestDirectory;

        // Style approve button
        approveButton.setBackground(new Color(50, 205, 50));
        approveButton.setForeground(Color.WHITE);
        approveButton.setFont(new Font("Arial", Font.BOLD, 14));
        approveButton.setFocusPainted(false);

        // Style reject button
        rejectButton.setBackground(Color.RED);
        rejectButton.setForeground(Color.WHITE);
        rejectButton.setFont(new Font("Arial", Font.BOLD, 14));
        rejectButton.setFocusPainted(false);

        // Style placeholder label
        placeholderLabel.setFont(new Font("Arial", Font.BOLD, 14));
        placeholderLabel.setForeground(Color.GRAY);

        // Approve button action
        approveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                String requestId = (String) tableModel.getValueAt(row, 0);
                confirmAndUpdateStatus(row, requestId, "Approved");
            }
        });

        // Reject button action
        rejectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                String requestId = (String) tableModel.getValueAt(row, 0);
                confirmAndUpdateStatus(row, requestId, "Rejected");
            }
        });
    }

    private void confirmAndUpdateStatus(int row, String requestId, String status) {
        int confirmation = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to " + status.toLowerCase() + " this request?",
                "Confirm " + status,
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            for (DonationRequest request : donationRequestDirectory.getDonationRequests()) {
                if (request.getId().equals(requestId)) {
                    request.setStatus(status); // Update status in the model
                    tableModel.setValueAt(status, row, 6); // Update status in the table
                    tableModel.fireTableDataChanged();
                    JOptionPane.showMessageDialog(null, "Request " + status + " successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        }
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        panel.removeAll(); // Clear previous components
        this.table = table;

        String status = (String) table.getValueAt(row, 6); // Get status from the "Status" column
        if ("Pending".equalsIgnoreCase(status)) {
            panel.add(approveButton);
            panel.add(rejectButton);
        } else {
            panel.add(placeholderLabel);
        }

        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}