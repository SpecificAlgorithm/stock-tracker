package controller;

import view.PorfolioView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javax.swing.JOptionPane;
import model.Stock;
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

public class PorfolioController extends IController {

	PorfolioView View = new PorfolioView();

	public void sellStock(List<Stock> stocks) {
		// Listeners and handlers for selling Stocks functionality


		// When Selling Stocks
		
	} // bracket ends the Selling function

	public void buyStock(List<Stock> stocks) {
		// Buying a Stock


	}

}