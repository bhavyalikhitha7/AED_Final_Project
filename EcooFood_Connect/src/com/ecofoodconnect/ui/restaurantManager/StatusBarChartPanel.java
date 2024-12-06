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
 * @author tanmay
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

        for (int i = 0; i <= 10; i++) {
            int gridY = y + (chartHeight * i / 10);
            g2d.drawLine(x, gridY, x + chartWidth, gridY);

            // Y-axis values
            String value = String.valueOf((maxValue * (10 - i)) / 10);
            g2d.setColor(Color.BLACK);
            g2d.drawString(value, x - 30, gridY + 5);
        }

        int barWidth = chartWidth / data.size();
        int i = 0;

        for (Map.Entry<String, Long> entry : data.entrySet()) {
            int barHeight = (int) ((entry.getValue() * 1.0 / maxValue) * chartHeight);

            // Gradient fill for bars
            GradientPaint gradient = new GradientPaint(
                    x + i * barWidth, y + chartHeight - barHeight,
                    new Color(70, 130, 180),
                    x + i * barWidth + barWidth - 10, y + chartHeight,
                    new Color(30, 144, 255)
            );
            g2d.setPaint(gradient);
            g2d.fillRect(x + i * barWidth, y + chartHeight - barHeight, barWidth - 10, barHeight);

            // Label
            g2d.setColor(Color.BLACK);
            g2d.drawString(entry.getKey(), x + i * barWidth + (barWidth / 4), y + chartHeight + 15);
            i++;
        }
    }
}