/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui;

import com.ecofoodconnect.ui.enterpriseAdmin.EnterpriseConfigPanel;
import com.ecofoodconnect.ui.enterpriseAdmin.EnterpriseOperationsPanel;
import com.ecofoodconnect.ui.enterpriseAdmin.RoleAssignmentPanel;
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
 * @author tanmay
 */
public class EnterpriseAdminDashboard extends BaseDashboard {
    public EnterpriseAdminDashboard(JFrame previousScreen) {
        super("Enterprise Admin Dashboard");
//        setPreviousScreen(previousScreen);

        // Tabbed Pane for functionalities
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add panels
        tabbedPane.addTab("Enterprise Operations", new EnterpriseOperationsPanel());
        tabbedPane.addTab("Role Assignment", new RoleAssignmentPanel());
        tabbedPane.addTab("Enterprise Configurations", new EnterpriseConfigPanel());
        
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
