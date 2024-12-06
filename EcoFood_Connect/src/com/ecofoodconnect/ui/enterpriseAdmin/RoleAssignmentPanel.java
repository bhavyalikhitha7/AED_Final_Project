/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui.enterpriseAdmin;

import com.ecofoodconnect.models.EnterprisePerson;
import com.ecofoodconnect.models.Person;
import com.ecofoodconnect.services.AuthService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author tanmay
 */

public class RoleAssignmentPanel extends JPanel {
    private JTable userTable;
    private DefaultTableModel tableModel;

    public RoleAssignmentPanel() {
        setLayout(new BorderLayout());

        // Header
        JLabel headerLabel = new JLabel("Role Assignment", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(34, 139, 34));
        add(headerLabel, BorderLayout.NORTH);

        // Table for Users
        String[] columnNames = {"ID", "Name", "Username", "Role"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE); // Zebra stripes
                }
                c.setFont(new Font("Arial", Font.PLAIN, 14));
                return c;
            }
        };
        userTable.setRowHeight(30);
        userTable.setShowGrid(false);
        userTable.setIntercellSpacing(new Dimension(0, 0));
        userTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        userTable.getTableHeader().setBackground(new Color(34, 139, 34));
        userTable.getTableHeader().setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons for Role Management
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        JButton addUserButton = createStyledButton("Add User", new Color(34, 139, 34), "Add a new user to this enterprise", "icons/add.png");
        JButton editUserButton = createStyledButton("Edit User", new Color(70, 130, 180), "Edit the selected user's details", "icons/edit.png");
        JButton deleteUserButton = createStyledButton("Delete User", Color.RED, "Remove the selected user", "icons/delete.png");
        JButton assignRoleButton = createStyledButton("Assign Role", new Color(34, 139, 34), "Assign a role to the selected user", "icons/assign.png");
        JButton unassignRoleButton = createStyledButton("Unassign Role", Color.ORANGE, "Unassign the role from the selected user", "icons/unassign.png");

        // Add Listeners
        addUserButton.addActionListener(this::handleAddUser);
        editUserButton.addActionListener(this::handleEditUser);
        deleteUserButton.addActionListener(this::handleDeleteUser);
        assignRoleButton.addActionListener(this::handleAssignRole);
        unassignRoleButton.addActionListener(this::handleUnassignRole);

        buttonPanel.add(addUserButton);
        buttonPanel.add(editUserButton);
        buttonPanel.add(deleteUserButton);
        buttonPanel.add(assignRoleButton);
        buttonPanel.add(unassignRoleButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load initial data
        loadUserData();
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        return button;
    }

    private void loadUserData() {
        tableModel.setRowCount(0); // Clear existing data

        // Fetch users in the current enterprise
        String enterpriseType = AuthService.getEnterpriseTypeOfCurrentUser();
        ArrayList<Person> users = AuthService.getUsersByEnterpriseType(enterpriseType);

        for (Person user : users) {
            String role = user.getRole() == null ? "No Role" : user.getRole(); // Default to "No Role"
            tableModel.addRow(new Object[]{user.getId(), user.getName(), user.getUsername(), role});
        }
    }

    private void handleAddUser(ActionEvent e) {
        // Form panel for adding user
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField nameField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<String> roleDropdown = new JComboBox<>(getRolesForEnterprise());

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Role:"));
        panel.add(roleDropdown);
        
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);

        for (Component component : panel.getComponents()) {
            if (component instanceof JLabel) {
                component.setFont(new Font("Arial", Font.BOLD, 14));
            } else if (component instanceof JTextField || component instanceof JComboBox) {
                component.setFont(new Font("Arial", Font.PLAIN, 14));
            }
        }

        // Show form dialog
        int result = JOptionPane.showConfirmDialog(this, panel, "Add User", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String role = (String) roleDropdown.getSelectedItem();

            // Validation
            if (name.isEmpty() || username.isEmpty() || password.isEmpty() || role == null) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Generate user ID and assign enterprise type
            String newUserId = UUID.randomUUID().toString();
            String enterpriseType = AuthService.getEnterpriseTypeOfCurrentUser();

            // Create and register the user
            EnterprisePerson newUser = new EnterprisePerson(newUserId, name, username, password, role, enterpriseType);
            boolean isRegistered = AuthService.register(newUser);

            if (isRegistered) {
                loadUserData(); // Refresh user table
                JOptionPane.showMessageDialog(this, "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            }
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

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField nameField = new JTextField(user.getName());
        JTextField usernameField = new JTextField(user.getUsername());
        JLabel passwordLabel = new JLabel("********"); // Masked password
        JComboBox<String> roleDropdown = new JComboBox<>(getRolesForEnterprise());
        roleDropdown.setSelectedItem(user.getRole());

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        panel.add(roleDropdown, gbc);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit User", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String username = usernameField.getText().trim();
            String role = (String) roleDropdown.getSelectedItem();

            if (name.isEmpty() || username.isEmpty() || role == null) {
                JOptionPane.showMessageDialog(this, "All fields except Password are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update user details
            user.setName(name);
            user.setUsername(username);
            user.setRole(role);

            loadUserData(); // Refresh table
            JOptionPane.showMessageDialog(this, "User updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void handleDeleteUser(ActionEvent e) {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String userId = (String) tableModel.getValueAt(selectedRow, 0);
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Delete User", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            AuthService.deleteUser(userId);
            loadUserData();
            JOptionPane.showMessageDialog(this, "User deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleAssignRole(ActionEvent e) {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to assign a role.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String userId = (String) tableModel.getValueAt(selectedRow, 0);
        Person user = AuthService.getUserById(userId);

        JComboBox<String> roleDropdown = new JComboBox<>(getRolesForEnterprise());
        int result = JOptionPane.showConfirmDialog(this, roleDropdown, "Assign Role", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String role = (String) roleDropdown.getSelectedItem();
            if (role == null) {
                JOptionPane.showMessageDialog(this, "Please select a valid role.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            user.setRole(role);
            loadUserData();
            JOptionPane.showMessageDialog(this, "Role assigned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleUnassignRole(ActionEvent e) {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to unassign their role.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String userId = (String) tableModel.getValueAt(selectedRow, 0);
        Person user = AuthService.getUserById(userId);

        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to unassign this role?", "Unassign Role", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            user.setRole(null); // Unassign role
            loadUserData();
            JOptionPane.showMessageDialog(this, "Role unassigned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private String[] getRolesForEnterprise() {
        String enterpriseType = AuthService.getEnterpriseTypeOfCurrentUser();
        return switch (enterpriseType) {
            case "Restaurants" -> new String[]{"EnterpriseAdmin", "RestaurantManager"};
            case "FoodBanks" -> new String[]{"EnterpriseAdmin", "FoodBankManager"};
            case "LogisticsProviders" -> new String[]{"EnterpriseAdmin", "LogisticsCoordinator"};
            case "WasteManagementFirms" -> new String[]{"EnterpriseAdmin", "WasteManagementOperator"};
            default -> new String[]{"EnterpriseAdmin"};
        };
    }
    
    private JButton createStyledButton(String text, Color color, String tooltip, String iconPath) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setToolTipText(tooltip);
        button.setIcon(new ImageIcon(iconPath)); // Add an icon
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        return button;
    }
}