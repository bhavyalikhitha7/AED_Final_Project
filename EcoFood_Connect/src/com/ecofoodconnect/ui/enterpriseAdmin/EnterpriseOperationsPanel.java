/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui.enterpriseAdmin;

import com.ecofoodconnect.models.Person;
import com.ecofoodconnect.services.AuthService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.UUID;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author tanmay
 */
public class EnterpriseOperationsPanel extends JPanel {

    public EnterpriseOperationsPanel() {
        setLayout(new BorderLayout());

        // Header
        JLabel headerLabel = new JLabel("Enterprise Operations", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(34, 139, 34));
        add(headerLabel, BorderLayout.NORTH);

        // Metrics Section
        JPanel metricsPanel = createMetricsPanel();
        add(metricsPanel, BorderLayout.CENTER);

        // Activity Log Section
        JPanel activityLogPanel = createActivityLogPanel();
        add(activityLogPanel, BorderLayout.SOUTH);
    }

    private JPanel createMetricsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        // Fetch data
        String enterpriseType = AuthService.getEnterpriseTypeOfCurrentUser();
        ArrayList<Person> users = AuthService.getUsersByEnterpriseType(enterpriseType);

        // Metric Cards
        panel.add(createMetricCard("Total Users", String.valueOf(users.size()), new Color(34, 139, 34), this::showTotalUsers));
        panel.add(createMetricCard("Active Roles", String.valueOf(getActiveRoles(users)), new Color(70, 130, 180), this::showActiveRoles));
        panel.add(createMetricCard("Pending Tasks", String.format("%.0f", Math.random() * 20), Color.ORANGE, this::showPendingTasks));
        panel.add(createMetricCard("Completed Operations", String.format("%.0f", Math.random() * 50), Color.BLUE, this::showCompletedOperations));

        return panel;
    }

    private JPanel createMetricCard(String title, String value, Color color, Runnable onClickAction) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(color, 3));
        card.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(color);

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 28));
        valueLabel.setForeground(Color.BLACK);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        // Add click listener
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                onClickAction.run();
            }
        });

        return card;
    }

    private JPanel createActivityLogPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Activity Log"));
        panel.setBackground(Color.WHITE);

        JTextArea logArea = new JTextArea(5, 40);
        logArea.setEditable(false);
        logArea.setText(AuthService.getSystemLogs()); // Simulated logs
        logArea.setFont(new Font("Arial", Font.PLAIN, 14));
        logArea.setForeground(Color.DARK_GRAY);

        panel.add(new JScrollPane(logArea), BorderLayout.CENTER);
        return panel;
    }

    private int getActiveRoles(ArrayList<Person> users) {
        return (int) users.stream().filter(user -> user.getRole() != null).count();
    }
    
    private void showTotalUsers() {
        JFrame frame = new JFrame("Total Users");
        frame.setSize(600, 400);

        // User Table
        String[] columnNames = {"ID", "Name", "Username", "Role"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        // Fetch users
        ArrayList<Person> users = AuthService.getUsersByEnterpriseType(AuthService.getEnterpriseTypeOfCurrentUser());
        for (Person user : users) {
            model.addRow(new Object[]{user.getId(), user.getName(), user.getUsername(), user.getRole()});
        }

        frame.add(new JScrollPane(table));
        frame.setVisible(true);
    }
    
    private void showActiveRoles() {
        JFrame frame = new JFrame("Active Roles");
        frame.setSize(600, 400);

        JTextArea roleArea = new JTextArea();
        roleArea.setEditable(false);

        ArrayList<Person> users = AuthService.getUsersByEnterpriseType(AuthService.getEnterpriseTypeOfCurrentUser());
        StringBuilder roles = new StringBuilder("Active Roles:\n");

        users.stream()
             .filter(user -> user.getRole() != null)
             .forEach(user -> roles.append(user.getRole()).append(" - ").append(user.getName()).append("\n"));

        roleArea.setText(roles.toString());
        frame.add(new JScrollPane(roleArea));
        frame.setVisible(true);
    }
    
    private void showPendingTasks() {
        JFrame frame = new JFrame("Pending Tasks");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Pending Tasks", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(new Color(34, 139, 34));
        frame.add(headerLabel, BorderLayout.NORTH);

        String enterpriseType = AuthService.getEnterpriseTypeOfCurrentUser();
        ArrayList<String> tasks = AuthService.getPendingTasks(enterpriseType);

        DefaultListModel<String> taskListModel = new DefaultListModel<>();
        for (String task : tasks) {
            taskListModel.addElement(task);
        }

        JList<String> taskList = new JList<>(taskListModel);
        taskList.setFont(new Font("Arial", Font.PLAIN, 14));
        taskList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        taskList.setFixedCellHeight(30);

        frame.add(new JScrollPane(taskList), BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setBackground(new Color(70, 130, 180));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> frame.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
    
    private void showCompletedOperations() {
        JFrame frame = new JFrame("Completed Operations");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Completed Operations", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(new Color(34, 139, 34));
        frame.add(headerLabel, BorderLayout.NORTH);

        String enterpriseType = AuthService.getEnterpriseTypeOfCurrentUser();
        ArrayList<String> operations = AuthService.getCompletedOperations(enterpriseType);

        DefaultListModel<String> operationListModel = new DefaultListModel<>();
        for (String operation : operations) {
            operationListModel.addElement(operation);
        }

        JList<String> operationList = new JList<>(operationListModel);
        operationList.setFont(new Font("Arial", Font.PLAIN, 14));
        operationList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        operationList.setFixedCellHeight(30);

        frame.add(new JScrollPane(operationList), BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setBackground(new Color(70, 130, 180));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> frame.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}