package controller;

import view.SearchStockView;

public class SearchStockController extends IController{
	
	String[] columns = {"Name", "Ticker", "Current Selling $",
			"Net Gain Today", "Additional Information"};
	
	public void switchContext()
	{
		SearchStockView view = new SearchStockView();
		view.switchContext(this);
	}
	
	public String[] getColumns()
	{
		return columns;
	}
	
	public String[][] getData()
	{
		String[][] data = {{"a", "a", "a", "a", "a"}};
		return data;
	}
	
	public void goBackToHome()
	{
		HomeController hc = new HomeController();
		hc.switchContext(user);
	}

}
