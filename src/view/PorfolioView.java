package view;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import controller.IController;
import controller.PortfolioController;
import model.Stock;
import utility.CommonUtil;
import utility.YahooClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.sun.javafx.fxml.expression.Expression;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.awt.event.ActionEvent;
import java.util.regex.*;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import utility.YahooClient;
import yahoofinance.YahooFinance;

public class PorfolioView implements IView {
   
	
	// sellStock function Variables
//	private JFrame frame = new JFrame();
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
	//
	//
	
    //buyStock function Variables
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
	//
	//PorfolioController controller;
	
	public PorfolioView() {
    	JTextArea area1 = new JTextArea(1,1);
        area1.setText("Funds:");
        area1.setEditable(false); 
    	JTextArea area = new JTextArea(1,1);
        area.setText(String.valueOf(i));
        area.setEditable(false); 
    	yahoofinance.Stock stock = null;
		try {
			stock = YahooFinance.get("GOOG");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		yahoofinance.Stock stock1 = null;
		try {
			stock1 = YahooFinance.get("ABB");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        String[] columnNames = { "Stock", "Amount ", "Sell"};
        Object[][] data =  {{stock.getName(),(cost+1000)},{stock1.getName(),(cost + 2000)}};
        model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class getColumnClass(int column) {

                switch(column) {
                case 0: 
                case 1: return String.class;
                case 2: return Boolean.class;
                default: return Object.class;

                }
            }
        };

        table = new JTable(model) {
            public boolean isCellEditable(int row, int col) {
            	 if (col < 2) {
     	            return false;
     	        } else {
     	            return true;
     	        }
            }
        };

        sorter = new TableRowSorter<TableModel>(model);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setRowSorter(sorter);
        table.setGridColor(Color.black);
        JScrollPane scrollPane = new JScrollPane(table);
        
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	String label = "Message";
                for(int row = 0; row < table.getRowCount(); ++row) {
                    if((Boolean) table.getValueAt(row, 2) == true) {
                    	int j = (Integer) table.getValueAt(row, 1);
                    	i = i + j;
                    	area.setText(String.valueOf(i));
                        ((DefaultTableModel) model).removeRow(table.convertRowIndexToModel(row));
                        JOptionPane.showMessageDialog(button, label + ": Stock Sold!");
                        row--;
                    }
                }
            }
        });

        searchBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = searchTextField.getText();
                if (text.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(text));
                }
            }
        });
        flowLayoutPanel.add(area1);
        flowLayoutPanel.add(area);
        flowLayoutPanel.add(searchTextField);
        flowLayoutPanel.add(searchBtn);
        panel.add(flowLayoutPanel,BorderLayout.NORTH);
        panel.add(scrollPane , BorderLayout.CENTER);
        panel.add(button, BorderLayout.PAGE_END);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
	}
	
	public void initalize() {
		
		rdbtnMultiple1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (rdbtnMultiple1.isSelected()) {

					// if multiple button pressed
					lblNumOfStocks.setVisible(true);
					NumOFStocks.setVisible(true);

				} else if (!rdbtnMultiple1.isSelected()) {

					lblNumOfStocks.setVisible(false);
					NumOFStocks.setVisible(false);

				}
			}
		});
	}
	
	public static Connection dbconnection() {
		try {

			Class.forName("org.sqlite.JDBC");
			
			Connection Call = DriverManager
			        .getConnection("jdbc:sqlite:" + CommonUtil.getAbsolutePathOfFile("db" + File.separator + "StockTracker.sqlite"));
			return Call;

		}
		catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Disconnected");
			return null;
		}
	}
	    

	
	private TableView portfolio;

	private HomeView homeView;

	public final TableView getPortfolio() {
		return this.portfolio;
	}

	public final void setPortfolio(final TableView somePortfolio) {
		this.portfolio = somePortfolio;
	}

	public final HomeView getHomeView() {
		return this.homeView;
	}

	public final void setHomeView(final HomeView some) {
		this.homeView = some;
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
	

	public void sellStock() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 849, 566);
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		
		NumOFStocks.setFont(new Font("Tahoma", Font.PLAIN, 35));
		NumOFStocks.setBounds(380, 148, 323, 42);
		contentPane.add(NumOFStocks);
		NumOFStocks.setColumns(10);
		NumOFStocks.setVisible(false);

		btnSell11 = new JButton("Sell");
		btnSell11.setFont(new Font("Tahoma", Font.PLAIN, 37));
		btnSell11.setBounds(604, 402, 195, 61);
		contentPane.add(btnSell11);

		btnCancel11 = new JButton("Cancel");
		btnCancel11.setFont(new Font("Tahoma", Font.PLAIN, 37));
		btnCancel11.setBounds(270, 402, 268, 61);
		contentPane.add(btnCancel11);

		lblIfYouAre = new JLabel("If you are selling multiple stocks press \"multiple\":");
		lblIfYouAre.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblIfYouAre.setBounds(11, 24, 782, 66);
		contentPane.add(lblIfYouAre);

		rdbtnMultiple = new JRadioButton("multiple");
		rdbtnMultiple.setFont(new Font("Tahoma", Font.PLAIN, 35));
		rdbtnMultiple.setSelected(false);
		rdbtnMultiple.setBounds(91, 94, 317, 53);
		contentPane.add(rdbtnMultiple);

		lblNumOfStocks = new JLabel("Num of Stocks:");
		lblNumOfStocks.setFont(new Font("Tahoma", Font.PLAIN, 34));
		lblNumOfStocks.setBounds(97, 148, 292, 42);
		lblNumOfStocks.setVisible(false);
		contentPane.add(lblNumOfStocks);

		Company = new JTextField();
		Company.setFont(new Font("Tahoma", Font.PLAIN, 33));
		Company.setBounds(380, 226, 323, 53);
		contentPane.add(Company);
		Company.setColumns(10);

		Price = new JTextField();
		Price.setFont(new Font("Tahoma", Font.PLAIN, 33));
		Price.setColumns(10);
		Price.setBounds(380, 303, 323, 53);
		contentPane.add(Price);
	}

	public void buyStock() 
	{
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 844, 545);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		NumOFStocks1 = new JTextField();
		NumOFStocks1.setFont(new Font("Tahoma", Font.PLAIN, 35));
		NumOFStocks1.setBounds(380, 148, 317, 42);
		contentPane1.add(NumOFStocks);
		NumOFStocks1.setColumns(10);
		NumOFStocks1.setVisible(false);

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
		contentPane1.add(lblIfYouAre1);

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

	}

}
