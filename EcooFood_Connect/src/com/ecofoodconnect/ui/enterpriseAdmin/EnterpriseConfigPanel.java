/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui.enterpriseAdmin;

import com.ecofoodconnect.services.AuthService;
import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import javax.swing.text.MaskFormatter;


/**
 *
 * @author tanmay
 */

public class EnterpriseConfigPanel extends JPanel {
    private JComboBox<String> startHourDropdown;
    private JComboBox<String> endHourDropdown;
    private JTextField emergencyContactField;
    private JTextField specificField1;
    private JTextField specificField2;
    private JTextField specificField3;
    
    private JPanel categoriesPanel;
    private JPanel distributionAreasPanel;
    private JPanel preferredRoutesPanel;
    private JPanel vehicleTypesPanel; 
    private JPanel wasteCategoriesPanel;
    private JPanel pickupSchedulesPanel;


    public EnterpriseConfigPanel() {
        setLayout(new BorderLayout());

        // Header
        JLabel headerLabel = new JLabel("Enterprise Configurations", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(34, 139, 34));
        add(headerLabel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Fields based on enterprise type
        String enterpriseType = AuthService.getEnterpriseTypeOfCurrentUser();
        switch (enterpriseType) {
            case "Restaurants":
                createRestaurantConfigFields(contentPanel, gbc);
                break;
            case "FoodBanks":
                createFoodBankConfigFields(contentPanel, gbc);
                break;
            case "LogisticsProviders":
                createLogisticsConfigFields(contentPanel, gbc);
                break;
            case "WasteManagementFirms":
                createWasteManagementConfigFields(contentPanel, gbc);
                break;
        }

        add(contentPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = createStyledButton("Save Configurations", new Color(34, 139, 34));
        JButton resetButton = createStyledButton("Reset to Defaults", Color.RED);

        saveButton.addActionListener(e -> handleSaveConfigurations(enterpriseType));
        resetButton.addActionListener(e -> handleResetConfigurations(enterpriseType));

        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void createRestaurantConfigFields(JPanel panel, GridBagConstraints gbc) {
        // Operational Hours
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Operational Hours:"), gbc);

        startHourDropdown = new JComboBox<>(getHours());
        endHourDropdown = new JComboBox<>(getHours());
        startHourDropdown.setSelectedItem("9:00 AM");
        endHourDropdown.setSelectedItem("9:00 PM");

        JPanel hoursPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        hoursPanel.add(startHourDropdown);
        hoursPanel.add(endHourDropdown);
        gbc.gridx = 1;
        panel.add(hoursPanel, gbc);

        // Surplus Food Threshold
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Surplus Food Threshold (lbs):"), gbc);

        specificField1 = new JTextField(10);
        gbc.gridx = 1;
        panel.add(specificField1, gbc);

        // Food Categories
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Food Categories:"), gbc);

        JPanel categoriesPanel = createFoodCategoriesPanel();
        gbc.gridx = 1;
        panel.add(categoriesPanel, gbc);

        // Weekly Food Donation Targets
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Weekly Food Donation Targets (lbs):"), gbc);

        specificField3 = new JTextField(10);
        gbc.gridx = 1;
        panel.add(specificField3, gbc);

        // Emergency Contact
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Emergency Contact:"), gbc);

        emergencyContactField = createFormattedTextField();
        gbc.gridx = 1;
        panel.add(emergencyContactField, gbc);
    }
    
    private JPanel createFoodCategoriesPanel() {
        categoriesPanel = new JPanel(); // Make it accessible for resetting
        categoriesPanel.setLayout(new GridLayout(3, 2, 5, 5));

        JCheckBox perishableCheckbox = new JCheckBox("Perishable");
        JCheckBox nonPerishableCheckbox = new JCheckBox("Non-Perishable");
        JCheckBox cannedCheckbox = new JCheckBox("Canned");
        JCheckBox frozenCheckbox = new JCheckBox("Frozen");
        JCheckBox freshProduceCheckbox = new JCheckBox("Fresh Produce");
        JCheckBox beveragesCheckbox = new JCheckBox("Beverages");

        categoriesPanel.add(perishableCheckbox);
        categoriesPanel.add(nonPerishableCheckbox);
        categoriesPanel.add(cannedCheckbox);
        categoriesPanel.add(frozenCheckbox);
        categoriesPanel.add(freshProduceCheckbox);
        categoriesPanel.add(beveragesCheckbox);

        return categoriesPanel;
    }

    private String[] getHours() {
        return new String[]{
            "6:00 AM", "7:00 AM", "8:00 AM", "9:00 AM", "10:00 AM", "11:00 AM",
            "12:00 PM", "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM", "5:00 PM", "6:00 PM", 
            "7:00 PM", "8:00 PM", "9:00 PM", "10:00 PM", "11:00 PM"
        };
    }

    private JTextField createFormattedTextField() {
        try {
            MaskFormatter formatter = new MaskFormatter("(###) ###-####");
            formatter.setPlaceholderCharacter('_');
            return new JFormattedTextField(formatter);
        } catch (ParseException e) {
            return new JTextField();
        }
    }
    
    private void createFoodBankConfigFields(JPanel panel, GridBagConstraints gbc) {
        // Operational Hours
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.3; // Adjust column weight for labels
        panel.add(new JLabel("Operational Hours:"), gbc);

        startHourDropdown = new JComboBox<>(getHours());
        endHourDropdown = new JComboBox<>(getHours());
        startHourDropdown.setSelectedItem("9:00 AM");
        endHourDropdown.setSelectedItem("9:00 PM");

        JPanel hoursPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        hoursPanel.add(startHourDropdown);
        hoursPanel.add(endHourDropdown);
        gbc.gridx = 1;
        gbc.weightx = 0.7; // Adjust column weight for inputs
        panel.add(hoursPanel, gbc);

        // Storage Capacity
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0.3;
        panel.add(new JLabel("Storage Capacity (lbs):"), gbc);

        specificField1 = new JTextField(10);
        specificField1.setPreferredSize(new Dimension(100, 25)); // Set consistent width
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(specificField1, gbc);

        // Food Distribution Areas
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 0.3;
        panel.add(new JLabel("Food Distribution Areas:"), gbc);

        JPanel distributionAreasPanel = createDistributionAreasPanel();
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.fill = GridBagConstraints.BOTH; // Allow dynamic resizing for multi-select checkboxes
        panel.add(distributionAreasPanel, gbc);

        // Required Food Categories
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.weightx = 0.3;
        panel.add(new JLabel("Required Food Categories:"), gbc);

        JPanel foodCategoriesPanel = createFoodCategoriesPanel();
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.fill = GridBagConstraints.BOTH; // Allow dynamic resizing for multi-select checkboxes
        panel.add(foodCategoriesPanel, gbc);

        // Emergency Contact
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JLabel("Emergency Contact:"), gbc);

        emergencyContactField = createFormattedTextField();
        emergencyContactField.setPreferredSize(new Dimension(80, 25)); // Set consistent width
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(emergencyContactField, gbc);
    }
    
    private JPanel createDistributionAreasPanel() {
        distributionAreasPanel = new JPanel(new GridLayout(5, 2, 5, 5));

        String[] majorCities = {
            "New York", "Los Angeles", "Chicago", "Houston", "Phoenix",
            "Philadelphia", "San Antonio", "San Diego", "Dallas", "San Jose"
        };

        for (String city : majorCities) {
            JCheckBox cityCheckbox = new JCheckBox(city);
            distributionAreasPanel.add(cityCheckbox);
        }

        return distributionAreasPanel;
    }

    private void createLogisticsConfigFields(JPanel panel, GridBagConstraints gbc) {
        // Operational Hours
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Operational Hours:"), gbc);

        startHourDropdown = new JComboBox<>(getHours());
        endHourDropdown = new JComboBox<>(getHours());
        startHourDropdown.setSelectedItem("9:00 AM");
        endHourDropdown.setSelectedItem("9:00 PM");

        JPanel hoursPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        hoursPanel.add(startHourDropdown);
        hoursPanel.add(endHourDropdown);
        gbc.gridx = 1;
        panel.add(hoursPanel, gbc);

        // Preferred Routes
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Preferred Routes:"), gbc);

        preferredRoutesPanel = createPreferredRoutesPanel(); // Initialize here
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(preferredRoutesPanel, gbc);

        // Fleet Size
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JLabel("Fleet Size:"), gbc);

        specificField1 = new JTextField(10);
        gbc.gridx = 1;
        panel.add(specificField1, gbc);

        // Vehicle Types
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Vehicle Types:"), gbc);

        vehicleTypesPanel = createVehicleTypesPanel(); // Initialize here
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(vehicleTypesPanel, gbc);

        // Emergency Contact
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JLabel("Emergency Contact:"), gbc);

        emergencyContactField = createFormattedTextField();
        gbc.gridx = 1;
        panel.add(emergencyContactField, gbc);
    }
    
    private JPanel createPreferredRoutesPanel() {
        preferredRoutesPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        String[] routes = {"Route A", "Route B", "Route C", "Route D", "Route E"};
        for (String route : routes) {
            JCheckBox routeCheckbox = new JCheckBox(route);
            preferredRoutesPanel.add(routeCheckbox);
        }

        return preferredRoutesPanel;
    }
    
    private JPanel createVehicleTypesPanel() {
        vehicleTypesPanel = new JPanel(new GridLayout(2, 2, 5, 5));

        String[] vehicleTypes = {"Refrigerated", "Standard", "Heavy-Duty", "Electric"};
        for (String type : vehicleTypes) {
            JCheckBox typeCheckbox = new JCheckBox(type);
            vehicleTypesPanel.add(typeCheckbox);
        }

        return vehicleTypesPanel;
    }
    
    private void createWasteManagementConfigFields(JPanel panel, GridBagConstraints gbc) {
        // Operational Hours
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Operational Hours:"), gbc);

        startHourDropdown = new JComboBox<>(getHours());
        endHourDropdown = new JComboBox<>(getHours());
        startHourDropdown.setSelectedItem("9:00 AM");
        endHourDropdown.setSelectedItem("9:00 PM");

        JPanel hoursPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        hoursPanel.add(startHourDropdown);
        hoursPanel.add(endHourDropdown);
        gbc.gridx = 1;
        panel.add(hoursPanel, gbc);

        // Waste Categories
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Waste Categories:"), gbc);

        wasteCategoriesPanel = createWasteCategoriesPanel();
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(wasteCategoriesPanel, gbc);

        // Processing Capacity
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JLabel("Processing Capacity (lbs/day):"), gbc);

        specificField2 = new JTextField(10);
        gbc.gridx = 1;
        panel.add(specificField2, gbc);

        // Pickup Schedules
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Pickup Schedules:"), gbc);

        pickupSchedulesPanel = createPickupSchedulesPanel();
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(pickupSchedulesPanel, gbc);

        // Emergency Contact
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JLabel("Emergency Contact:"), gbc);

        emergencyContactField = createFormattedTextField();
        gbc.gridx = 1;
        panel.add(emergencyContactField, gbc);
    }
    
    private JPanel createWasteCategoriesPanel() {
        wasteCategoriesPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        String[] categories = {"Biodegradable", "Recyclable", "Non-Recyclable", "E-Waste", "Hazardous"};
        for (String category : categories) {
            JCheckBox categoryCheckbox = new JCheckBox(category);
            wasteCategoriesPanel.add(categoryCheckbox);
        }

        return wasteCategoriesPanel;
    }
    
    private JPanel createPickupSchedulesPanel() {
        pickupSchedulesPanel = new JPanel(new GridLayout(2, 2, 5, 5));

        String[] schedules = {"Morning (6:00 AM - 9:00 AM)", "Afternoon (12:00 PM - 3:00 PM)",
                              "Evening (4:00 PM - 7:00 PM)", "Night (8:00 PM - 11:00 PM)"};
        for (String schedule : schedules) {
            JCheckBox scheduleCheckbox = new JCheckBox(schedule);
            pickupSchedulesPanel.add(scheduleCheckbox);
        }

        return pickupSchedulesPanel;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }

    private void handleSaveConfigurations(String enterpriseType) {
        String startHour = (String) startHourDropdown.getSelectedItem();
        String endHour = (String) endHourDropdown.getSelectedItem();
        String emergencyContact = emergencyContactField.getText().trim();

        // Validate common fields
        if (startHour.isEmpty() || endHour.isEmpty() || emergencyContact.isEmpty()) {
            showErrorMessage("Operational hours and emergency contact are required.");
            return;
        }

        try {
            // Additional validations based on enterprise type
            switch (enterpriseType) {
                case "Restaurants":
                    String surplusFoodThreshold = specificField1.getText().trim();
                    String weeklyDonationTarget = specificField3.getText().trim();

                    if (surplusFoodThreshold.isEmpty() || weeklyDonationTarget.isEmpty()) {
                        showErrorMessage("Surplus food threshold and weekly donation target are required.");
                        return;
                    }

                    // Validate numeric inputs
                    Double.parseDouble(surplusFoodThreshold);
                    Double.parseDouble(weeklyDonationTarget);
                    break;

                case "FoodBanks":
                    String storageCapacity = specificField1.getText().trim();
                    if (storageCapacity.isEmpty()) {
                        showErrorMessage("Storage capacity is required.");
                        return;
                    }

                    // Validate numeric input
                    Double.parseDouble(storageCapacity);
                    break;

                case "LogisticsProviders":
                    String fleetSize = specificField1.getText().trim();
                    if (fleetSize.isEmpty()) {
                        showErrorMessage("Fleet size is required.");
                        return;
                    }

                    // Validate numeric input
                    Integer.parseInt(fleetSize);

                    // Validate Preferred Routes
                    if (!isValidCheckboxSelection(preferredRoutesPanel)) {
                        showErrorMessage("At least one preferred route must be selected.");
                        return;
                    }

                    // Validate Vehicle Types
                    if (!isValidCheckboxSelection(vehicleTypesPanel)) {
                        showErrorMessage("At least one vehicle type must be selected.");
                        return;
                    }
                    break;

                case "WasteManagementFirms":
                    String processingCapacity = specificField2.getText().trim();
                    if (processingCapacity.isEmpty()) {
                        showErrorMessage("Processing capacity is required.");
                        return;
                    }

                    // Validate numeric input
                    try {
                        Double.parseDouble(processingCapacity);
                    } catch (NumberFormatException e) {
                        showErrorMessage("Processing capacity must be a valid number.");
                        return;
                    }

                    // Validate Waste Categories
                    if (!isValidCheckboxSelection(wasteCategoriesPanel)) {
                        showErrorMessage("At least one waste category must be selected.");
                        return;
                    }

                    // Validate Pickup Schedules
                    if (!isValidCheckboxSelection(pickupSchedulesPanel)) {
                        showErrorMessage("At least one pickup schedule must be selected.");
                        return;
                    }
                    break;
                default:
                    showErrorMessage("Unknown enterprise type.");
                    return;
            }
        } catch (NumberFormatException e) {
            showErrorMessage("Numeric fields must contain valid numbers.");
            return;
        }

        // Save the configurations (mock backend save for now)
        JOptionPane.showMessageDialog(this,
                "Configurations saved successfully for " + enterpriseType + "!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleResetConfigurations(String enterpriseType) {
        // Reset common fields
        startHourDropdown.setSelectedItem("9:00 AM");
        endHourDropdown.setSelectedItem("9:00 PM");
        emergencyContactField.setText("(123) 456-7890");

        // Reset fields specific to each enterprise type
        switch (enterpriseType) {
            case "Restaurants":
                if (specificField1 != null) specificField1.setText(""); // Surplus Food Threshold
                if (specificField3 != null) specificField3.setText(""); // Weekly Donation Target
                resetFoodCategories();
                break;

            case "FoodBanks":
                if (specificField1 != null) specificField1.setText(""); // Storage Capacity
                resetDistributionAreas();
                resetFoodCategories();
                break;

            case "LogisticsProviders":
                if (specificField1 != null) specificField1.setText(""); // Fleet Size
                resetCheckboxPanel(preferredRoutesPanel);
                resetCheckboxPanel(vehicleTypesPanel);
                break;

            case "WasteManagementFirms":
                if (specificField2 != null) specificField2.setText(""); // Processing Capacity
                resetCheckboxPanel(wasteCategoriesPanel); // Reset Waste Categories
                resetCheckboxPanel(pickupSchedulesPanel); // Reset Pickup Schedules
                break;

            default:
                JOptionPane.showMessageDialog(this,
                        "Unknown enterprise type for reset.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
        }

        // Inform the user
        JOptionPane.showMessageDialog(this,
                "Configurations reset to default values for " + enterpriseType + "!",
                "Reset Complete",
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    private boolean isValidCheckboxSelection(JPanel panel) {
        for (Component component : panel.getComponents()) {
            if (component instanceof JCheckBox && ((JCheckBox) component).isSelected()) {
                return true; // At least one checkbox is selected
            }
        }
        return false;
    }
    
    private void resetCheckboxPanel(JPanel panel) {
        for (Component component : panel.getComponents()) {
            if (component instanceof JCheckBox) {
                ((JCheckBox) component).setSelected(false); // Unselect all checkboxes
            }
        }
    }
    
    private void resetFoodCategories() {
        Component[] components = categoriesPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                checkBox.setSelected(false); // Unselect all categories
            }
        }
    }
    
    private void resetDistributionAreas() {
        Component[] components = distributionAreasPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                checkBox.setSelected(false); // Unselect all areas
            }
        }
    }
    
    private void resetWasteCategories() {
        if (wasteCategoriesPanel != null) {
            Component[] components = wasteCategoriesPanel.getComponents();
            for (Component component : components) {
                if (component instanceof JCheckBox) {
                    ((JCheckBox) component).setSelected(false); // Unselect all categories
                }
            }
        }
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, 
            message, 
            "Input Error", 
            JOptionPane.ERROR_MESSAGE);
    }
}