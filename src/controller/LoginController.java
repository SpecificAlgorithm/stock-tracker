package controller; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.User;
import utility.*;
import view.ActionEvent;
import view.HomeView;
import view.LoginView;
public class LoginController  extends IController { 
	 
	 
		private ValidationUtil valUtil; 
	 
		private DatabaseUtil databaseUtil = new DatabaseUtil(); 
	 
	 public void login(ActionEvent event) throws SQLException
	 {
		 boolean logOn = DatabaseUtil.canLogOn(event);
		 if(logOn)
		 {
			 HomeController hCont = new HomeController();
			 User user = new User();
			 user.setUsername(event.username);
			 hCont.switchContext(user);
		 }
		 else
		 {
			 JOptionPane.showMessageDialog(null,"Not Found");
		 }
	 }
	 
	 public void init()
	 {
		 LoginView view = new LoginView();
		 view.init();
	 }
	 private boolean checkIfRemembered()
	 {
		 return databaseUtil.checkIfRemember();
	 }
} 