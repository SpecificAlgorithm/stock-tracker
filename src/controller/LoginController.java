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
	 
	 public void login(ActionEvent event, boolean remembered, boolean shouldRemember) throws SQLException
	 {
		 if(!remembered)
		 {
			 boolean logOn = DatabaseUtil.canLogOn(event);
			 if(logOn)
			 {
				 if(shouldRemember)
				 {
					 DatabaseUtil.setRememberedUsername(event.username);
				 }
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
		 else
		 {
<<<<<<< HEAD
			 if(shouldRemember)
			 {
				 
			 }
=======

>>>>>>> origin/master
			 HomeController hCont = new HomeController();
			 User user = new User();
			 user.setUsername(event.username);
			 hCont.switchContext(user);
		 }
		 
		 
		
	 }
	 
	 public void init() throws SQLException
	 {
		 if(!databaseUtil.checkIfRemember())
		 {
			LoginView view = new LoginView();
			view.init(); 
		 }
		 else
		 {
			 ActionEvent event = new ActionEvent();
			 event.username = databaseUtil.getRememberedUsername();
			 this.login(event, true, false);
		 }
		 
	 }
	 private boolean checkIfRemembered()
	 {
		 return databaseUtil.checkIfRemember();
	 }
} 