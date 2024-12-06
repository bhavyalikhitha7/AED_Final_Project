/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui.foodBankManager;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
/**
 *
 * @author tanmay
 */

public class ActionButtonRenderer extends JPanel implements TableCellRenderer {
    private JButton approveButton = new JButton("Approve");
    private JButton rejectButton = new JButton("Reject");
    private JLabel placeholderLabel = new JLabel("-");

    public ActionButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        setBackground(Color.WHITE);

        // Style approve button
        approveButton.setBackground(new Color(50, 205, 50)); // Lime Green
        approveButton.setForeground(Color.WHITE);
        approveButton.setFont(new Font("Arial", Font.BOLD, 14));
        approveButton.setFocusPainted(false);
        approveButton.setPreferredSize(new Dimension(90, 30)); // Adjust button size

        // Style reject button
        rejectButton.setBackground(Color.RED);
        rejectButton.setForeground(Color.WHITE);
        rejectButton.setFont(new Font("Arial", Font.BOLD, 14));
        rejectButton.setFocusPainted(false);
        rejectButton.setPreferredSize(new Dimension(90, 30)); // Adjust button size

        // Style placeholder label
        placeholderLabel.setFont(new Font("Arial", Font.BOLD, 14));
        placeholderLabel.setForeground(Color.GRAY);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        removeAll(); // Clear previous components

        String status = (String) table.getValueAt(row, 6); // Get status from the "Status" column
        if ("Pending".equalsIgnoreCase(status)) {
            add(approveButton);
            add(rejectButton);
        } else {
            add(placeholderLabel);
        }

        return this;
    }
}