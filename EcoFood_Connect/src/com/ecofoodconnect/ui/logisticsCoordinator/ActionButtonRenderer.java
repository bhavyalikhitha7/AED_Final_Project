/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui.logisticsCoordinator;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
/**
 *
 * @author tanmay
 */

public class ActionButtonRenderer extends JPanel implements TableCellRenderer {
    private JButton startTransitButton = new JButton("Start Transit");
    private JButton completeButton = new JButton("Mark as Completed");
    private JLabel viewDetailsLabel = new JLabel("View Details");

    public ActionButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        setBackground(Color.WHITE);

        // Style buttons
        startTransitButton.setBackground(new Color(70, 130, 180)); // Steel Blue
        startTransitButton.setForeground(Color.WHITE);
        startTransitButton.setFont(new Font("Arial", Font.BOLD, 12));
        startTransitButton.setFocusPainted(false);

        completeButton.setBackground(new Color(50, 205, 50)); // Lime Green
        completeButton.setForeground(Color.WHITE);
        completeButton.setFont(new Font("Arial", Font.BOLD, 12));
        completeButton.setFocusPainted(false);

        // Style label
        viewDetailsLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        viewDetailsLabel.setForeground(Color.GRAY);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        removeAll(); // Clear previous components

        String status = (String) table.getValueAt(row, 4); // Assuming "Status" is in column 4
        if ("Scheduled".equalsIgnoreCase(status)) {
            add(startTransitButton);
        } else if ("In Transit".equalsIgnoreCase(status)) {
            add(completeButton);
        } else if ("Completed".equalsIgnoreCase(status)) {
            add(viewDetailsLabel);
        }

        return this;
    }
}