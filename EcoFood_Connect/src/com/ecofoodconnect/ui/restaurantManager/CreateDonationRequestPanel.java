/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui.restaurantManager;

import com.ecofoodconnect.models.DonationRequest;
import com.ecofoodconnect.models.DonationRequestDirectory;
import com.ecofoodconnect.services.AuthService;
import com.ecofoodconnect.utils.DateValidator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.UUID;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author tanmay
 */

public class CreateDonationRequestPanel {

    private DonationRequestDirectory donationRequestDirectory;

    public CreateDonationRequestPanel(DonationRequestDirectory donationRequestDirectory) {
        this.donationRequestDirectory = donationRequestDirectory;
    }

    public JPanel createPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Header
        JLabel headerLabel = new JLabel("Create Donation Request", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(new Color(34, 139, 34));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fields
        JLabel foodTypeLabel = new JLabel("Food Type:");
        JComboBox<String> foodTypeDropdown = new JComboBox<>(new String[]{"Perishable", "Non-Perishable", "Canned", "Frozen", "Fresh Produce", "Beverages"});
        JLabel quantityLabel = new JLabel("Quantity (lbs):");
        JTextField quantityField = new JTextField();
        JLabel expiryDateLabel = new JLabel("Expiry Date:");
        JTextField expiryDateField = new JTextField("MM/DD/YYYY");
        JLabel notesLabel = new JLabel("Additional Notes:");
        JTextArea notesField = new JTextArea(4, 20);
        notesField.setLineWrap(true);
        JScrollPane notesScrollPane = new JScrollPane(notesField);

        // Adding fields
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(foodTypeLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(foodTypeDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(quantityLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(quantityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(expiryDateLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(expiryDateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(notesLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(notesScrollPane, gbc);

        panel.add(formPanel, BorderLayout.CENTER);

        // Buttons
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String foodType = (String) foodTypeDropdown.getSelectedItem();
            String quantityStr = quantityField.getText();
            String expiryDate = expiryDateField.getText();
            String notes = notesField.getText();

            if (foodType == null || quantityStr.isEmpty() || expiryDate.isEmpty() || notes.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(panel, "Please enter a valid positive number for Quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate Expiry Date
            if (!DateValidator.isValidDate(expiryDate)) {
                JOptionPane.showMessageDialog(panel, "Please enter a valid expiry date in MM/DD/YYYY format, and it must be today or a future date.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create and Add Donation Request
            String createdBy = AuthService.getCurrentUser().getUsername(); // Get logged-in user's username
            DonationRequest request = new DonationRequest(
                    UUID.randomUUID().toString(),
                    foodType,
                    quantity,
                    expiryDate,
                    notes,
                    "Pending",
                    createdBy
            );
            donationRequestDirectory.addDonationRequest(request);

            JOptionPane.showMessageDialog(panel, "Donation Request Created Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Reset Fields
            foodTypeDropdown.setSelectedIndex(0);
            quantityField.setText("");
            expiryDateField.setText("MM/DD/YYYY");
            notesField.setText("");
        });
        
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            foodTypeDropdown.setSelectedIndex(0);
            quantityField.setText("");
            expiryDateField.setText("MM/DD/YYYY");
            notesField.setText("");
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(submitButton);
        buttonPanel.add(resetButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
}