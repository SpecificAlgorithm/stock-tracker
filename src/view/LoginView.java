package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import controller.IController;
import controller.LoginController;
import controller.RegistrationController;
import utility.*;
import view.ActionEvent.ActionType;

public class LoginView   implements IView { 
	 
	 
		private TextField username; 
		private TextField password; 
		Connection connection = null;
		private JPasswordField passwordField;
		private JTextField textField;
		private JPasswordField NewPW;
		private JPasswordField passwordField_2;
		LoginController loginController;
		RegistrationController registerController;
		
		JRadioButton rdbtnRemeberMe;

		
		public void init()
		{
			loginController = new LoginController();
			registerController = new RegistrationController();
			connection = DatabaseUtil.dbconnection();
//			this.setFrame(new JFrame());
			frame.getContentPane().removeAll();
			frame.setBounds(100, 100, 1046, 696);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(new CardLayout(0, 0));
			frame.setVisible(true);
			JPanel LogInPanel = new JPanel();
			frame.getContentPane().add(LogInPanel, "name_177766443117171");
			LogInPanel.setLayout(null);
			
			JButton btnRegister = new JButton("Sign Up");
			btnRegister.setFont(new Font("Andalus", Font.PLAIN, 53));
			btnRegister.setBackground(new Color(211, 211, 211));                                   
			btnRegister.setForeground(new Color(0, 0, 255));
			btnRegister.setBounds(329, 444, 287, 89);
			LogInPanel.add(btnRegister);
			
			JButton btnLogIn = new JButton("Log In");
			btnLogIn.setForeground(new Color(0, 0, 255));
			btnLogIn.setFont(new Font("Andalus", Font.PLAIN, 53));
			btnLogIn.setBackground(new Color(211, 211, 211));
			btnLogIn.setBounds(45, 444, 211, 89);
			LogInPanel.add(btnLogIn);
			
			JLabel lblUsername = new JLabel("Username");
			lblUsername.setFont(new Font("Georgia", Font.PLAIN, 37));
			lblUsername.setBounds(35, 109, 255, 54);
			LogInPanel.add(lblUsername);
			
			JLabel lblPassword = new JLabel("Password");
			lblPassword.setFont(new Font("Georgia", Font.PLAIN, 37));
			lblPassword.setBounds(40, 241, 250, 49);
			LogInPanel.add(lblPassword);
			
			JTextPane textPane = new JTextPane();
			textPane.setFont(new Font("Sitka Small", Font.PLAIN, 37));
			textPane.setBounds(35, 163, 369, 54);
			LogInPanel.add(textPane);
			
			rdbtnRemeberMe = new JRadioButton("Remember me");
			rdbtnRemeberMe.setFont(new Font("Sitka Small", Font.PLAIN, 31));
			rdbtnRemeberMe.setForeground(new Color(255, 0, 0));
			rdbtnRemeberMe.setBounds(35, 371, 277, 50);
			LogInPanel.add(rdbtnRemeberMe);
			
			
			passwordField = new JPasswordField();
			passwordField.setFont(new Font("Tahoma", Font.PLAIN, 37));
			passwordField.setBounds(35, 297, 369, 54);
			LogInPanel.add(passwordField);
			
			JLabel lblLogo = new JLabel("");
			lblLogo.setFont(new Font("Tahoma", Font.PLAIN, 30));
			Image img = new ImageIcon(this.getClass().getResource("/logo.png")).getImage();
			lblLogo.setIcon(new ImageIcon(img));
			lblLogo.resize(100, 50);
			lblLogo.setBounds(584, 76, 250, 102);
			LogInPanel.add(lblLogo);
			
			JPanel Registeration = new JPanel();
			frame.getContentPane().add(Registeration, "name_177776294875350");
			Registeration.setLayout(null);
			
			JLabel lblCreateNewAccount = new JLabel("Create new account :");
			lblCreateNewAccount.setFont(new Font("Sitka Small", Font.PLAIN, 49));
			lblCreateNewAccount.setBounds(22, 24, 695, 70);
			Registeration.add(lblCreateNewAccount);
			
			textField = new JTextField();
			textField.setFont(new Font("Bookman Old Style", Font.PLAIN, 34));
			textField.setBounds(488, 132, 343, 54);
			Registeration.add(textField);
			textField.setColumns(10);
			
			NewPW = new JPasswordField();
			NewPW.setFont(new Font("Tahoma", Font.PLAIN, 30));
			NewPW.setBounds(488, 227, 343, 53);
			Registeration.add(NewPW);
			
			passwordField_2 = new JPasswordField();
			passwordField_2.setFont(new Font("Tahoma", Font.PLAIN, 30));
			passwordField_2.setBounds(488, 337, 343, 52);
			Registeration.add(passwordField_2);
			
			JLabel lblUsername_1 = new JLabel("Username");
			lblUsername_1.setFont(new Font("Tahoma", Font.PLAIN, 43));
			lblUsername_1.setBounds(48, 132, 380, 58);
			Registeration.add(lblUsername_1);
			
			JLabel lblNewPassword = new JLabel("New password");
			lblNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 43));
			lblNewPassword.setBounds(48, 227, 380, 56);
			Registeration.add(lblNewPassword);
			
			JLabel lblNewLabel = new JLabel("Confirm password");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 41));
			lblNewLabel.setBounds(48, 337, 380, 56);
			Registeration.add(lblNewLabel);
			
			JButton btnCreate = new JButton("Create");
			
			btnCreate.setFont(new Font("Sitka Small", Font.PLAIN, 28));
			btnCreate.setBounds(790, 530, 155, 47);
			Registeration.add(btnCreate);
			
			JButton btnCancel = new JButton("Cancel");
			
			btnCancel.setFont(new Font("Sitka Small", Font.PLAIN, 28));
			btnCancel.setBounds(602, 530, 155, 47);
			Registeration.add(btnCancel);

	        
	        
	        
	        
/******************************************************************************        
*******************************************************************************
****** 	   Handlers                 Handlers                 Handlers   *******
*******************************************************************************
*******************************************************************************/
			
			
			
			
			
			btnLogIn.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent arg0) {
				try{
					view.ActionEvent event = new view.ActionEvent();
					event.action = ActionType.LOGIN;
					event.username = textPane.getText();
					event.password = passwordField.getText();
					loginController.login(event, false, rdbtnRemeberMe.isSelected());
				}catch(Exception e){   JOptionPane.showMessageDialog(null,"Error !!!");     }
				}
			});
	        
	        
	                                                     //     SignUp button Handler
	        btnRegister.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					LogInPanel.setVisible(false);
					Registeration.setVisible(true);
				}
			});


	                                                                //  Registration cancel handler 
	        btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					LogInPanel.setVisible(true);
					Registeration.setVisible(false);
					
				}
			});
	        
	    //REMEMBER ME
	        rdbtnRemeberMe.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					//when multiple checked
					if(rdbtnRemeberMe.isSelected()){
						//set the current password to the text field
						textPane.setText(textPane.getText());
						
					}else if(!rdbtnRemeberMe.isSelected()){
			             //dont set
					}
				}
			});
	                                                 // Connect database and save data Handler                                              
	        btnCreate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					utility.CommonUtil temp = new utility.CommonUtil();	
					view.ActionEvent event = new view.ActionEvent();
					event.username = textField.getText();
					event.password = NewPW.getText();
						if(NewPW.getSelectedText() == passwordField_2.getSelectedText() && textField.getText() != null){
							  if(temp.regexChecker("^[a-z0-9]{8,18}$", NewPW.getText())){
								  if(registerController.register(event)){
					        	 LogInPanel.setVisible(true);
						         Registeration.setVisible(false);
						         event.action = view.ActionEvent.ActionType.REGISTER;
								    }else JOptionPane.showMessageDialog(frame, " ineligible username");
							    }else JOptionPane.showMessageDialog(frame, "password must be 8-18 chars digits and letters");
						 }else JOptionPane.showMessageDialog(frame, "passwords dont match");
					
					
					
				      
			          	}
					
				});
	   
		}
	 
	 
		public final TextField getUsername() {
			return this.username;
		} 
		public final void setUsername(final TextField someUsername) {
			this.username = someUsername;
		} 
	 
		public final TextField getPassword() {
			return this.password;
		} 
		public final void setPassword(final TextField somePassword) {
			this.password = somePassword;
		}
		@Override
		public IController getController() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public void setController(IController some) {
			// TODO Auto-generated method stub
			
		}
	 
} 