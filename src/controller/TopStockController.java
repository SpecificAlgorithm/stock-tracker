package controller;

import view.TopStockView;

public class TopStockController extends IController
{
	public void switchContext()
	{
		TopStockView view = new TopStockView(this);
		
	}
	public void goBackToHome()
	{
		HomeController hc = new HomeController();
		hc.switchContext(user);
	}
	
	public String[] getTopStocks()
	{
		String[] ret = {"MMM", "AXP", "AAPL", "BA", "CAT",
						"CVX", "CSCO", "KO", "DIS", "DD",
						"XOM", "GE", "GS", "HD", "IBM",
						"INTC", "JNJ", "JPM", "MCD", "MRK",
						"MSFT", "NKE", "PFE", "PG", "TRV",
						"UTX", "UNH", "VZ", "V", "WMT"};
		return ret;
	}
}
