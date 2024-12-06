/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author tanmay
 */

import com.ecofoodconnect.models.DonationRequestDirectory;
import com.ecofoodconnect.ui.restaurantManager.CreateDonationRequestPanel;
import com.ecofoodconnect.ui.restaurantManager.ViewDonationRequestsPanel;

import com.ecofoodconnect.models.DonationRequestDirectory;
import com.ecofoodconnect.services.AuthService;
import com.ecofoodconnect.ui.BaseDashboard;
import com.ecofoodconnect.ui.restaurantManager.DonationReportsPanel;


public class RestaurantManagerDashboard extends BaseDashboard {

    private DonationRequestDirectory donationRequestDirectory;

    public RestaurantManagerDashboard(JFrame previousScreen) {
        super("Restaurant Manager Dashboard");

        // Create a single shared instance of DonationRequestDirectory
        this.donationRequestDirectory = AuthService.getDonationRequestDirectory();

        JTabbedPane tabbedPane = new JTabbedPane();

        // Pass the shared instance to both panels
        CreateDonationRequestPanel createPanel = new CreateDonationRequestPanel(donationRequestDirectory);
        tabbedPane.addTab("Create Donation Requests", createPanel.createPanel());

        ViewDonationRequestsPanel viewPanel = new ViewDonationRequestsPanel(donationRequestDirectory);
        JPanel viewPanelUI = viewPanel.createPanel();
        tabbedPane.addTab("View Donation Requests", viewPanelUI);
        
        DonationReportsPanel reportsPanel = new DonationReportsPanel(donationRequestDirectory);
        tabbedPane.addTab("Donation Reports", reportsPanel.createPanel());

        // Add listener to reload data when switching to the "View Donation Requests" tab
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedComponent() == viewPanelUI) {
                viewPanel.loadDonationRequests(); // Reload table data
            }
        });

        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }
    

    private JPanel createDonationReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel headerLabel = new JLabel("Donation Reports", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(new Color(34, 139, 34));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        JPanel reportPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        reportPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        reportPanel.setBackground(Color.WHITE);

        JLabel totalDonationsLabel = new JLabel("Total Donations: ");
        totalDonationsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalDonationsLabel.setForeground(Color.BLACK);

        JLabel foodWasteReductionLabel = new JLabel("Food Waste Reduced: ");
        foodWasteReductionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        foodWasteReductionLabel.setForeground(Color.BLACK);

        reportPanel.add(totalDonationsLabel);
        reportPanel.add(foodWasteReductionLabel);

        panel.add(reportPanel, BorderLayout.CENTER);
        return panel;
    }
}