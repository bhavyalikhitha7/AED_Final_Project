/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ecofoodconnect.ui.wasteManagement;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
/**
 *
 * @author tanmay
 */

public class ActionButtonRenderer extends JButton implements TableCellRenderer {
    public ActionButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "" : value.toString());
        setBackground(isSelected ? Color.CYAN : new Color(70, 130, 180));
        setForeground(Color.WHITE);
        return this;
    }
}