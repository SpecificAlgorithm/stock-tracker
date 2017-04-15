package controller;


import model.User;
import utility.DatabaseUtil;
import view.HistoryView;


public class HistoryController extends IController
{
	HistoryView view;
	String ticker;
	User user;
	public HistoryController(User user, String ticker)
	{
		this.ticker = ticker;
		this.user = user;
		switchContext(ticker);
	}
	
	public void switchContext(String ticker)
	{
		view = new HistoryView(ticker, this);
	}
	
	public String[] getColumns()
	{
		String[] cols = {"Ticker", "# bought", "$ spent", "date"};
		return cols;
	}
	public Object[][] getData()
	{
		DatabaseUtil util = new DatabaseUtil();

		return util.getEntriesFor(user, ticker);
	}
	
	public void goBackToHome()
	{
		HomeController hc = new HomeController();
		hc.switchContext(user);
	}
}
