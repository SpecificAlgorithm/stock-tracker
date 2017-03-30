package view;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controller.IController;
import controller.SearchStockController;
import utility.YahooClient;

public class SearchStockView implements IView {
	
	private JTable table;
	private SearchStockController ssCont;
	YahooClient client = new YahooClient();
	JTextField field;
	JPanel panel;
	JScrollPane scroll;
	DefaultTableModel tModel;

	
	public void switchContext(SearchStockController ssCont)
	{
		this.ssCont = ssCont;
		frame.getContentPane().removeAll();
		frame.setLayout(new FlowLayout());
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		field = new JTextField();
		GhostText ghostText = new GhostText(field, "Enter stock name or Ticker here...");
		
		JButton button = new JButton();
		button.setText("Search!");
		setActionListenerForButton(button);
		
		JButton button2 = new JButton();
		button2.setText("go back");
		setActionListenerForButton2(button2);
		
//		this.tModel = new DefaultTableModel(ssCont.getData(), this.ssCont.getColumns());
		
	    this.tModel = new DefaultTableModel();
	    this.tModel.setDataVector(ssCont.getData(), this.ssCont.getColumns());

	    table = new JTable(this.tModel);
	    table.getColumn("Buy").setCellRenderer(new ButtonRenderer());
	    table.getColumn("Buy").setCellEditor(
	        new ButtonEditor(new JCheckBox()));
	    JScrollPane scroll = new JScrollPane(table);
//		table = new JTable(this.tModel);
		scroll = new JScrollPane(table);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.75;
		c.weighty = 1;
		panel.add(field, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		panel.add(button, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 2;
		c.weighty = 2;
		panel.add(button2, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 2;
		c.weighty = 2;
		panel.add(scroll, c);
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
	
	
	private void setActionListenerForButton(JButton button)
	{
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					updateTable();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
	}
	
	private void setActionListenerForButton2(JButton button2)
	{
		button2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					ssCont.goBackToHome();
				
				
			}
		});
	}
	
	public void updateTable() throws IOException
	{
		String[][] data = client.searchStock(field.getText());
		this.tModel.setDataVector(data, this.ssCont.getColumns());
		

	    this.tModel.fireTableDataChanged();
	    table.getColumn("Buy").setCellRenderer(new ButtonRenderer());
	    table.getColumn("Buy").setCellEditor(
	        new ButtonEditor(new JCheckBox()));
	}
	
	
	
	
	
	@Override
	public IController getController() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setController(IController some) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	class MyTableModel extends AbstractTableModel {
	    private String[] columnNames = ssCont.getColumns();//same as before...
	    private Object[][] data = ssCont.getData();//same as before...

	    public int getColumnCount() {
	        return columnNames.length;
	    }

	    public int getRowCount() {
	        return data.length;
	    }

	    public String getColumnName(int col) {
	        return columnNames[col];
	    }

	    public Object getValueAt(int row, int col) {
	        return data[row][col];
	    }

	    public Class getColumnClass(int c) {
	        return getValueAt(0, c).getClass();
	    }

	    /*
	     * Don't need to implement this method unless your table's
	     * editable.
	     */
	    public boolean isCellEditable(int row, int col) {
	        //Note that the data/cell address is constant,
	        //no matter where the cell appears onscreen.
	        if (col < 2) {
	            return false;
	        } else {
	            return true;
	        }
	    }

	    /*
	     * Don't need to implement this method unless your table's
	     * data can change.
	     */
	    public void setValueAt(Object value, int row, int col) {
	        data[row][col] = value;
	        fireTableCellUpdated(row, col);
	    }
	}
}
