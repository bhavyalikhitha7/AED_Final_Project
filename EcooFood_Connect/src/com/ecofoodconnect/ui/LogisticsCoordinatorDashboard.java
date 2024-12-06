/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui;

import com.ecofoodconnect.models.DonationRequestDirectory;
import com.ecofoodconnect.models.LogisticsRequestDirectory;
import com.ecofoodconnect.services.AuthService;
import com.ecofoodconnect.ui.logisticsCoordinator.AssignDriversPanel;
import com.ecofoodconnect.ui.logisticsCoordinator.TrackLogisticsPanel;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author tanmay
 */

public class LogisticsCoordinatorDashboard extends BaseDashboard {
    private LogisticsRequestDirectory logisticsRequestDirectory;

    public LogisticsCoordinatorDashboard(JFrame previousScreen) {
        super("Logistics Coordinator Dashboard");
        this.logisticsRequestDirectory = AuthService.getLogisticsRequestDirectory(); // Fetch from AuthService

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Assign Drivers", createAssignDriversTab());
        tabbedPane.addTab("Track Logistics", createTrackLogisticsTab());

        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createAssignDriversTab() {
        return new AssignDriversPanel(logisticsRequestDirectory).createPanel();
    }

    private JPanel createTrackLogisticsTab() {
        return new TrackLogisticsPanel(logisticsRequestDirectory).createPanel();
    }
}
