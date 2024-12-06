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
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author tanmay
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

        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }
}
