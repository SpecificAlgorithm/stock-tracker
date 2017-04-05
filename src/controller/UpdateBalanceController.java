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
	
	/**
	 * Get current balance of current user
	 * @return balance
	 */
	public double getCurrentBalance() {
		double balance = DatabaseUtil.getCurrentBalance(user.getUsername());
		return balance;
	}
	
	/**
	 * Get current balance of user and format as currency (12345 -> $12,345.00)
	 * @return currency-formatted balance
	 */
	public String getCurrentFormattedBalance() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		double balance = getCurrentBalance();
		return balance == -1 ? "" : formatter.format(balance);
	}
}
