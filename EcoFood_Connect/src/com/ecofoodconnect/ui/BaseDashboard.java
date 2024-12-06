/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author tanmay
 */
public class BaseDashboard extends JFrame {
    private JFrame previousScreen;
    private JButton backButton;

    public BaseDashboard(String title) {
        setTitle(title);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(34, 139, 34));
        JLabel headerLabel = new JLabel(title, SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        // Back Button
//        backButton = new JButton("Back");
//        backButton.setFont(new Font("Arial", Font.BOLD, 14));
//        backButton.setBackground(new Color(220, 220, 220));
//        backButton.setVisible(false); // Initially hidden
//        backButton.addActionListener((ActionEvent e) -> {
//            if (previousScreen != null) {
//                dispose();
//                previousScreen.setVisible(true);
//            }
//        });
//        headerPanel.add(backButton, BorderLayout.WEST);
        
        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setBackground(new Color(255, 69, 0));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener((ActionEvent e) -> handleLogout());
        headerPanel.add(logoutButton, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);
    }

    // Set the previous screen reference
//    public void setPreviousScreen(JFrame previousScreen) {
//        this.previousScreen = previousScreen;
//        backButton.setVisible(previousScreen != null); // Show/hide based on availability
//    }
    
    private void handleLogout() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Logout Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (choice == JOptionPane.YES_OPTION) {
            dispose(); // Close current screen
            new LoginScreen().setVisible(true); // Redirect to login screen
        }
    }
}