package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;


class ButtonTextFieldCell extends AbstractCellEditor implements
        TableCellEditor, ActionListener {

    private JPanel panel;
//    private JTextField t;
    JButton b;

    public ButtonTextFieldCell() {
        this("");
    }

    public ButtonTextFieldCell(String txt) {

        panel = new JPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
//        t = new JTextField(50);
//        t.setText(txt);
        // t.setPreferredSize(new Dimension(50,16));
//        panel.add(t);
        b = new JButton("BLAH LAB");
//        b.setPreferredSize(new Dimension(16, 16));
        b.addActionListener(this);
        panel.add(b);
//        panel.add(Box.createHorizontalGlue());
    }

    @Override
    public Object getCellEditorValue() {
        return this;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table,
            Object value, boolean isSelected, int row, int column) {
        return panel;
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JOptionPane.showConfirmDialog(panel, "you clicked a button",
                    "Info", JOptionPane.CLOSED_OPTION);
        }
    }

    @Override
    public String toString() {
        return "blalaslasdlas;lkjf";
    }

}


