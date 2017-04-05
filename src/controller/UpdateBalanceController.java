package controller;

import java.text.NumberFormat;

import utility.DatabaseUtil;

public class UpdateBalanceController extends IController {
	
	NumberFormat formatter = NumberFormat.getCurrencyInstance();
	
	public UpdateBalanceController() {
		
	}
	
	public void goBackToHome()
	{
		HomeController hc = new HomeController();
		hc.switchContext(user);
	}
	
	/**
	 * Update balance in database if user's input is valid
	 * @param value
	 * @return
	 */
	public boolean updateBalanceHandler(String value) {
		double newBalance;
		try {
			newBalance = Double.valueOf(value);
			DatabaseUtil.updateBalance(user.getUsername(), newBalance);
			return true;
		} catch (NumberFormatException e1) { // Wrong balance format
			return false;
		}
	}
	
	public String getCurrentBalance() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		double balance = DatabaseUtil.getCurrentBalance(user.getUsername());
		return balance == -1 ? "" : formatter.format(balance);
	}
}
