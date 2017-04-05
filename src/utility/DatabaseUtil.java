package utility;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import controller.LoginController;
import controller.RegistrationController;
import controller.StockDetailsController;
import view.ActionEvent;

public class DatabaseUtil {
	
	private static final String DB_NAME = "StockTracker.sqlite";

	private StockDetailsController stockDetailsController;

	private RegistrationController registrationController;

	private LoginController loginController;
	
	public static void handleLogOff()
	{
		Connection connection = dbconnection();
		String query = "DELETE FROM RememberMe;"; //If user is remembered (or if not:P) delete any remembered users.
		try {
			PreparedStatement pst = connection.prepareStatement(query);
			pst.execute();
			pst.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}

	public static Connection dbconnection() {
		try {

			Class.forName("org.sqlite.JDBC");
			
			Connection Call = DriverManager
			        .getConnection("jdbc:sqlite:" + CommonUtil.getAbsolutePathOfFile("db" + File.separator + DB_NAME));
			return Call;

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Disconnected");
			return null;
		}
	}
	
	private static byte[] getPasswordHash(String passwordClear)
	{
		MessageDigest m = null;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m.reset();
		m.update(passwordClear.getBytes());
		byte[] digest = m.digest();
		System.out.println(digest.toString());
		return digest;
		
	}
	
	public boolean checkIfRemember()
	{
		String username = getRememberedUsername();
		return username != null;
	}
	
	public void setRememberedUsername(String username)
	{
		Connection connection = dbconnection();
		String query = "INSERT ? INTO RememberMe";
		
		try {
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, username);
			pst.execute();
			pst.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String getRememberedUsername()
	{
		String username = null;
		Connection connection = dbconnection();
		String query = "SELECT * FROM RememberMe";
		ResultSet result = null;
		 try {
			result = connection.prepareStatement(query).executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 

	
		 try {
			 username = result.getString("username");
			 } catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
				 //no user is remembered!
		}
		 
		 try {
			 result.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return username;
	}
	public byte[] getRememberedPassword(String username)
	{
		return null;
	}

	public static boolean canLogOn(ActionEvent event) throws SQLException {
		Connection connection = dbconnection();
		String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
		PreparedStatement pst = connection.prepareStatement(query);
		pst.setString(1, event.username);
		pst.setBytes(2, getPasswordHash(event.password)); // Sq lite

		ResultSet rs = pst.executeQuery();
		int count = 0;
		while (rs.next())
			count++;
		rs.close();
		pst.close();
		connection.close();
		if (count == 1) {
			return true;
		}

		else {
			return false;
		}
	}

	public static boolean registerInDatabase(ActionEvent event) {
		try {
			Connection connection = dbconnection();
			String initialCheck = "Select * FROM Users u WHERE u.username == ?";
			PreparedStatement CheckPst = connection.prepareStatement(initialCheck);
			CheckPst.setString(1, event.username);
			ResultSet rs = CheckPst.executeQuery();
			CheckPst.close();
			if(rs.next())
			{
				connection.close();
				return false;
			}
			
					
			String query = "INSERT INTO Users (username,password) VALUES (?,?)";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, event.username);
			pst.setBytes(2, getPasswordHash(event.password));
//			pst.setDouble(3, 0.0);

			pst.execute();
			pst.close();
			connection.close();
			return true;

		} catch (Exception e1) {

			System.out.println("this error is triggering");

			e1.printStackTrace();

			return false;
		}
	}

	public final StockDetailsController getStockDetailsController() {
		return this.stockDetailsController;
	}

	public final void setStockDetailsController(final StockDetailsController some) {
		this.stockDetailsController = some;
	}

	public final RegistrationController getRegistrationController() {
		return this.registrationController;
	}

	public final void setRegistrationController(final RegistrationController some) {
		this.registrationController = some;
	}

	public final LoginController getLoginController() {
		return this.loginController;
	}

	public final void setLoginController(final LoginController some) {
		this.loginController = some;
	}

}