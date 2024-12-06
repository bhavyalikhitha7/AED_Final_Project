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
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

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

        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }
}
