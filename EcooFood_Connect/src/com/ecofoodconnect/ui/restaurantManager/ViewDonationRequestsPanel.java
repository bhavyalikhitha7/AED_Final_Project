/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui.restaurantManager;

import com.ecofoodconnect.models.DonationRequest;
import com.ecofoodconnect.models.DonationRequestDirectory;
import com.ecofoodconnect.services.AuthService;
import com.ecofoodconnect.utils.DateValidator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author tanmay
 */


public class ViewDonationRequestsPanel {

    private DonationRequestDirectory donationRequestDirectory;
    private DefaultTableModel tableModel;

    public ViewDonationRequestsPanel(DonationRequestDirectory donationRequestDirectory) {
        this.donationRequestDirectory = donationRequestDirectory;
    }

    public JPanel createPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Header
        JLabel headerLabel = new JLabel("View Donation Requests", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(new Color(34, 139, 34));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"ID", "Food Type", "Quantity (lbs)", "Expiry Date", "Notes", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
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
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(34, 139, 34));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton editButton = createStyledButton("Edit Request", new Color(70, 130, 180));
        JButton deleteButton = createStyledButton("Delete Request", Color.RED);

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add button actions
        editButton.addActionListener(e -> handleEditRequest(table));
        deleteButton.addActionListener(e -> handleDeleteRequest(table));

        // Load Data
        loadDonationRequests();

        return panel;
    }

    public void loadDonationRequests() {
        tableModel.setRowCount(0); // Clear existing data

        String loggedInUser = AuthService.getCurrentUser().getUsername(); // Get logged-in user's username
        System.out.println("Logged-in User: " + loggedInUser);

        for (DonationRequest request : donationRequestDirectory.getDonationRequests()) {
            System.out.println("Request Created By: " + request.getCreatedBy()); // Debug print
            if (request.getCreatedBy().equals(loggedInUser)) { // Only display requests created by the logged-in user
                tableModel.addRow(new Object[]{
                        request.getId(),
                        request.getFoodType(),
                        request.getQuantity(),
                        request.getExpiryDate(),
                        request.getNotes(),
                        request.getStatus()
                });
            }
        }
         System.out.println("Rows added to table: " + tableModel.getRowCount()); 
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(color.darker(), 2));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        return button;
    }

    private void handleEditRequest(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a request to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the selected request
        String requestId = (String) tableModel.getValueAt(selectedRow, 0);
        DonationRequest request = donationRequestDirectory.getDonationRequests()
                .stream()
                .filter(r -> r.getId().equals(requestId))
                .findFirst()
                .orElse(null);

        if (request != null) {
            // Fields for editing
            JComboBox<String> foodTypeDropdown = new JComboBox<>(new String[]{
                    "Perishable", "Non-Perishable", "Canned", "Frozen", "Fresh Produce", "Beverages"
            });
            foodTypeDropdown.setSelectedItem(request.getFoodType());

            JTextField quantityField = new JTextField(String.valueOf(request.getQuantity()));

            JTextField expiryDateField = new JTextField(request.getExpiryDate());

            JTextArea notesField = new JTextArea(request.getNotes(), 4, 20);
            notesField.setLineWrap(true);
            JScrollPane notesScrollPane = new JScrollPane(notesField);

            // Edit Panel
            JPanel editPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            gbc.gridx = 0;
            gbc.gridy = 0;
            editPanel.add(new JLabel("Food Type:"), gbc);

            gbc.gridx = 1;
            editPanel.add(foodTypeDropdown, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            editPanel.add(new JLabel("Quantity (lbs):"), gbc);

            gbc.gridx = 1;
            editPanel.add(quantityField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            editPanel.add(new JLabel("Expiry Date:"), gbc);

            gbc.gridx = 1;
            editPanel.add(expiryDateField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            editPanel.add(new JLabel("Additional Notes:"), gbc);

            gbc.gridx = 1;
            editPanel.add(notesScrollPane, gbc);

            // Show edit dialog
            int result = JOptionPane.showConfirmDialog(null, editPanel, "Edit Donation Request", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                // Validation
                String foodType = (String) foodTypeDropdown.getSelectedItem();
                String quantityStr = quantityField.getText();
                String expiryDate = expiryDateField.getText();
                String notes = notesField.getText();

                if (foodType == null || quantityStr.isEmpty() || expiryDate.isEmpty() || notes.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate Quantity
                double quantity;
                try {
                    quantity = Double.parseDouble(quantityStr);
                    if (quantity <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid positive number for Quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate Expiry Date
                if (!DateValidator.isValidDate(expiryDate)) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid expiry date in MM/DD/YYYY format, and it must be today or a future date.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update Request
                request.setFoodType(foodType);
                request.setQuantity(quantity);
                request.setExpiryDate(expiryDate);
                request.setNotes(notes);

                // Reload Table
                loadDonationRequests();

                JOptionPane.showMessageDialog(null, "Donation Request updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void handleDeleteRequest(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a request to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String requestId = (String) tableModel.getValueAt(selectedRow, 0);

        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this request?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            donationRequestDirectory.getDonationRequests().removeIf(request -> request.getId().equals(requestId));
            loadDonationRequests(); // Reload table
            JOptionPane.showMessageDialog(null, "Donation Request deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}