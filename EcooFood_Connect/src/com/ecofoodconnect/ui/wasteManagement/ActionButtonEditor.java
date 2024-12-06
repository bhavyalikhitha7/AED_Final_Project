/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui.wasteManagement;

import com.ecofoodconnect.models.DonationRequest;
import com.ecofoodconnect.models.DonationRequestDirectory;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.EventObject;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author tanmay
 */

public class ActionButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private JButton button;
    private String label;
    private DonationRequestDirectory directory;
    private DefaultTableModel tableModel; // Added tableModel for dynamic updates
    private JTable table;

    public ActionButtonEditor(JCheckBox checkBox, DefaultTableModel tableModel, DonationRequestDirectory directory) {
        this.directory = directory;
        this.tableModel = tableModel; // Initialize tableModel
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
                // Current status is dynamically shown as "Pending Disposal"
                int confirm = JOptionPane.showConfirmDialog(table, "Dispose this request?", "Confirm Action", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    // Mark the request as "Disposed" (actual update to the model)
                    request.setStatus("Disposed");
                    directory.updateDonationRequest(request);

                    // Update table row dynamically
                    tableModel.setValueAt("Disposed", selectedRow, 4); // Update Status column
                    tableModel.setValueAt("-", selectedRow, 5); // Disable actions
                }

                fireEditingStopped(); // Stop editing to refresh table
            }
        }
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }
}