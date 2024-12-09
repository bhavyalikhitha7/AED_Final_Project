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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

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
        
         tabbedPane.setBackgroundAt(0, new Color(240, 230, 140)); // Light yellow
        tabbedPane.setBackgroundAt(1, new Color(144, 238, 144)); // Light green

          // Customize the size and font of the tabs
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14)); // Increase font size
        tabbedPane.setPreferredSize(new Dimension(800, 40)); // Adjust tab height

        // Modify UI to increase tab width and height
        UIManager.put("TabbedPane.tabInsets", new Insets(10, 30, 10, 30)); // Padding for width and height
        UIManager.put("TabbedPane.tabAreaInsets", new Insets(10, 10, 10, 10)); // Padding around the tab area

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