
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
import java.sql.SQLException;

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
import model.User;
import utility.CommonUtil;
import utility.DatabaseUtil;
import utility.YahooClient;

public class SellStockView implements IView {

	private static final long serialVersionUID = 1L;  
    protected static final boolean DEBUG = false;
	private JFrame frame;
	private JTextField NumTextField;
	Connection connection = null;   
	String name;
	DatabaseUtil database = new DatabaseUtil();
    double balance;
    User user;
    int num;
    double getspent = 0.0;
    PorfolioView view;
    
    public SellStockView(String username, PorfolioView pView)
    {
    	this.view = pView;
    	name = username;
    	user = new User(username);
    	balance = database.getCurrentBalance(name);
    	num = database.getCountOfStockForUser(user, name);
    	
    }
	public void initialize(String stockName) {
		
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
		frame.setVisible(true);
	
	
	
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
				YahooClient yClient = new YahooClient();
				double price = yClient.getCurrentSellingPrice(stockName);
				Connection connection = dbconnection();            //  currentBalance    and      numOwned   from DB
				PreparedStatement pst;
				int multiple  = 1;
				boolean checked = false;
				if (rdbtnMultiple.isSelected()) {
					String Num_Pat = "^[0-9]{1,3}$"; 
					String Input = NumTextField.getText(); 
					multiple = Integer.parseInt(Input); 
					utility.CommonUtil temp = new utility.CommonUtil();	
					checked = temp.regexChecker(Num_Pat, Input);
				}
				num = database.getCountOfStockForUser(user, stockName);
				
				if (checked || multiple == 1 ) {                                          // check if either the multiple number is valid or multipe is unpressed

						User user = new User(name);
						getspent = database.getSpentOnStock(user, stockName);                       // uses DB FUNCs
					//    selection ends
					if(multiple <= num){                                              //case when user have enough stocks
						//finish the transaction
						// SELLING IS PROCESSING
//						YahooClient yClient = new YahooClient();
						double sellingPrice = yClient.getCurrentSellingPrice(stockName);
						for(int i = 0; i < multiple; i++)
						{
							database.SellStock(user, stockName, sellingPrice);
						}
						view.refreash();
//					    if(num > 1){                                                     //case when user have more than one stock
//						try {
//							balance = balance + (multiple*price);
//							database.updateBalance(name, balance);
//							
//						    num = num - multiple;
//						    getspent = getspent - (multiple*price);
//						    database.updateSpent_Owned(name, stockName, getspent, num);
//							
//							frame.setVisible(false);
//							
//						} catch (Exception e1) {
//							JOptionPane.showMessageDialog(null, "Cant insert new data to the database in the many option !!!");
//						}
//					    }else if(num == 1)	{                                //case when user have just than one stock
//					    	try{	
//					    		balance = balance + (multiple*price);
//    							database.SellStock(user, stockName);
//    						    
//    					    	}catch(Exception e1){  
//    				                   JOptionPane.showMessageDialog(null," Cant delete the one stock!!!");   
//    			                          }
//					    }else{
//					    	  JOptionPane.showMessageDialog(null," Major ERROR GO GET REFUND!!!");   
//					    }
					    
					}else if (multiple > num){
					       JOptionPane.showMessageDialog(null," Sorry, you do not own that many!!!"); 
					}
					
					
					
					}else{
						JOptionPane.showMessageDialog(null," Please, Input a number 1 to 99!!!"); 
					}
				frame.dispose();	
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

public static Connection dbconnection() {
	try {

		Class.forName("org.sqlite.JDBC");
		
		Connection Call = DriverManager
		        .getConnection("jdbc:sqlite:" + CommonUtil.getAbsolutePathOfFile("db" + File.separator + "StockTracker.sqlite"));
		return Call;

	} catch (Exception e) {

		JOptionPane.showMessageDialog(null, "Disconnected");
		return null;
	}
}
}


