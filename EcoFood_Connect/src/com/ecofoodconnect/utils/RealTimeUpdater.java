/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.utils;

import com.ecofoodconnect.ui.EndUserDashboard;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.SwingUtilities;

/**
 *
 * @author tanmay
 */
public class RealTimeUpdater {
    private EndUserDashboard dashboard;

    public RealTimeUpdater(EndUserDashboard dashboard) {
        this.dashboard = dashboard;
    }

    public void startUpdater() {
        Timer timer = new Timer(true); // Daemon timer
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    // Refresh the UI dynamically
                    dashboard.refreshTabs();
                });
            }
        }, 0, 10000); // Initial delay 0ms, repeat every 10 seconds
    }
}