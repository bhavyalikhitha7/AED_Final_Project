/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui;

import com.ecofoodconnect.models.DonationRequestDirectory;
import com.ecofoodconnect.services.AuthService;
import com.ecofoodconnect.ui.qualityInspector.InspectionPanel;
import com.ecofoodconnect.ui.qualityInspector.QualityMetricsPanel;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author tanmay
 */
public class QualityInspectorDashboard extends BaseDashboard {
    private DonationRequestDirectory donationRequestDirectory;

    public QualityInspectorDashboard(JFrame previousScreen) {
        super("Quality Inspector Dashboard");

        // Use the centralized DonationRequestDirectory
        this.donationRequestDirectory = AuthService.getDonationRequestDirectory();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Inspect Donations", createInspectionPanel());
        tabbedPane.addTab("Quality Metrics", createQualityMetricsPanel());

        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createInspectionPanel() {
        return new InspectionPanel(donationRequestDirectory).createPanel();
    }

    private JPanel createQualityMetricsPanel() {
        return new QualityMetricsPanel(donationRequestDirectory).createPanel();
    }
}