package controller;

import view.PorfolioView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javax.swing.JOptionPane;
import model.Stock;
import model.User;
import utility.DatabaseUtil;
import view.PorfolioView;

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

public class PortfolioController extends IController {

	PorfolioView View;

	public void switchContext() {
		View = new PorfolioView();
		View.initalize();	
	}
	
	public void goBackToHome()
	{
		HomeController hc = new HomeController();
		hc.switchContext(user);
	}

	
	public Object[][] getData()
	{
		DatabaseUtil util = new DatabaseUtil();
		String[] stocks = util.getAllStockNamesForUser(user);
		Object[][] data = new Object[stocks.length][];
		for(int i = 0; i < stocks.length; i++)
		{
			double spent = util.getSpentOnStock(user, stocks[i]);
			int numBought = util.getCountOfStockForUser(user, stocks[i]);
			Object[] row = {stocks[i], numBought, spent, stocks[i], "", ""};
			data[i] = row;
		}
		return data;
		
	}
	public Object[] getColumns()
	{
		Object[] columns = {"Stock", "# owned", "$ Spent", "Sell", "History View", "Alert"};
		return columns;
	}
	
	public User getUser() {return user;}
	

}