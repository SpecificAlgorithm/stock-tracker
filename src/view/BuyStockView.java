
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

import controller.BuyStockController;
import controller.IController;
import model.User;
import utility.CommonUtil;
import utility.YahooClient;

import controller.*;


public class BuyStockView implements IView {
	
	private BuyStockController bsCont = new BuyStockController();
	
	private JPanel contentPane = new JPanel();
	private JTextField NumOFStocks = new JTextField();
	private JButton btnSell = new JButton(); //THis is actually the buy button.  SHould consider a rename here
	private JButton btnCancel = new JButton();
	private JTextField Company = new JTextField();
	private JTextField Price = new JTextField();
	JButton btnSell11 = new JButton();
	JButton btnCancel11 = new JButton();
	JLabel lblIfYouAre = new JLabel();
	JLabel lblNumOfStocks = new JLabel();
	
	
	
	private JPanel contentPane1 = new JPanel();
	private JTextField NumOFStocks1 = new JTextField();
	private JTextField Company1 = new JTextField();
	private JTextField Price1 = new JTextField();
	private JTextField NumText= new JTextField();
	JButton btnSell1 = new JButton();
	JButton btnCancel1 = new JButton();
	JLabel lblIfYouAre1 = new JLabel();
	JLabel lblIfYouAre2 = new JLabel();
	JRadioButton rdbtnMultiple = new JRadioButton();

	
	
	
	
	
	private static final long serialVersionUID = 1L;  
    protected static final boolean DEBUG = false;
    private JTable table;
    YahooClient client = new YahooClient();
    TableModel model;
    JPanel panel = new JPanel(new BorderLayout());
    JButton button = new JButton("Sell");
    JTextField searchTextField = new JTextField(15);
    JButton searchBtn = new JButton("Search");
    JPanel flowLayoutPanel = new JPanel(new FlowLayout());
    TableRowSorter<TableModel> sorter;
    int i = 100000;
    int cost = 5000;
    
    JLabel stockLabel = new JLabel();
	

	public void buyStock(User user, String stockName, double price) 
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 849, 466);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		stockLabel.setText(stockName);
		stockLabel.setVisible(true);
		contentPane.add(stockLabel);


		NumOFStocks1.setFont(new Font("Tahoma", Font.PLAIN, 35));
		NumOFStocks1.setBounds(380, 148, 317, 42);
		
		contentPane1.add(NumOFStocks);
		NumOFStocks1.setColumns(10);
		NumOFStocks1.setVisible(false);
		contentPane.add(NumOFStocks);
		
		
		NumText = new JTextField();
		NumText.setFont(new Font("Tahoma", Font.PLAIN, 35));
		NumText.setBounds(328, 229, 323, 42);
		contentPane.add(NumText);
		NumText.setColumns(10);
		NumText.setVisible(false);

		btnSell = new JButton("Buy");
		btnSell.setFont(new Font("Tahoma", Font.PLAIN, 37));
		btnSell.setBounds(604, 305, 195, 61);
		contentPane.add(btnSell);
		
	    btnCancel1 = new JButton("Cancel");
		btnCancel1.setFont(new Font("Tahoma", Font.PLAIN, 37));
		btnCancel1.setBounds(314, 305, 268, 61);
		contentPane.add(btnCancel1);
		
		
		lblIfYouAre1.setText("To buy multiple stocks of  " + stockName +  " check \"multiple\":");
		lblIfYouAre1.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblIfYouAre1.setBounds(11, 24, 782, 66);
		contentPane.add(lblIfYouAre1);
		
		lblIfYouAre2.setText("\""+ stockName +  "\"  stock  =  "+price+" $");
		lblIfYouAre2.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblIfYouAre2.setBounds(11, 80, 782, 66);
		contentPane.add(lblIfYouAre2);

		rdbtnMultiple = new JRadioButton("multiple");
		rdbtnMultiple.setFont(new Font("Tahoma", Font.PLAIN, 40));
		rdbtnMultiple.setSelected(false);
		rdbtnMultiple.setBounds(49, 162, 317, 53);
		contentPane.add(rdbtnMultiple);

		lblNumOfStocks = new JLabel("Num of Stocks:");
		lblNumOfStocks.setFont(new Font("Tahoma", Font.PLAIN, 34));
		lblNumOfStocks.setBounds(49, 225, 292, 42);
		lblNumOfStocks.setVisible(false);
		contentPane.add(lblNumOfStocks);

		
		//when buying multiple
		rdbtnMultiple.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnMultiple.isSelected()){                                                       // the condition
					lblNumOfStocks.setVisible(true);
					NumText.setVisible(true);
				}else if(!rdbtnMultiple.isSelected()){
					lblNumOfStocks.setVisible(false);
					NumText.setVisible(false);
				}
				
			}
		});
		
	
		// when cancel button pressed
				btnCancel1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						frame.setVisible(false);
					}
				});


		// Buying Stocks
		// Move to Handlers
		btnSell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int multiple  = 1;
				boolean checked = false;
				if (rdbtnMultiple.isSelected()) {
					String Num_Pat = "^[0-9]{1,2}$"; 
					String Input = NumText.getText(); 
					multiple = Integer.parseInt(Input); 
					utility.CommonUtil temp = new utility.CommonUtil();	
					checked = temp.regexChecker(Num_Pat, Input);
				}
				if (checked || multiple == 1 ) {
				{
					bsCont.buyStock(user, stockName, multiple, price,frame);
				}
			}
			else
			{
				  JOptionPane.showMessageDialog(null," Sorry, Input should be '1' to '99' !!!");  
			}

			}
		});
		frame.setVisible(true);

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



