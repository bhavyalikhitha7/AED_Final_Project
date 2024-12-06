/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui.logisticsCoordinator;

import com.ecofoodconnect.models.LogisticsRequest;
import com.ecofoodconnect.models.LogisticsRequestDirectory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author tanmay
 */
public class TrackLogisticsPanel {
    private LogisticsRequestDirectory logisticsRequestDirectory;

    public TrackLogisticsPanel(LogisticsRequestDirectory logisticsRequestDirectory) {
        this.logisticsRequestDirectory = logisticsRequestDirectory;
    }

    public JPanel createPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Header
        JLabel headerLabel = new JLabel("Track Ongoing Logistics", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(34, 139, 34));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"Request ID", "Driver", "Pickup Date", "Pickup Time", "Status", "Actions"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(245, 245, 245) : Color.WHITE); // Zebra stripes
                } else {
                    c.setBackground(new Color(173, 216, 230)); // Highlight selected row
                }
                return c;
            }
        };

        // Table header styling
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(34, 139, 34));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(50); // Increased row height

        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(150); // Request ID
        table.getColumnModel().getColumn(5).setPreferredWidth(250); // Actions column

        // Populate table
        tableModel.setRowCount(0); // Clear any existing rows
        for (LogisticsRequest request : logisticsRequestDirectory.getRequests()) {
            tableModel.addRow(new Object[]{
                    request.getRequestId(),
                    request.getDriver(),
                    request.getPickupDate(),
                    request.getPickupTime(),
                    request.getStatus(),
                    "Mark Delivered"
            });
        }

        // Add action buttons
        table.getColumnModel().getColumn(5).setCellRenderer(new ActionButtonRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new ActionButtonEditor(new JCheckBox(), tableModel, logisticsRequestDirectory));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
}