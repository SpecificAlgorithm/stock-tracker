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
import utility.YahooClient;


public class BuyStockView implements IView {
	
	private JPanel contentPane = new JPanel();
	private JTextField NumOFStocks = new JTextField();
	private JButton btnSell = new JButton();
	private JButton btnCancel = new JButton();
	private JTextField Company = new JTextField();
	private JTextField Price = new JTextField();
	JButton btnSell11 = new JButton();
	JButton btnCancel11 = new JButton();
	JLabel lblIfYouAre = new JLabel();
//	JRadioButton rdbtnMultiple1;
	JLabel lblNumOfStocks = new JLabel();
	
	
	
	private JPanel contentPane1 = new JPanel();
	private JTextField NumOFStocks1 = new JTextField();
	private JTextField Company1 = new JTextField();
	private JTextField Price1 = new JTextField();
	JRadioButton rdbtnMultiple = new JRadioButton();
	JButton btnSell1 = new JButton();
	JButton btnCancel1 = new JButton();
	JLabel lblIfYouAre1 = new JLabel();
	JRadioButton rdbtnMultiple1 = new JRadioButton();
	JLabel lblNumOfStocks1 = new JLabel();
	
	
	
	
	
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
	

	public void buyStock(String stockName) 
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 844, 545);
		
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

		btnSell1 = new JButton("Buy");
		btnSell1.setFont(new Font("Tahoma", Font.PLAIN, 37));
		btnSell1.setBounds(621, 402, 183, 53);
		contentPane.add(btnSell1);

		btnCancel11 = new JButton("Cancel");

		btnCancel11.setFont(new Font("Tahoma", Font.PLAIN, 37));
		btnCancel11.setBounds(432, 402, 167, 53);
		contentPane.add(btnCancel11);

		lblIfYouAre1 = new JLabel("If you are buying multiple stocks press \"multiple\":");
		lblIfYouAre1.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblIfYouAre1.setBounds(11, 24, 782, 66);
		contentPane.add(lblIfYouAre1);

		rdbtnMultiple1 = new JRadioButton("multiple");
		rdbtnMultiple1.setFont(new Font("Tahoma", Font.PLAIN, 35));
		rdbtnMultiple1.setSelected(false);
		rdbtnMultiple1.setBounds(91, 94, 317, 53);
		contentPane.add(rdbtnMultiple1);

		lblNumOfStocks1 = new JLabel("Num of Stocks:");
		lblNumOfStocks1.setFont(new Font("Tahoma", Font.PLAIN, 34));
		lblNumOfStocks1.setBounds(97, 148, 292, 42);
		lblNumOfStocks1.setVisible(false);
		contentPane.add(lblNumOfStocks1);

		Company = new JTextField();
		Company.setFont(new Font("Tahoma", Font.PLAIN, 32));
		Company.setBounds(390, 214, 307, 42);
		contentPane.add(Company);
		Company.setColumns(10);

		Price = new JTextField();
		Price.setFont(new Font("Tahoma", Font.PLAIN, 34));
		Price.setColumns(10);
		Price.setBounds(390, 272, 307, 42);
		contentPane.add(Price);
		
		
		
		
		btnSell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Connection connection = dbconnection();
				if (rdbtnMultiple.isSelected()) {
					String Num_Pat = "^[0-9]{1,2}$"; // Pattern set for integers
														// FIXME : fix it and
														// make more than 1
					String Input = NumOFStocks.getText();
					utility.CommonUtil temp = new utility.CommonUtil();
					if (temp.regexChecker(Num_Pat, Input)) {
						int multiple = Integer.parseInt(Input); // convert to
																// integer INPUT
						// stop
						try {

							// get number of stocks of a specific company out of
							// the DB
							// Subtract and make sure the user have enough
							// update the Number of stocks

							String query = "SELECT COUNT(*) FROM OwnedStocks WHERE Price = ?"; // un--tested
							PreparedStatement pst = connection.prepareStatement(query);
							pst.setString(1, Price.getText());
							int Number = pst.getUpdateCount();
							System.out.println(Number);
							ResultSet rs = pst.executeQuery();
							int count = 0;
							while (rs.next())
								count++;
							if (count >= multiple)
								JOptionPane.showMessageDialog(null, "Selling Proccesed");
							else
								JOptionPane.showMessageDialog(null, "You Dont Have enough Stocks");

							System.out.println(count);
							rs.close();
							pst.close();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "Cant COUNT!!!");
						}

						// end of "if multiple is chosen."

					} else
						JOptionPane.showMessageDialog(null, "Please enter 2 to 99 for Number of Stocks"); // When
																											// the
																											// pattern
																											// does
																											// not
																											// match

				} else if (!rdbtnMultiple.isSelected()) {
					// if multiple is not selected
					// just subtract one from the NumOfStocks Column of the
					// company specified
					// OR delete the the row if one stock FOUND!
					//
					// Steps followed:
					// 1 - type a selection query
					// 2- Pull the number of the stocks for the company
					// 3- Decide If you would update " more than one found CASE"
					// or delete "just one found CASE"

					//
					//   

					int NumOFStocks = 0;
					try {
						// This query is unTESTED
						String query = "SELECT NumOfStocks FROM OwnedStocks WHERE Company = ? AND Price = ?"; // FIXME!!
						PreparedStatement pst = connection.prepareStatement(query); // ERROR
																					// because
																					// the
																					// SQL
																					// not
																					// Linked
																					// to
																					// this
																					// class
																					// yet
						pst.setString(1, Company.getText());
						pst.setString(2, Price.getText());

						ResultSet rs = pst.executeQuery();
						pst.execute();

						NumOFStocks = rs.getInt("NumOfStocks");

						int count = 0;
						while (rs.next()) {

							//  
						}

						rs.close();
						pst.close();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Error !!!");
					}

					if (NumOFStocks > 1) { // This case means the user have more
											// than one
											// stock of the company so we would
											// just decrement

						try {

							String query = "UPDATE OwnedStocks WHERE Company = ? SET NumOfStocks = NumOfStocks - 1";
							PreparedStatement pst = connection.prepareStatement(query);
							pst.setString(1, Company.getText());
							pst.execute();
							pst.close();

						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "Cant Subtract one");
						}

					} else if (NumOFStocks == 1) { // This case means the user
													// just have one stock so we
													// would delete the row

						try {

							String query = "DELETE FROM OwnedStocks WHERE Company = ? AND BoughtPrice = ?AND ";
							// first check if subtract
							PreparedStatement pst = connection.prepareStatement(query);

							pst.setString(1, Company.getText());
							pst.setString(2, Price.getText());

							pst.execute();
							pst.close();

						} catch (Exception e1) {

							JOptionPane.showMessageDialog(null, "CANT DELETE !!!");
						}

					} else { // This case runs when the user us trying to sell
								// stocks he d

						JOptionPane.showMessageDialog(null, "Company not found !!!");
					}

				}

			}
		});

		// when cancel button pressed
		btnCancel1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				contentPane.setVisible(false);
				// find buy sell FIXME !!!!!!! FIX ME
			}
		});

		// when buying multiple
		rdbtnMultiple.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnMultiple.isSelected()) { // the condition
					lblNumOfStocks.setVisible(true);
					NumOFStocks.setVisible(true);
				} else if (!rdbtnMultiple.isSelected()) {
					lblNumOfStocks.setVisible(false);
					NumOFStocks.setVisible(false);
				}

			}
		});

		// Buying Stocks
		// Move to Handlers
		btnSell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection connection = dbconnection();
				if (rdbtnMultiple.isSelected()) {
					String Num_Pat = "^[0-9]{1,2}$"; // Reg exp pattern
					String Input = NumOFStocks.getText(); // getting User input
					int multiple = Integer.parseInt(Input); // change the input
					utility.CommonUtil temp = new utility.CommonUtil();							// to int
					if (temp.regexChecker(Num_Pat, Input)) { // check if the
																// pattern is
																// right
						JOptionPane.showMessageDialog(null, "Match"); // send a
																		// message
																		// that
																		// the
																		// pattern
																		// matches
						try {
							String query = "INSERT INTO OwnedStocks (Company,BoughtPrice,NumOfStocks) VALUES (?,?,?)";
							PreparedStatement pst = connection.prepareStatement(query);
							pst.setString(1, Company.getText());
							pst.setString(2, Price.getText());
							pst.setInt(3, multiple);
							pst.execute();
							pst.close();

						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "be Patient !!!");
						}
					} else
						JOptionPane.showMessageDialog(null, "Input should be 2-99");

				} else {
					JOptionPane.showMessageDialog(null, "Thank god");
					try {
						String query = "INSERT INTO OwnedStocks (Company,BoughtPrice,NumOfStocks) VALUES (?,?,?)";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, Company.getText());
						pst.setString(2, Price.getText());
						pst.setInt(3, 1);
						pst.execute();
						pst.close();

					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "be Patient !!!");
					}

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
