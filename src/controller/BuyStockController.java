package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

import model.User;

public class BuyStockController {
	
	public boolean buyStock(User user, String ticker,  int numberToBuy, double price)
	{
		utility.DatabaseUtil databaseUtil = new utility.DatabaseUtil();
		if(!databaseUtil.canFundsDedect(user, 10)) return false;
		databaseUtil.buyStock(user, ticker, numberToBuy, price);
		return true;
//		Connection connection = dbconnection();
//		if (numberToBuy > 1) {
//			String Num_Pat = "^[0-9]{1,2}$"; // Reg exp pattern
//			String Input = NumText.getText(); // getting User input
//			int multiple = Integer.parseInt(Input); // change the input
//			utility.CommonUtil temp = new utility.CommonUtil();							// to int
//			if (temp.regexChecker(Num_Pat, Input)) { // check if the
//														// pattern is
//														// right
//				JOptionPane.showMessageDialog(null, "Match"); // send a
//																// message
//																// that
//																// the
//																// pattern
//																// matches
//				try {
//					String query = "INSERT INTO OwnedStock (username,Ticker,numberOwned,spent) VALUES (?,?,?,?)";
//					PreparedStatement pst = connection.prepareStatement(query);
//					String name = "user";
//					pst.setString(1, name);
//					pst.setString(2, stockName);
//					pst.setInt(3, multiple);
//					double spent = price * multiple;
//					pst.setDouble(4, spent);
//					pst.execute();
//					pst.close();
//
//				} catch (Exception e1) {
//					JOptionPane.showMessageDialog(null, "cant insert !!!");
//				}
//			} else
//				JOptionPane.showMessageDialog(null, "Input should be 2-99");
//
//		} 
//		else {
//			try {
//				String query = "INSERT INTO OwnedStock (username,Ticker,numberOwned,spent) VALUES (?,?,?,?)";
//				PreparedStatement pst = connection.prepareStatement(query);
//				String name = "user";
//				pst.setString(1, name);
//				pst.setString(2, stockName);
//				pst.setInt(3, 1);
//				double spent = price * 1;
//				pst.setDouble(4, spent);
//				pst.execute();
//				pst.close();
//
//			} catch (Exception e1) {
//				JOptionPane.showMessageDialog(null, "be Patient !!!");
//			}
//
//		}
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
