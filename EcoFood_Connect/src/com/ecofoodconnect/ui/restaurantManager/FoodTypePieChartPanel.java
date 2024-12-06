/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui.restaurantManager;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author tanmay
 */

public class FoodTypePieChartPanel extends JPanel {
    private Map<String, Double> data;
    private Map<String, Color> colorMapping;

    public FoodTypePieChartPanel(Map<String, Double> data) {
        this.data = data;
        setPreferredSize(new Dimension(400, 300));

        // Define fixed colors for food types
        colorMapping = new HashMap<>();
        colorMapping.put("Perishable", new Color(135, 206, 250)); // Light Blue
        colorMapping.put("Non-Perishable", new Color(144, 238, 144)); // Light Green
        colorMapping.put("Canned", new Color(255, 182, 193)); // Light Pink
        colorMapping.put("Frozen", new Color(255, 165, 0)); // Orange
        colorMapping.put("Fresh Produce", new Color(173, 216, 230)); // Light Cyan
        colorMapping.put("Beverages", new Color(238, 130, 238)); // Violet
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

        // Center the Pie Chart
        int diameter = Math.min(getWidth() / 2, getHeight() / 2);
        int x = (getWidth() - diameter) / 2; // Dynamically calculate X for centering
        int y = getHeight() / 4;

        double total = data.values().stream().mapToDouble(Double::doubleValue).sum();
        double startAngle = 0;

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            double angle = (entry.getValue() / total) * 360;

            // Slice
            g2d.setColor(colorMapping.getOrDefault(entry.getKey(), getRandomColor()));
            g2d.fillArc(x, y, diameter, diameter, (int) startAngle, (int) angle);

            startAngle += angle;
        }

        // Legend
        int legendX = x + diameter + 20;
        int legendY = y;
        for (Map.Entry<String, Color> entry : colorMapping.entrySet()) {
            g2d.setColor(entry.getValue());
            g2d.fillRect(legendX, legendY, 15, 15);
            g2d.setColor(Color.BLACK);
            g2d.drawString(entry.getKey(), legendX + 20, legendY + 12);
            legendY += 20;
        }
    }

    private Color getRandomColor() {
        return new Color((int) (Math.random() * 0x1000000));
    }
}