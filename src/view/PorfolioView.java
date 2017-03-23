package view;

import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import controller.IController;
import controller.PorfolioController;
import model.Stock;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.util.regex.*;

public class PorfolioView implements IView {
   
	
	// sellStock function Variables
	private JFrame frame = new JFrame();
	private JPanel contentPane;
	private JTextField NumOFStocks;
	private JButton btnSell;
	private JButton btnCancel;
	private JTextField Company;
	private JTextField Price;
	contentPane = new JPanel();
	NumOFStocks = new JTextField();
	JButton btnSell11;
	JButton btnCancel11;
	JLabel lblIfYouAre;
	JRadioButton rdbtnMultiple1;
	JLabel lblNumOfStocks;
	//
	//
	
    //buyStock function Variables
	private JPanel contentPane1;
	private JTextField NumOFStocks1;
	private JTextField Company1;
	private JTextField Price1;
	JRadioButton rdbtnMultiple;
	JButton btnSell1;
	JButton btnCancel1;
	JLabel lblIfYouAre1;
	JRadioButton rdbtnMultiple1;
	JLabel lblNumOfStocks1;
	//
	//PorfolioController controller;
	
	public PorfolioView() {
		this.controller = new PorfolioController();
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
				controller.buyStock(stocks);
			}
		});
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

	public void sellStock(List<Stock> stocks) {
		
		
		
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

	public void buyStock(List<Stock> stocks) {

		

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

	}

}
