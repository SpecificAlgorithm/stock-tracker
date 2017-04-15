package utility;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import controller.*;
import model.User;
public class BackgroundService  extends Thread  { 
	 
	 
		private User user;
		private HomeController homeController; 
	 
		private StockDetailsController stockDetailsController; 
	 
		private AlertSettingsController alertSettingController; 
	 
	 public BackgroundService(User user)
	 {
		 this.user = user;
		 this.start();
	 }
		public final HomeController getHomeController() {
			return this.homeController;
		} 
		public final void setHomeController(final HomeController some) {
			this.homeController = some;
		} 
	 
		public final StockDetailsController getStockDetailsController() {
			return this.stockDetailsController;
		} 
		public final void setStockDetailsController(final StockDetailsController some) {
			this.stockDetailsController = some;
		} 
	 
		public final AlertSettingsController getAlertSettingsController() {
			return this.alertSettingController;
		} 
		public final void setAlertSettingsController(final AlertSettingsController some) {
			this.alertSettingController = some;
		} 
		public void run()
		{
			this.checkAlerts();
		}
	 public void checkAlerts()
	 {
		 DatabaseUtil util = new DatabaseUtil();
		 while(true)
		 {
			 
			 try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 String[] tickers = util.getAllStockNamesForAlertUser(user);
			 for(int i = 0; i < tickers.length; i++)
			 {
				 String[] messages = util.getAlertMessages(user, tickers[i]);
				 for(int j = 0; j < messages.length; j++)
				 {
					 JOptionPane.showMessageDialog(null, messages[i]);
				 }
			 }
			 
		 }
	 }
} 