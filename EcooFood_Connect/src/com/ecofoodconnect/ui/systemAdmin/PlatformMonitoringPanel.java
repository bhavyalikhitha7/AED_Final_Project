/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui.systemAdmin;

import com.ecofoodconnect.services.AuthService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 *
 * @author tanmay
 */
public class PlatformMonitoringPanel extends JPanel {
    private JLabel activeUserLabel;
    private JProgressBar cpuUsageBar;
    private JProgressBar memoryUsageBar;
    private JTextArea logArea;

    public PlatformMonitoringPanel() {
        setLayout(new BorderLayout());

        // Header
        JLabel headerLabel = new JLabel("Real-Time Platform Monitoring", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(34, 139, 34));
        add(headerLabel, BorderLayout.NORTH);

        // Main content area
        JPanel contentPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        contentPanel.setBackground(Color.WHITE);

        // Section 1: Active Users
        activeUserLabel = new JLabel("Active Users: " + AuthService.getActiveUserCount(), SwingConstants.CENTER);
        activeUserLabel.setFont(new Font("Arial", Font.BOLD, 18));
        contentPanel.add(createCard("", activeUserLabel, new Color(173, 216, 230)));

        // Section 2: System Metrics
        cpuUsageBar = createProgressBar("CPU Usage");
        memoryUsageBar = createProgressBar("Memory Usage");
        JPanel metricsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        metricsPanel.add(cpuUsageBar);
        metricsPanel.add(memoryUsageBar);
        contentPanel.add(createCard("System Metrics", metricsPanel, new Color(240, 230, 140)));

        // Section 3: System Logs
        logArea = new JTextArea(AuthService.getSystemLogs());
        logArea.setFont(new Font("Arial", Font.PLAIN, 14));
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        JScrollPane logScrollPane = new JScrollPane(logArea);
        contentPanel.add(createCard("System Logs", logScrollPane, new Color(255, 228, 181)));

        add(contentPanel, BorderLayout.CENTER);

        // Start periodic updates
        Timer timer = new Timer(3000, e -> updateMetrics());
        timer.start();
    }

    private JPanel createCard(String title, JComponent content, Color backgroundColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(backgroundColor);
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        card.add(titleLabel, BorderLayout.NORTH);

        card.add(content, BorderLayout.CENTER);

        return card;
    }

    private JProgressBar createProgressBar(String label) {
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setBorder(BorderFactory.createTitledBorder(label));
        return progressBar;
    }

    private void updateMetrics() {
        activeUserLabel.setText("Active Users: " + AuthService.getActiveUserCount());
        cpuUsageBar.setValue(AuthService.getCpuUsage());
        memoryUsageBar.setValue(AuthService.getMemoryUsage());
        logArea.setText(AuthService.getSystemLogs());
    }
}