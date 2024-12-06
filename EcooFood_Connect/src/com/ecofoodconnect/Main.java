/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect;

import javax.swing.SwingUtilities;

/**
 *
 * @author tanmay
 */
public class Main {
    public static void main(String[] args) {
        // Initialize application configuration
        AppConfig.initialize();

        // Ensure UI is initialized on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Show login screen
            new LoginScreen().setVisible(true);
        });
    }
}