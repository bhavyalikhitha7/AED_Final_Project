/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui;

import com.ecofoodconnect.models.DonationRequest;
import com.ecofoodconnect.models.DonationRequestDirectory;
import com.ecofoodconnect.models.FoodBankManager;
import com.ecofoodconnect.services.AuthService;
import com.ecofoodconnect.ui.foodBankManager.InventoryBarChartPanel;
import com.ecofoodconnect.ui.foodBankManager.ActionButtonEditor;
import com.ecofoodconnect.ui.foodBankManager.ActionButtonRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author tanmay
 */
public class FoodBankManagerDashboard extends BaseDashboard {
    private DonationRequestDirectory donationRequestDirectory;

    public FoodBankManagerDashboard(JFrame previousScreen) {
        super("Food Bank Manager Dashboard");
        this.donationRequestDirectory = AuthService.getDonationRequestDirectory();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Donation Requests", createDonationRequestsTab());
        tabbedPane.addTab("Inventory Monitoring", createInventoryMonitoringTab());
         tabbedPane.setBackgroundAt(0, new Color(173, 216, 250)); // Light blue
        tabbedPane.setBackgroundAt(1, new Color(240, 230, 140)); // Light yellow
  // Customize the size and font of the tabs
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14)); // Increase font size
        tabbedPane.setPreferredSize(new Dimension(800, 40)); // Adjust tab height

        // Modify UI to increase tab width and height
        UIManager.put("TabbedPane.tabInsets", new Insets(10, 30, 10, 30)); // Padding for width and height
        UIManager.put("TabbedPane.tabAreaInsets", new Insets(10, 10, 10, 10)); // Padding around the tab area

        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }

    public JPanel createDonationRequestsTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Header
        JLabel headerLabel = new JLabel("Review Donation Requests", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(34, 139, 34));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"ID", "Food Type", "Quantity", "Restaurant", "Expiry Date", "Notes", "Status", "Actions"};
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

        // Set row height for better button visibility
        table.setRowHeight(50);

        // Table header styling
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(34, 139, 34));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        // Set column width for "Actions" column
        table.getColumnModel().getColumn(7).setPreferredWidth(200);

        // Populate table with all donation requests
        for (DonationRequest request : donationRequestDirectory.getDonationRequests()) {
            tableModel.addRow(new Object[]{
                    request.getId(),
                    request.getFoodType(),
                    request.getQuantity(),
                    request.getCreatedBy(), // Restaurant
                    request.getExpiryDate(),
                    request.getNotes(),
                    request.getStatus(),
                    "" // Placeholder for Action buttons or "-"
            });
        }

        // Add custom renderer and editor for action buttons
        table.getColumnModel().getColumn(7).setCellRenderer(new ActionButtonRenderer());
        table.getColumnModel().getColumn(7).setCellEditor(new ActionButtonEditor(new JCheckBox(), tableModel, donationRequestDirectory));

        // Scroll pane with modern border
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(192, 192, 192), 1));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
    
    public JPanel createInventoryMonitoringTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Header
        JLabel headerLabel = new JLabel("Food Bank Inventory", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(34, 139, 34));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"Food Type", "Quantity (lbs)", "Last Updated", "Percentage of Total"};
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
        table.setRowHeight(30);

        // Center-align numeric columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Quantity
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Percentage

        // Calculate inventory based on approved requests
        Map<String, Double> inventory = new HashMap<>();
        Map<String, String> lastUpdated = new HashMap<>();
        double totalInventory = 0;

        for (DonationRequest request : donationRequestDirectory.getDonationRequests()) {
            if ("Approved".equalsIgnoreCase(request.getStatus())) {
                String foodType = request.getFoodType();
                inventory.put(foodType, inventory.getOrDefault(foodType, 0.0) + request.getQuantity());
                lastUpdated.put(foodType, request.getExpiryDate()); // Assuming expiry date as last updated
                totalInventory += request.getQuantity();
            }
        }

        // Populate the table
        DecimalFormat df = new DecimalFormat("0.00");
        for (Map.Entry<String, Double> entry : inventory.entrySet()) {
            String foodType = entry.getKey();
            double quantity = entry.getValue();
            String percentage = totalInventory > 0 ? df.format((quantity / totalInventory) * 100) + "%" : "0%";
            String updatedDate = lastUpdated.get(foodType);

            tableModel.addRow(new Object[]{foodType, df.format(quantity), updatedDate, percentage});
        }

        // Add total row
        tableModel.addRow(new Object[]{"Total", df.format(totalInventory), "-", "100%"});

        // Scroll pane with modern border
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add chart
        JPanel chartPanel = new JPanel();
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setLayout(new BorderLayout());
        chartPanel.add(new InventoryBarChartPanel(inventory), BorderLayout.CENTER);

        panel.add(chartPanel, BorderLayout.SOUTH);

        return panel;
    }
}
