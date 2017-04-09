package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

import model.User;
import javax.swing.*;
public class BuyStockController {
	
	public boolean buyStock(User user, String ticker,  int numberToBuy, double price, JFrame frame)
	{
		utility.DatabaseUtil databaseUtil = new utility.DatabaseUtil();
		if(!databaseUtil.canFundsDedect(user, 10)) return false;
		databaseUtil.buyStock(user, ticker, numberToBuy, price,frame);
		return true;

	}
	public boolean validNumber(String num_pat, String input)
	{
		int multiple = Integer.parseInt(input); // change the input
		utility.CommonUtil temp = new utility.CommonUtil();		
		if (temp.regexChecker(num_pat, input))
		{
			return true;
		}
		return false;
	}

}
