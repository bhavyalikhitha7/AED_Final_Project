/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui.foodBankManager;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
/**
 *
 * @author Bhavya
 */
public class InventoryBarChartPanel extends JPanel {
    private Map<String, Double> inventory;

    public InventoryBarChartPanel(Map<String, Double> inventory) {
        this.inventory = inventory;
        setPreferredSize(new Dimension(400, 200));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if (inventory == null || inventory.isEmpty()) {
            g2d.setColor(Color.BLACK);
            g2d.drawString("No Data Available", getWidth() / 2 - 50, getHeight() / 2);
            return;
        }

        int chartWidth = getWidth() - 100;
        int chartHeight = getHeight() - 50;
        int x = 50, y = 20;

        // Draw axes
        g2d.setColor(Color.BLACK);
        g2d.drawLine(x, y, x, y + chartHeight); // Y-axis
        g2d.drawLine(x, y + chartHeight, x + chartWidth, y + chartHeight); // X-axis

        // Define colors for the bars
        Color[] colors = {
            new Color(70, 130, 180),
            new Color(255, 99, 71),
            new Color(154, 205, 50),
            new Color(255, 215, 0),
            new Color(238, 130, 238),
            new Color(244, 164, 96)
        };

        // Calculate bar properties
        int barWidth = chartWidth / inventory.size();
        double maxQuantity = inventory.values().stream().mapToDouble(Double::doubleValue).max().orElse(1);

        int i = 0;
        for (Map.Entry<String, Double> entry : inventory.entrySet()) {
            String foodType = entry.getKey();
            double quantity = entry.getValue();
            int barHeight = (int) ((quantity / maxQuantity) * chartHeight);

            // Set color for each bar
            g2d.setColor(colors[i % colors.length]); // Cycle through colors
            g2d.fillRect(x + i * barWidth + 10, y + chartHeight - barHeight, barWidth - 20, barHeight);

            // Label
            g2d.setColor(Color.BLACK);
            g2d.drawString(foodType, x + i * barWidth + 15, y + chartHeight + 15);
            g2d.drawString(String.format("%.2f lbs", quantity), x + i * barWidth + 15, y + chartHeight - barHeight - 5);

            i++;
        }
    }
}
