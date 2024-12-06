/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui.systemAdmin;

import com.ecofoodconnect.models.Person;
import com.ecofoodconnect.services.AuthService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 *
 * @author tanmay
 */

public class ReportsPanel extends JPanel {
    private JTextArea userMetricsArea;
    private JPanel roleDistributionList;
    private JTextArea loginTrendsArea;

    public ReportsPanel() {
        setLayout(new BorderLayout());

        // Header
        JLabel headerLabel = new JLabel("Detailed System Reports", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(34, 139, 34));
        add(headerLabel, BorderLayout.NORTH);

        // Main content area
        JPanel contentPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        contentPanel.setBackground(Color.WHITE);

        // Section 1: User Metrics
        userMetricsArea = new JTextArea();
        JPanel userMetricsPanel = createCard("User Metrics", userMetricsArea, new Color(173, 216, 230));
        contentPanel.add(userMetricsPanel);

        // Section 2: Role Distribution
        roleDistributionList = new JPanel(new GridLayout(0, 1)); // Dynamic list layout
        JScrollPane roleScrollPane = new JScrollPane(roleDistributionList);
        roleScrollPane.setBorder(BorderFactory.createEmptyBorder());
        JPanel roleDistributionPanel = createCard("Role Distribution", roleScrollPane, new Color(240, 230, 140));
        contentPanel.add(roleDistributionPanel);

        // Section 3: Login Trends
        loginTrendsArea = new JTextArea();
        JPanel loginTrendsPanel = createCard("Login Trends", loginTrendsArea, new Color(255, 228, 181));
        contentPanel.add(loginTrendsPanel);

        add(contentPanel, BorderLayout.CENTER);

        // Footer
        JButton exportButton = new JButton("Export Report as PDF");
        exportButton.setFont(new Font("Arial", Font.BOLD, 16));
        exportButton.setBackground(new Color(34, 139, 34));
        exportButton.setForeground(Color.WHITE);
        exportButton.addActionListener(e -> exportReport());
        add(exportButton, BorderLayout.SOUTH);

        // Start periodic updates
        Timer timer = new Timer(5000, e -> updateData());
        timer.start();
    }

    private JPanel createCard(String title, JComponent content, Color backgroundColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(backgroundColor);
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        card.add(titleLabel, BorderLayout.NORTH);

        card.add(content, BorderLayout.CENTER);

        return card;
    }

    private void updateData() {
        // Update User Metrics
        ArrayList<Person> users = AuthService.getAllUsers();
        userMetricsArea.setText("Total Users: " + users.size() + "\n" +
                "Active Users: " + AuthService.getActiveUserCount() + "\n" +
                "Most Active Role: " + AuthService.getMostActiveRole());

        // Update Role Distribution
        roleDistributionList.removeAll();
        Map<String, Integer> roleCounts = AuthService.getRoleCounts();
        for (Map.Entry<String, Integer> entry : roleCounts.entrySet()) {
            JLabel roleLabel = new JLabel(entry.getKey() + ": " + entry.getValue());
            roleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            roleDistributionList.add(roleLabel);
        }
        roleDistributionList.revalidate();
        roleDistributionList.repaint();

        // Update Login Trends
        loginTrendsArea.setText(AuthService.getLoginTrends());
    }

    private void exportReport() {
        JOptionPane.showMessageDialog(this, "Report exported successfully!", "Export Complete", JOptionPane.INFORMATION_MESSAGE);
    }
}