/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui.restaurantManager;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
/**
 *
 * @author Bhavya
 */
public class StatusBarChartPanel extends JPanel {
    private Map<String, Long> data;

    public StatusBarChartPanel(Map<String, Long> data) {
        this.data = data;
        setPreferredSize(new Dimension(400, 300));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if (data == null || data.isEmpty()) {
            g2d.setColor(Color.BLACK);
            g2d.drawString("No Data Available", getWidth() / 2 - 50, getHeight() / 2);
            return;
        }

        // Bar Chart
        int chartWidth = getWidth() - 100;
        int chartHeight = getHeight() - 100;
        int x = 50, y = 50;

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawRect(x, y, chartWidth, chartHeight);

        // Draw grid lines and Y-axis values
        g2d.setColor(new Color(200, 200, 200));
        long maxValue = data.values().stream().mapToLong(Long::longValue).max().orElse(1);

        for (int i = 0; i <= maxValue; i++) {
            int gridY = y + (chartHeight * (int)(maxValue - i) / (int)maxValue);
            g2d.drawLine(x, gridY, x + chartWidth, gridY);

            // Y-axis values
            String value = String.valueOf(i);
            g2d.setColor(Color.BLACK);
            g2d.drawString(value, x - 30, gridY + 5);
        }

        // Colors for bars
        Color[] colors = {
                new Color(70, 130, 180), new Color(255, 99, 71),
                new Color(154, 205, 50), new Color(255, 215, 0),
                new Color(238, 130, 238)
        };

        int barWidth = chartWidth / data.size();
        int i = 0;

        // Draw bars
        for (Map.Entry<String, Long> entry : data.entrySet()) {
            int barHeight = (int) ((entry.getValue() * 1.0 / maxValue) * chartHeight);

            // Color for each bar
            g2d.setColor(colors[i % colors.length]);
            g2d.fillRect(x + i * barWidth, y + chartHeight - barHeight, barWidth - 10, barHeight);

            // Label
            g2d.setColor(Color.BLACK);
            g2d.drawString(entry.getKey(), x + i * barWidth + (barWidth / 4), y + chartHeight + 15);
            i++;
        }
    }
}

