package controller;

import java.io.IOException;

import utility.YahooClient;
import view.SearchStockView;

public class SearchStockController extends IController{
	
	String[] columns = {"Name", "Ticker", "Current Selling $",
			"Net Gain Today", "Additional Information", "Buy"};
	
	public void switchContext()
	{
		SearchStockView view = new SearchStockView();
		view.switchContext(this);
	}
	
	public String[] getColumns()
	{
		return columns;
	}
	
	public String[][] getData(String searchStr)
	{
		String[][] data = {{"", "", "", "", "", ""}};
		if(searchStr != null)
		{
			YahooClient client = new YahooClient();
			try {
				data = client.searchStock(searchStr);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;
	}
	
	
	public void goBackToHome()
	{
		HomeController hc = new HomeController();
		hc.switchContext(user);
	}


}
