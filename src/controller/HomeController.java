package controller; 
import utility.*;
import model.*;
import view.*;
public class HomeController  extends IController { 
		private BackgroundService bckgrnSer = null; 
		
		private CommonUtil comUtil = null;
		private SearchStockController ssCont;
		private TopStockController tscCont;
		private UpdateBalanceController ubCont;
		private String userName;
		
		public void switchContext(User user)
		{
			ssCont = new SearchStockController();
			tscCont = new TopStockController();
			System.out.println(user.getUsername());
			setUser(user);
			HomeView view = new HomeView();
			view.start(this);
		}
		
		
	


		public void switchToSearchContext()
		{
			ssCont.switchContext();
		}
	 
		public void switchToTopStockContext()
		{
			tscCont.switchContext();
		}

		public void switchToUpdateBalanceContext()
		{
			ubCont.switchContext();
		}




} 