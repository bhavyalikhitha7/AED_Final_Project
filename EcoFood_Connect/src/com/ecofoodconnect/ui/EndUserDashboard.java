/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui;

import com.ecofoodconnect.models.DonationRequest;
import com.ecofoodconnect.services.AuthService;
import com.ecofoodconnect.ui.endUser.ActionButtonEditor;
import com.ecofoodconnect.ui.endUser.ActionButtonRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author tanmay
 */
public class EndUserDashboard extends BaseDashboard {
    private String loggedInUser;
    private DefaultTableModel availableDonationsTableModel;
    private DefaultTableModel myRequestsTableModel;

    public EndUserDashboard(JFrame previousScreen) {
        super("End User Dashboard");
//        setPreviousScreen(previousScreen); // Enable back navigation
        this.loggedInUser = AuthService.getCurrentUser().getUsername();

        JTabbedPane tabbedPane = new JTabbedPane();

        // Add tabs
        tabbedPane.addTab("Available Donations", createAvailableDonationsTab());
        tabbedPane.addTab("My Requests", createMyRequestsTab());
        // Set light colors for the tabs
       
        tabbedPane.setBackgroundAt(0, new Color(240, 230, 140)); // Light yellow
        tabbedPane.setBackgroundAt(1, new Color(144, 238, 144)); // Light green
        
         // Customize the size and font of the tabs
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14)); // Increase font size
        tabbedPane.setPreferredSize(new Dimension(800, 40)); // Adjust tab height

        // Modify UI to increase tab width and height
        UIManager.put("TabbedPane.tabInsets", new Insets(10, 30, 10, 30)); // Padding for width and height
        UIManager.put("TabbedPane.tabAreaInsets", new Insets(10, 10, 10, 10)); // Padding around the tab area

        add(tabbedPane, BorderLayout.CENTER);
        
        new DeliverySimulator(AuthService.getDonationRequestDirectory()).startSimulation();

        setVisible(true);
    }

    private JPanel createAvailableDonationsTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel headerLabel = new JLabel("Available Donations", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(34, 139, 34));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Food Type", "Quantity", "Expiry Date", "Notes", "Actions"};
        availableDonationsTableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(availableDonationsTableModel) {
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

        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(40);

        // Load available donations
        loadAvailableDonations();

        // Add Action Button
        table.getColumnModel().getColumn(5).setCellRenderer(new ActionButtonRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new ActionButtonEditor(new JCheckBox(), availableDonationsTableModel, AuthService.getDonationRequestDirectory(), loggedInUser, this));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createMyRequestsTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel headerLabel = new JLabel("My Requests", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(34, 139, 34));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Food Type", "Quantity", "Status", "Actions"};
        myRequestsTableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(myRequestsTableModel) {
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

        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(40);

        // Load user's requests
        loadMyRequests();

        // Add action buttons
        table.getColumnModel().getColumn(4).setCellRenderer(new ActionButtonRenderer());
        table.getColumnModel().getColumn(4).setCellEditor(new ActionButtonEditor(
                new JCheckBox(), myRequestsTableModel, AuthService.getDonationRequestDirectory(), loggedInUser, this));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void loadAvailableDonations() {
        availableDonationsTableModel.setRowCount(0); // Clear existing rows
        ArrayList<DonationRequest> requests = AuthService.getDonationRequestDirectory().getDonationRequests();
        for (DonationRequest request : requests) {
            if ("Approved".equalsIgnoreCase(request.getStatus()) && request.getRequestedBy() == null) {
                availableDonationsTableModel.addRow(new Object[]{
                        request.getId(),
                        request.getFoodType(),
                        request.getQuantity(),
                        request.getExpiryDate(),
                        request.getNotes(),
                        "Request"
                });
            }
        }
    }

    private void loadMyRequests() {
        myRequestsTableModel.setRowCount(0); // Clear existing rows
        ArrayList<DonationRequest> requests = AuthService.getDonationRequestDirectory().getDonationRequests();

        for (DonationRequest request : requests) {
            if (loggedInUser.equals(request.getRequestedBy())) { // Only show requests made by the logged-in user
                String action = "Requested".equalsIgnoreCase(request.getStatus()) ? "Confirm Received" : "-";
                myRequestsTableModel.addRow(new Object[]{
                        request.getId(),
                        request.getFoodType(),
                        request.getQuantity(),
                        request.getStatus(),
                        action // Show Confirm Received only for requested items
                });
            }
        }
    }

    public void refreshTabs() {
        loadAvailableDonations();
        loadMyRequests();
    }
}