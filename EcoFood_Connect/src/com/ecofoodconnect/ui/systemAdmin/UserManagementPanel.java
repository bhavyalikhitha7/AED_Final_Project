/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui.systemAdmin;

import com.ecofoodconnect.models.Person;
import com.ecofoodconnect.services.AuthService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author tanmay
 */
public class UserManagementPanel extends JPanel {
    private JTable userTable;
    private DefaultTableModel tableModel;

    public UserManagementPanel() {
        setLayout(new BorderLayout());

        // Table for displaying users
        String[] columnNames = {"ID", "Name", "Username", "Role"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);

//        // Buttons for CRUD operations
//        JPanel buttonPanel = new JPanel();
//        JButton addUserButton = new JButton("Add User");
//        JButton editUserButton = new JButton("Edit User");
//        JButton deleteUserButton = new JButton("Delete User");
    // Buttons for CRUD operations
JPanel buttonPanel = new JPanel();

JButton addUserButton = new JButton("Add User");
addUserButton.setBackground( new Color(34, 139, 34));
addUserButton.setForeground(Color.WHITE);
addUserButton.setPreferredSize(new Dimension(100, 30)); 
JButton editUserButton = new JButton("Edit User");
editUserButton.setBackground(new Color(70, 130, 180));
editUserButton.setForeground(Color.WHITE);
editUserButton.setPreferredSize(new Dimension(100, 30)); 
JButton deleteUserButton = new JButton("Delete User");
deleteUserButton.setBackground(Color.RED);
deleteUserButton.setForeground(Color.WHITE);
deleteUserButton.setPreferredSize(new Dimension(100, 30)); 

Font buttonFont = new Font("Arial", Font.BOLD, 13); // Font: Arial, Bold, Size: 13
addUserButton.setFont(buttonFont);
editUserButton.setFont(buttonFont);
deleteUserButton.setFont(buttonFont);
addUserButton.setBorder(null); // Remove border
editUserButton.setBorder(null); // Remove border
deleteUserButton.setBorder(null);

// Add buttons to the panel
buttonPanel.add(addUserButton);
buttonPanel.add(editUserButton);
buttonPanel.add(deleteUserButton);

        buttonPanel.add(addUserButton);
        buttonPanel.add(editUserButton);
        buttonPanel.add(deleteUserButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load user data
        loadUserData();

        // Button Listeners
        addUserButton.addActionListener(this::handleAddUser);
        editUserButton.addActionListener(this::handleEditUser);
        deleteUserButton.addActionListener(this::handleDeleteUser);
    }

    private void loadUserData() {
        tableModel.setRowCount(0); // Clear existing data
        ArrayList<Person> users = AuthService.getAllUsers();
        for (Person user : users) {
            tableModel.addRow(new Object[]{user.getId(), user.getName(), user.getUsername(), user.getRole()});
        }
    }

    private void handleAddUser(ActionEvent e) {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        JTextField nameField = new JTextField();
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        JComboBox<String> roleDropdown = new JComboBox<>(new String[]{"SystemAdmin", "EnterpriseAdmin", "RestaurantManager", "FoodBankManager", "LogisticsCoordinator", "QualityInspector", "WasteManagementOperator", "EndUser"});

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Role:"));
        panel.add(roleDropdown);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add User", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String role = (String) roleDropdown.getSelectedItem();

            if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            AuthService.register(new Person(String.valueOf(System.currentTimeMillis()), name, username, password, role));
            loadUserData(); // Refresh table
        }
    }

    private void handleEditUser(ActionEvent e) {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String userId = (String) tableModel.getValueAt(selectedRow, 0);
        Person user = AuthService.getUserById(userId);

        if (user == null) {
            JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        JTextField nameField = new JTextField(user.getName());
        JTextField usernameField = new JTextField(user.getUsername());
        JPasswordField passwordField = new JPasswordField(user.getPassword());
        passwordField.setEditable(false); // Make password non-editable
        JComboBox<String> roleDropdown = new JComboBox<>(new String[]{
                "SystemAdmin", "EnterpriseAdmin", "RestaurantManager",
                "FoodBankManager", "LogisticsCoordinator", "QualityInspector",
                "WasteManagementOperator", "EndUser"
        });
        roleDropdown.setSelectedItem(user.getRole());

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password: (not editable)"));
        panel.add(passwordField);
        panel.add(new JLabel("Role:"));
        panel.add(roleDropdown);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit User", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            user.setName(nameField.getText().trim());
            user.setUsername(usernameField.getText().trim());
            user.setRole((String) roleDropdown.getSelectedItem());

            loadUserData(); // Refresh the user table
        }
    }

    private void handleDeleteUser(ActionEvent e) {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String userId = (String) tableModel.getValueAt(selectedRow, 0);
        AuthService.deleteUser(userId);
        loadUserData(); // Refresh table
    }
}
