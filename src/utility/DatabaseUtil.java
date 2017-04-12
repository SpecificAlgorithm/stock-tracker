package utility;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.LoginController;
import controller.RegistrationController;
import controller.StockDetailsController;
import model.Stock;
import model.User;
import view.ActionEvent;
public class DatabaseUtil {

	private static final String DB_NAME = "StockTracker.sqlite";

	private StockDetailsController stockDetailsController;

	private RegistrationController registrationController;

	private LoginController loginController;
	
	
	public static void buyStock(User user, String stockName, int multiple, double price,JFrame frame)
	{
		String name = user.getUsername();
		
		double balance = getCurrentBalance(name);
		Connection connection = dbconnection();
		double spent = getSpent(name,stockName);
		
				try {
					if(multiple*price < balance){
						
					
					String query = "INSERT INTO OwnedStock (username,Ticker,numberOwned,spent,date) VALUES (?,?,?,?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.setString(1, name);
					pst.setString(2, stockName);
					pst.setInt(3, multiple);
					spent = price * multiple;
					pst.setDouble(4, spent);
					pst.setInt(5, (int) Instant.now().toEpochMilli());
					pst.execute();
					pst.close();
					balance = balance - spent;      // update balancess
					updateBalance(name, balance);
					frame.setVisible(false);
					
					}else  {
						System.out.println(getnameStock(name));
						JOptionPane.showMessageDialog(null, "Your balance does not allow the transaction  ");
					}
					
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "cant insert !!!");
				}
	}
		

	public static boolean canFundsDedect(User user, double value)
	{
		Connection connection = dbconnection();
		String query = "SELECT balance FROM Users u WHERE u.username = ?";
		try {
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, user.getUsername());
			ResultSet result = pst.executeQuery();
			double funds = result.getDouble(1);
			pst.close();
			connection.close();
			if(funds - value < 0) return false;
			else return true;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static void handleLogOff() {
		Connection connection = dbconnection();
		String query = "DELETE FROM RememberMe;"; // If user is remembered (or
		                                          // if not:P) delete any
		                                          // remembered users.
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

	private static byte[] getPasswordHash(String passwordClear) {
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

	public boolean checkIfRemember() {
		String username = getRememberedUsername();
		return username != null;
	}

	public static void setRememberedUsername(String username) {
		Connection connection = dbconnection();
		String query = "INSERT INTO RememberMe (username) VALUES (?)";

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

	public String getRememberedUsername() {
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
			// e.printStackTrace();
			// no user is remembered!
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

	public byte[] getRememberedPassword(String username) {
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
			if (rs.next()) {
				connection.close();
				return false;
			}

			String query = "INSERT INTO Users (username,password) VALUES (?,?)";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, event.username);
			pst.setBytes(2, getPasswordHash(event.password));
			// pst.setDouble(3, 0.0);

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
	
	/**
	 * Update new balance for user in database
	 * 
	 * @param username
	 * @param balance
	 */
	public static void updateBalance(String username, double balance) {
		String sql = "UPDATE Users SET balance = ? WHERE username = ?";
		Connection connection = dbconnection();
		PreparedStatement pst;
		try {
			pst = connection.prepareStatement(sql);
			pst.setDouble(1, balance);
			pst.setString(2, username);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void updateSpent_Owned(String username,String stockname, double spent,double OwnedStocks) {
		String sql = "UPDATE OwnedStock SET numberOwned = ? AND spent = ? WHERE username = ? AND Ticker = ?";
		Connection connection = dbconnection();
		PreparedStatement pst;
		try {
			pst = connection.prepareStatement(sql);
			pst.setDouble(1, OwnedStocks);
			pst.setDouble(1, spent);
			pst.setString(2, username);
			pst.setString(2, stockname);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Get current balance of user
	 * @param username
	 * @return
	 */
	public static double getCurrentBalance(String username) {
		String sql = "SELECT balance from Users WHERE username = ?";
		Connection connection = dbconnection();
		PreparedStatement pst;
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, username);
			ResultSet result = pst.executeQuery();
			return result.getDouble("balance");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Get the ticker symbol of user
	 * @return
	 */
	
	public static ResultSet getTransactions(String username) {
		String sql = "SELECT * FROM OwnedStock WHERE username = ?";
		Connection connection = dbconnection();
		PreparedStatement pst;
		try {
			pst = connection.prepareStatement(sql);
			ResultSet result = pst.executeQuery();
			return result;
		} catch (SQLException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public List<Stock> extractTransaction(ResultSet result) {
		if (result == null) {
			return null;
		}

		List<Stock> stocks = new ArrayList<Stock>();
		try {
			do {
				String ticker = result.getString("Ticker");
				double spent = result.getDouble("spent");
				Stock s = new Stock();
				s.setStockCode(ticker);
				s.setPrice(spent);
				stocks.add(s);
			} while (result.next());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stocks;
	}

	
	
public void createTransactionTable(List<Stock> stocks) {
	for (Stock s : stocks) {
		String ticker = s.getStockCode();
		
		
	}
}
	
	//FIXME TODO
public static int getTransNum(String username) {
		
	    String sql = "select count(*) as numRow from OwnedStock";
		Connection connection = dbconnection();
		PreparedStatement pst;
		try {
			pst = connection.prepareStatement(sql);
			ResultSet result = pst.executeQuery();
//			int rows = result.getFetchSize();
			int row = result.getInt("numRow");
			return row;
		} catch (SQLException e) {
			// Auto-generated catch block
			e.printStackTrace();
			return -1;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				//  Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

/**
 * Get Ticker for user in database
 * 
 * @param username
 */
	
	public static String getnameStock(String username) {
		
		String sql = "SELECT Ticker from OwnedStock WHERE username = ?";
		Connection connection = dbconnection();
		PreparedStatement pst;
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, username);
			ResultSet result = pst.executeQuery();
			return result.getString("Ticker");
		} catch (SQLException e) {
			//  Auto-generated catch block
			e.printStackTrace();
			return "ERRoR";
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				//  Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	public static double getnumberOwned(String username) {
		String sql = "SELECT numberOwned from OwnedStock WHERE username = ?";
		Connection connection = dbconnection();
		PreparedStatement pst;
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, username);
			ResultSet result = pst.executeQuery();
			return result.getInt("numberOwned");
		} catch (SQLException e) {
			//  Auto-generated catch block
			e.printStackTrace();
			return -1;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				//  Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static double getSpent(String username,String stockname) {
		String sql = "SELECT spent from OwnedStock WHERE username = ? AND Ticker = ?";
		Connection connection = dbconnection();
		PreparedStatement pst;
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, username);
			pst.setString(2, stockname);
			ResultSet result = pst.executeQuery();
			return result.getDouble("spent");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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