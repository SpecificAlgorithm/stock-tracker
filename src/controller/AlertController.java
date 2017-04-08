package controller;

import view.AlertView;

public class AlertController extends IController {

		AlertView View;

		public void switchContext2() {
			View = new AlertView();
				
		}
		
		public void goBackToHome()
		{
			HomeController hc = new HomeController();
			hc.switchContext(user);
		}

		

	}


