package controller; 
import utility.*;


import javax.swing.JOptionPane;

import java.sql.SQLException;

import java.sql.SQLException;


import model.*;
import view.*;
public class HomeController  extends IController { 
		private BackgroundService bckgrnSer = null; 
		
		private CommonUtil comUtil = null;
		private SearchStockController ssCont;
		private TopStockController tscCont;
		private UpdateBalanceController ubCont;
		private PortfolioController pCont;
		private String userName;
		
		public void switchContext(User user)
		{
			// Declaring the controllers for the buttons
			ssCont = new SearchStockController();
			tscCont = new TopStockController();
			ubCont = new UpdateBalanceController();
			pCont = new PortfolioController();
			System.out.println(user.getUsername());
			
			// Basing the session on the specific user
			setUser(user);
			
			// Creating a HomeView instance
			HomeView view = new HomeView();
			
			// Opening the Home view here
			view.start(this);
		}
		private void logOff()
		{
			LoginController lCont = new LoginController();
			try {
				lCont.init();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	


		public void switchToSearchContext()
		{
			ssCont.switchContext();
		}
	 
		public void switchToTopStockContext()
		{
			// Calling the TopStock here
			//JOptionPane.showMessageDialog(null,"Top Stock View Successful!");
			tscCont.switchContext();
		}

		public void switchToUpdateBalanceContext()
		{
			ubCont.switchContext();
		}

		public void switchToPortfolioContext()
		{
			pCont.switchContext();
		}

		public void handleLogOff()
		{
			utility.DatabaseUtil database = new utility.DatabaseUtil();
			database.handleLogOff();
			this.logOff();
		}


} 