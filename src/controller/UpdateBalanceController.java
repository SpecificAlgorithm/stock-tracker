package controller;

import view.UpdateBalanceView;

public class UpdateBalanceController extends IController {

	public void switchContext() {
		UpdateBalanceView view = new UpdateBalanceView();
		view.switchContext();
	}
	
	public void goBackToHome()
	{
		HomeController hc = new HomeController();
		hc.switchContext(user);
	}

}
