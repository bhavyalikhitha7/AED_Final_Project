/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect;

import com.ecofoodconnect.config.AppConfig;
import com.ecofoodconnect.ui.LoginScreen;
import javax.swing.SwingUtilities;

/**
 *
 * @author tanmay
 */
public class Main {
    public static void main(String[] args) {
        // Initialize application configuration
        AppConfig.testConnection();

        // Ensure UI is initialized on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Show login screen
            new LoginScreen().setVisible(true);
        });
    }
}