/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui.endUser;

import com.ecofoodconnect.models.DonationRequest;
import com.ecofoodconnect.models.DonationRequestDirectory;
import com.ecofoodconnect.ui.EndUserDashboard;
import java.awt.Color;
import java.awt.Component;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author tanmay
 */
public class ActionButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private JButton button;
    private String label;
    private DonationRequestDirectory directory;
    private JTable table;
    private String loggedInUser;
    private EndUserDashboard dashboard;

    public ActionButtonEditor(JCheckBox checkBox, DefaultTableModel tableModel, DonationRequestDirectory directory, String loggedInUser, EndUserDashboard dashboard) {
        this.directory = directory;
        this.loggedInUser = loggedInUser;
        this.dashboard = dashboard;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> processRequest());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return label;
    }

    private void processRequest() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String requestId = (String) table.getValueAt(selectedRow, 0);
            DonationRequest request = directory.getDonationRequests().stream()
                    .filter(r -> r.getId().equals(requestId))
                    .findFirst()
                    .orElse(null);

            if (request != null) {
                int confirm;
                if ("Request".equals(label)) {
                    // Confirmation dialog for requesting donation
                    confirm = JOptionPane.showConfirmDialog(
                            table,
                            "Are you sure you want to request this donation?",
                            "Confirm Request",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        // Request donation
                        request.setRequestedBy(loggedInUser);
                        request.setStatus("Requested");
                        JOptionPane.showMessageDialog(table, "Donation Requested Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else if ("Confirm Received".equals(label)) {
                    // Confirmation dialog for confirming receipt
                    confirm = JOptionPane.showConfirmDialog(
                            table,
                            "Have you received this donation?",
                            "Confirm Receipt",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        // Confirm receipt
                        request.setStatus("Delivered");
                        JOptionPane.showMessageDialog(table, "Donation Marked as Delivered!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

                // Refresh tabs to reflect updates
                dashboard.refreshTabs();
                fireEditingStopped(); // Stop editing to refresh table
            }
        }
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }
}