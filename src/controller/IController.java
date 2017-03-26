package controller; 
import view.IView;

import java.util.Stack;

import model.*;
public class IController  { 
	 
	 
	 	IView view = null; 
		IModel model = null;
		static User user = new User();

		
		public static void setUser(User use)
		{
			user.setUsername(use.getUsername());
		}
} 