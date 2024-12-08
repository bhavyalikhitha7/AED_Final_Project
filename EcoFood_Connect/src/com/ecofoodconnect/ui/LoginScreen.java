/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui;

import com.ecofoodconnect.models.Person;
import com.ecofoodconnect.services.AuthService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.net.URL;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author tanmay
 */
public class LoginScreen extends javax.swing.JFrame {

    /**
     * Creates new form LoginScreen
     */
//    public LoginScreen() {
//        setTitle("EcoFood Connect - Login");
//        setSize(400, 600);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        setLayout(new BorderLayout());
//
//        // Header Panel
//        JPanel headerPanel = new JPanel();
//        headerPanel.setBackground(new Color(34, 139, 34));
//        JLabel headerLabel = new JLabel("EcoFood Connect");
//        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
//        headerLabel.setForeground(Color.WHITE);
//        headerPanel.add(headerLabel);
//        
//        // Image Panel (Between Header and Form)
//        JPanel imagePanel = new JPanel();
//        imagePanel.setBackground(Color.WHITE);
//
//        // Load and scale the image
//        ImageIcon logoIcon = null;
//        URL imageUrl = getClass().getResource("/images/logo.png");
//        if (imageUrl == null) {
//            System.err.println("Image not found! Ensure it is in 'src/images/logo.png'");
//        } else {
//            logoIcon = new ImageIcon(imageUrl);
//            Image scaledImage = logoIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
//            logoIcon = new ImageIcon(scaledImage);
//        }
//
//        JLabel logoLabel = new JLabel(logoIcon);
//        imagePanel.add(logoLabel);
//        
//        // Form Panel
//        JPanel formPanel = new JPanel();
//        formPanel.setLayout(new GridBagLayout());
//        formPanel.setBackground(Color.WHITE);
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(15, 15, 15, 15);
//
//        JLabel usernameLabel = new JLabel("Username:");
//        JTextField usernameField = new JTextField(20);
//
//        JLabel passwordLabel = new JLabel("Password:");
//        JPasswordField passwordField = new JPasswordField(20);
//
//        JButton loginButton = new JButton("Login");
//        loginButton.setBackground(new Color(0, 102, 204));
//        loginButton.setForeground(Color.WHITE);
//
//        JButton registerButton = new JButton("Register");
//        registerButton.setBackground(Color.LIGHT_GRAY);
//
//        // Add components to form
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        formPanel.add(usernameLabel, gbc);
//        gbc.gridx = 1;
//        formPanel.add(usernameField, gbc);
//
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        formPanel.add(passwordLabel, gbc);
//        gbc.gridx = 1;
//        formPanel.add(passwordField, gbc);
//
//        gbc.gridx = 0;
//        gbc.gridy = 2;
//        gbc.gridwidth = 2;
//        formPanel.add(loginButton, gbc);
//
//        gbc.gridy = 3;
//        formPanel.add(registerButton, gbc);
//
//        // Footer
//        JPanel footerPanel = new JPanel();
//        footerPanel.setBackground(new Color(240, 240, 240));
//        JLabel footerLabel = new JLabel("© 2024 EcoFood Connect. All rights reserved.");
//        footerLabel.setForeground(Color.GRAY);
//        footerPanel.add(footerLabel);
//
//        // Add Action Listeners
//        loginButton.addActionListener(e -> {
//            String username = usernameField.getText().trim();
//            String password = new String(passwordField.getPassword()).trim();
//
//            if (username.isEmpty() || password.isEmpty()) {
//                JOptionPane.showMessageDialog(null, "Username and password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            Person user = AuthService.authenticate(username, password);
//            if (user != null) {
//                JOptionPane.showMessageDialog(null, "Login Successful! Welcome " + user.getName());
//                dispose();
//
//                // Redirect to appropriate dashboard
//                JFrame dashboard = switch (user.getRole()) {
//                    case "SystemAdmin" -> new SystemAdminDashboard(this);
//                    case "EnterpriseAdmin" -> new EnterpriseAdminDashboard(this);
//                    case "RestaurantManager" -> new RestaurantManagerDashboard(this);
//                    case "FoodBankManager" -> new FoodBankManagerDashboard(this);
//                    case "LogisticsCoordinator" -> new LogisticsCoordinatorDashboard(this);
//                    case "QualityInspector" -> new QualityInspectorDashboard(this);
//                    case "WasteManagementOperator" -> new WasteManagementDashboard(this);
//                    case "EndUser" -> new EndUserDashboard(this);
//                    default -> {
//                        JOptionPane.showMessageDialog(null, "Role not recognized.", "Error", JOptionPane.ERROR_MESSAGE);
//                        yield null; // Return null for incompatible roles.
//                    }
//                };
//            } else {
//                JOptionPane.showMessageDialog(null, "Invalid Credentials. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        });
//
//        registerButton.addActionListener(e -> {
//            dispose();
//            new RegisterScreen().setVisible(true); // Redirect to Register Screen
//        });
//
//        add(headerPanel, BorderLayout.NORTH);
//        add(imagePanel, BorderLayout.CENTER); 
//        add(formPanel, BorderLayout.SOUTH);
//        add(footerPanel, BorderLayout.PAGE_END);
//
//        setVisible(true);
//    }


    public LoginScreen() {
        setTitle("EcoFood Connect - Login");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(34, 139, 34));
        JLabel headerLabel = new JLabel("EcoFood Connect");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        // Image Panel
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.WHITE);

        ImageIcon logoIcon = null;
        URL imageUrl = getClass().getResource("/images/logo.png");
        if (imageUrl == null) {
            System.err.println("Image not found! Ensure it is in 'src/images/logo.png'");
        } else {
            logoIcon = new ImageIcon(imageUrl);
            Image scaledImage = logoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            logoIcon = new ImageIcon(scaledImage);
        }

        JLabel logoLabel = new JLabel(logoIcon);
        imagePanel.add(logoLabel);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);

        JButton registerButton = new JButton("Register");
        registerButton.setBackground(Color.LIGHT_GRAY);

        // Add components to form
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(loginButton, gbc);

        gbc.gridy = 3;
        formPanel.add(registerButton, gbc);

        // Combine Image and Form Panels
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS)); // Stack vertically
        centerPanel.add(imagePanel);
        centerPanel.add(formPanel);
        
        
        //        // Add Action Listeners
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username and password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Person user = AuthService.authenticate(username, password);
            if (user != null) {
                JOptionPane.showMessageDialog(null, "Login Successful! Welcome " + user.getName());
                dispose();

                // Redirect to appropriate dashboard
                JFrame dashboard = switch (user.getRole()) {
                    case "SystemAdmin" -> new SystemAdminDashboard(this);
                    case "EnterpriseAdmin" -> new EnterpriseAdminDashboard(this);
                    case "RestaurantManager" -> new RestaurantManagerDashboard(this);
                    case "FoodBankManager" -> new FoodBankManagerDashboard(this);
                    case "LogisticsCoordinator" -> new LogisticsCoordinatorDashboard(this);
                    case "QualityInspector" -> new QualityInspectorDashboard(this);
                    case "WasteManagementOperator" -> new WasteManagementDashboard(this);
                    case "EndUser" -> new EndUserDashboard(this);
                    default -> {
                        JOptionPane.showMessageDialog(null, "Role not recognized.", "Error", JOptionPane.ERROR_MESSAGE);
                        yield null; // Return null for incompatible roles.
                    }
                };
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Credentials. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> {
            dispose();
            new RegisterScreen().setVisible(true); // Redirect to Register Screen
        });

        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(240, 240, 240));
        JLabel footerLabel = new JLabel("© 2024 EcoFood Connect. All rights reserved.");
        footerLabel.setForeground(Color.GRAY);
        footerPanel.add(footerLabel);

        // Add Panels to Frame
        add(headerPanel, BorderLayout.NORTH);    // Header
        add(centerPanel, BorderLayout.CENTER);  // Image and Form combined
        add(footerPanel, BorderLayout.PAGE_END); // Footer

        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// 

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {        
        SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

