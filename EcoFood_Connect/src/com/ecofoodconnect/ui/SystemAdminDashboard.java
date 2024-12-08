/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui;

import com.ecofoodconnect.ui.systemAdmin.PlatformMonitoringPanel;
import com.ecofoodconnect.ui.systemAdmin.ReportsPanel;
import com.ecofoodconnect.ui.systemAdmin.UserManagementPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

/**
 *
 * @author Bhavya
 */
public class SystemAdminDashboard extends BaseDashboard {

    public SystemAdminDashboard(JFrame previousScreen) {
        super("System Admin Dashboard");
//        setPreviousScreen(previousScreen);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Add tabs with separate JPanel classes
        tabbedPane.addTab("Manage Users", new UserManagementPanel());
        tabbedPane.addTab("System Reports", new ReportsPanel());
        tabbedPane.addTab("Monitor Platform", new PlatformMonitoringPanel());
         // Set light colors for the tabs
        tabbedPane.setBackgroundAt(0, new Color(173, 216, 250)); // Light blue
        tabbedPane.setBackgroundAt(1, new Color(240, 230, 140)); // Light yellow
        tabbedPane.setBackgroundAt(2, new Color(144, 238, 144)); // Light green
        
         // Customize the size and font of the tabs
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14)); // Increase font size
        tabbedPane.setPreferredSize(new Dimension(800, 40)); // Adjust tab height

        // Modify UI to increase tab width and height
        UIManager.put("TabbedPane.tabInsets", new Insets(10, 30, 10, 30)); // Padding for width and height
        UIManager.put("TabbedPane.tabAreaInsets", new Insets(10, 10, 10, 10)); // Padding around the tab area
        
        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }
}
