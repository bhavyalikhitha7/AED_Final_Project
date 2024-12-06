/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui.enterpriseAdmin;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author tanmay
 */
public class MultiSelectDropdown extends JPanel {
    private final JComboBox<String> comboBox;
    private final ArrayList<String> selectedItems;
    private final ArrayList<JCheckBox> checkBoxes;

    public MultiSelectDropdown(String[] items) {
        selectedItems = new ArrayList<>();
        checkBoxes = new ArrayList<>();

        comboBox = new JComboBox<>(new String[]{"Select Regions"});
        comboBox.setEditable(false);
        comboBox.setFocusable(false);

        comboBox.addActionListener(e -> {
            if (comboBox.isPopupVisible()) {
                showMultiSelectPopup();
            }
        });

        setLayout(new BorderLayout());
        add(comboBox, BorderLayout.CENTER);

        for (String item : items) {
            JCheckBox checkBox = new JCheckBox(item);
            checkBoxes.add(checkBox);
        }
    }

    private void showMultiSelectPopup() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        for (JCheckBox checkBox : checkBoxes) {
            panel.add(checkBox);
        }

        int result = JOptionPane.showConfirmDialog(this, panel, "Select Regions", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            selectedItems.clear();
            for (JCheckBox checkBox : checkBoxes) {
                if (checkBox.isSelected()) {
                    selectedItems.add(checkBox.getText());
                }
            }
            updateComboBoxDisplay();
        }
    }

    private void updateComboBoxDisplay() {
        if (selectedItems.isEmpty()) {
            comboBox.setSelectedItem("Select Regions");
        } else if (selectedItems.size() <= 3) {
            comboBox.setSelectedItem(String.join(", ", selectedItems));
        } else {
            String displayText = String.join(", ", selectedItems.subList(0, 3)) + ", ...";
            comboBox.setSelectedItem(displayText);
        }
    }

    public ArrayList<String> getSelectedItems() {
        return selectedItems;
    }

    public void clearSelection() {
        selectedItems.clear();
        for (JCheckBox checkBox : checkBoxes) {
            checkBox.setSelected(false);
        }
        updateComboBoxDisplay();
    }
}