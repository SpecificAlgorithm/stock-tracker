
package controller; 

import utility.*;
import view.AlertSettingsView;
import view.HomeView;
public class AlertSettingsController   extends IController { 
		BackgroundService bckgrndServ;
		String ticker;
		boolean isPrivate;
		
		
		public AlertSettingsController() {}
		
		public void switchContext(boolean privateStock, String ticker)
		{
			isPrivate = privateStock;
			this.ticker = ticker;
			AlertSettingsView view = new AlertSettingsView(this);
			view.init(privateStock);
		}
		
		public void goBackHome()
		{
			HomeController hCont = new HomeController();
			hCont.switchContext(user);
		}
		
		public boolean saveAlert(String publicORprivate, String alertType, double value)
		{
			DatabaseUtil util = new DatabaseUtil();
			return util.saveAlert(user, ticker, publicORprivate, alertType, value, getStartingValue(publicORprivate));
			//user, public/private, profit/value, value
		}
		public double getStartingValue(String publicORprivate)
		{
			YahooClient yClient = new YahooClient();
			if(publicORprivate == "public")
			{
				
				return yClient.getCurrentSellingPrice(ticker);
			}
			else
			{
				DatabaseUtil util = new DatabaseUtil();
				return yClient.getCurrentSellingPrice(ticker) * (double) util.getCountOfStockForUser(user, ticker);
			}
		}
} 