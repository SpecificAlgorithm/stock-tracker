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
		String[] ret = {"AAPL", "NVDA", "INTC", "XOM", "NKE"};
		return ret;
	}
}
