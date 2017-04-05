
 package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import controller.IController;
import utility.CommonUtil;

public class SellStockView implements IView {

	private static final long serialVersionUID = 1L;  
    protected static final boolean DEBUG = false;
	private JFrame frame;
	private JTextField NumTextField;
	Connection connection = null;                     //remove TODO

	
	private void initialize() {
		
		//sconnection = SQL.dbconnection();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 699, 274);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnSell = new JButton("Sell");
		btnSell.setFont(new Font("Tahoma", Font.PLAIN, 35));
		btnSell.setBounds(496, 138, 164, 47);
		frame.getContentPane().add(btnSell);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 31));
		btnCancel.setBounds(321, 138, 164, 49);
		frame.getContentPane().add(btnCancel);
		
		JLabel lblSelectmultipleTo = new JLabel("Select \"multiple\" to specify a number: ");
		lblSelectmultipleTo.setFont(new Font("Tahoma", Font.PLAIN, 33));
		lblSelectmultipleTo.setBounds(22, 29, 611, 29);
		frame.getContentPane().add(lblSelectmultipleTo);
		
		JLabel JLnumOfStocks = new JLabel("Number of stocks :");
		JLnumOfStocks.setFont(new Font("Tahoma", Font.PLAIN, 29));
		JLnumOfStocks.setBounds(212, 85, 247, 29);
		frame.getContentPane().add(JLnumOfStocks);
		JLnumOfStocks.setVisible(false);
		
		NumTextField = new JTextField();
		NumTextField.setFont(new Font("Tahoma", Font.PLAIN, 34));
		NumTextField.setBounds(462, 84, 198, 35);
		frame.getContentPane().add(NumTextField);
		NumTextField.setColumns(10);
		NumTextField.setVisible(false);
		
		
		JRadioButton rdbtnMultiple = new JRadioButton("Multiple");
		rdbtnMultiple.setFont(new Font("Tahoma", Font.PLAIN, 33));
		rdbtnMultiple.setBounds(32, 73, 151, 47);
		frame.getContentPane().add(rdbtnMultiple);
	
	
	
	////////////////////////////////////////////////////////////
	
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		
		
		////////////////////////////////////////////////////////////

	
	
		rdbtnMultiple.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//when multiple checked
				if(rdbtnMultiple.isSelected()){
					JLnumOfStocks.setVisible(true);
					NumTextField.setVisible(true);
				}else if(!rdbtnMultiple.isSelected()){
					JLnumOfStocks.setVisible(false);
					NumTextField.setVisible(false);
				}
			}
		});
	
	
	
	
		////////////////////////////////////////////////////////////
		
		
		btnSell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				//Getting the number Inserted
				int NumberOfStocks = 1;
				String Input = NumTextField.getText();
				NumberOfStocks = Integer.parseInt(Input); 
				
				try{
					
					String name = "user";     // String Text
					String CoName = "Google";
					
					//String updateTableSQL = "UPDATE OwnedStock SET numberOwned = ? WHERE Ticker = ? AND  username = ?";
					//PreparedStatement preparedStatement = connection.prepareStatement(updateTableSQL);
					//preparedStatement.setInt(1, 444);
					//preparedStatement.setString(2,CoName);
					//preparedStatement.setString(3, name);
					// execute insert SQL stetement
					//preparedStatement .executeUpdate();
			        
					String sql = "UPDATE OwnedStock SET numberOwned=? WHERE username = ?";
					 
					PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setInt(1, 4);
					statement.setString(2, name);
                    
					int rowsUpdated = statement.executeUpdate();
					if (rowsUpdated > 0) {
					    System.out.println("An existing user was updated successfully!");
					}
			        
			        
			       statement.close();
			       
				}catch(Exception e1){   JOptionPane.showMessageDialog(null,"Error !!!");     }
				// Copy Finish
				
			}
		});
	
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
}
