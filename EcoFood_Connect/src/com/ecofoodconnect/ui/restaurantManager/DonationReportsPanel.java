/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui.restaurantManager;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author tanmay
 */
import com.ecofoodconnect.models.DonationRequest;
import com.ecofoodconnect.models.DonationRequestDirectory;
import com.ecofoodconnect.services.AuthService;

public class DonationReportsPanel {

    private DonationRequestDirectory donationRequestDirectory;

    public DonationReportsPanel(DonationRequestDirectory donationRequestDirectory) {
        this.donationRequestDirectory = donationRequestDirectory;
    }

    public JPanel createPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel headerLabel = new JLabel("Donation Reports", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(new Color(34, 139, 34));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        JPanel chartsPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        chartsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        chartsPanel.setBackground(Color.WHITE);

        // Add updated charts
        chartsPanel.add(new StatusBarChartPanel(prepareStatusData()));
        chartsPanel.add(new FoodTypePieChartPanel(prepareFoodTypeData()));

        panel.add(chartsPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        int totalRequests = donationRequestDirectory.getDonationRequests().size();
        double totalQuantity = donationRequestDirectory.getDonationRequests()
                .stream()
                .mapToDouble(DonationRequest::getQuantity)
                .sum();

        JLabel totalRequestsLabel = new JLabel("Total Donations: " + totalRequests, SwingConstants.CENTER);
        totalRequestsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalRequestsLabel.setForeground(Color.BLACK);

        JLabel totalQuantityLabel = new JLabel("Total Quantity Donated: " + totalQuantity + " lbs", SwingConstants.CENTER);
        totalQuantityLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalQuantityLabel.setForeground(Color.BLACK);

        panel.add(totalRequestsLabel);
        panel.add(totalQuantityLabel);

        return panel;
    }

    private Map<String, Long> prepareStatusData() {
        String loggedInUser = AuthService.getCurrentUser().getUsername();
        Map<String, Long> statusCounts = new HashMap<>();

        for (DonationRequest request : donationRequestDirectory.getDonationRequests()) {
            System.out.println("Request Created By: " + request.getCreatedBy()); // Debug print
            if (request.getCreatedBy().equals(loggedInUser)) {
                statusCounts.put(request.getStatus(), statusCounts.getOrDefault(request.getStatus(), 0L) + 1);
            }
        }

        System.out.println("Status Data: " + statusCounts); // Debug print
        return statusCounts;
    }

    private Map<String, Double> prepareFoodTypeData() {
        String loggedInUser = AuthService.getCurrentUser().getUsername(); // Get logged-in user's username
        Map<String, Double> foodTypeCounts = new HashMap<>();

        for (DonationRequest request : donationRequestDirectory.getDonationRequests()) {
            if (request.getCreatedBy().equals(loggedInUser)) { // Only include the logged-in user's requests
                foodTypeCounts.put(request.getFoodType(), foodTypeCounts.getOrDefault(request.getFoodType(), 0.0) + request.getQuantity());
            }
        }
        return foodTypeCounts;
    }
}