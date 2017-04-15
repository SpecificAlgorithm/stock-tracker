package utility;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.sun.xml.internal.ws.api.policy.AlternativeSelector;

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
	
	
	public static String[] getAlertMessages(User user, String ticker)
	{
		ArrayList<String> arr = new ArrayList<>();
		YahooClient yClient = new YahooClient();
		Connection connection = dbconnection();
		//------------------------------------------------------------------------------------------------------------------------
		String sql = "SELECT * FROM Alerts WHERE username = ? AND ticker = ? AND type = ? AND  value > startingValue AND ? > value"; //this gets high threasholds.  that is threasholds that were set above the selling price
		//also this statement is only meant for public stock
		PreparedStatement pst;
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, user.getUsername());
			pst.setString(2, ticker);
			pst.setString(3, "public");
			pst.setDouble(4, yClient.getCurrentSellingPrice(ticker));
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				arr.add("Ticker \"" + rs.getString("ticker") + "\" has triggered the alert set for: " + rs.getDouble("value") + " This is a gain");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//-----------------------------------------------------------------------------------------------------------------------------
		//this sql statement will get stocks with threasholds set lower than original.  This is also only meant for public stock.
		sql = "SELECT * FROM Alerts WHERE username = ? AND ticker = ? AND type = ? AND value < startingValue AND ? < value";
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, user.getUsername());
			pst.setString(2, ticker);
			pst.setString(3, "public");
			pst.setDouble(4, yClient.getCurrentSellingPrice(ticker));
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				arr.add("Ticker \"" + rs.getString("ticker") + "\" has triggered the alert set for: " + rs.getDouble("value") + " This is a loss");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//-------------------------------------------------------------------------------------------------------------------------
		
		double numberOfStockOwned = getCountOfStockForUser(user, ticker);
		double price = yClient.getCurrentSellingPrice(ticker);
		double CurrentOwnedValue = price * numberOfStockOwned;
		sql = "SELECT * FROM Alerts WHERE username = ? AND ticker = ? AND type = ? AND tracking = ? AND value > startingValue AND ? > value";
		
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, user.getUsername());
			pst.setString(2, ticker);
			pst.setString(3, "private");
			pst.setString(4, "value");
			pst.setDouble(5, CurrentOwnedValue);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				arr.add("Ticker \"" + rs.getString("ticker") + "\" has triggered the alert set for: " + rs.getDouble("value") + " This is a gain in private stock!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//-----------------------------------------------------------------------------------------------------------------------------
		sql = "SELECT * FROM Alerts WHERE username = ? AND ticker = ? AND type = ? AND tracking = ? AND value < startingValue AND ? < value";
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, user.getUsername());
			pst.setString(2, ticker);
			pst.setString(3, "private");
			pst.setString(4, "value");
			pst.setDouble(5, CurrentOwnedValue);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				arr.add("Ticker \"" + rs.getString("ticker") + "\" has triggered the alert set for: " + rs.getDouble("value") + " This is a loss in your private stock!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//-------------------------------------------------------------------------------------------------------------------------
		
		sql = "SELECT * FROM Alerts WHERE username = ? AND ticker = ? AND type = ? AND tracking = ? AND ? > startingValue";
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, user.getUsername());
			pst.setString(2, ticker);
			pst.setString(3, "private");
			pst.setString(4, "profit");
			pst.setDouble(5, CurrentOwnedValue);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				arr.add("Ticker \"" + rs.getString("ticker") + "\" has triggered the alert set for: " + rs.getDouble("value") + " This stock has become profitable!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		String[] returnArr = new String[arr.size()];
		for(int i = 0; i < arr.size(); i++)
		{
			returnArr[i] = arr.get(i);
		}
		return returnArr;
	}
	
	public static void buyStock(User user, String stockName, int multiple, double price,JFrame frame)
	{
		String name = user.getUsername();
		
		double balance = getCurrentBalance(name);
		Connection connection = dbconnection();
		double spent = getSpentOnStock(user,stockName);
		
				try {
					if(multiple*price < balance){
						
					
					String query = "INSERT INTO OwnedStock (username,Ticker,numberOwned,spent,date) VALUES (?,?,?,?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.setString(1, name);
					pst.setString(2, stockName);
					pst.setInt(3, multiple);
					spent = price * multiple;
					pst.setDouble(4, spent);
					pst.setInt(5, (int) Instant.now().getEpochSecond());
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
	
	public Object[][] getEntriesFor(User user, String ticker)
	{
		ArrayList<Object[]> data = null;
		Connection connection = dbconnection();
		String sql = "Select * FROM OwnedStock WHERE username = ? AND Ticker = ?";
		PreparedStatement pst;
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, user.getUsername());
			pst.setString(2, ticker);
			ResultSet rs = pst.executeQuery();
			data = new ArrayList<>();
			while(rs.next())
			{
				ArrayList<Object> arr = new ArrayList<>();
				int numberOwned = rs.getInt("numberOwned");
				double spent = rs.getDouble("spent");
				int date = rs.getInt("date");
				long RealDate = (long) date * 1000L;
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd::HH:mm:ss");
				  format.setTimeZone(TimeZone.getTimeZone("CST"));
				  String formatted = format.format(RealDate);
				arr.add(ticker);
				arr.add(numberOwned);
				arr.add(spent);
				arr.add(formatted);
				data.add(arr.toArray());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object[][] returnData = new Object[data.size()][];
		for(int i = 0; i < data.size(); i++)
		{
			returnData[i] = data.get(i);
		}
		return returnData;
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
			pst.setDouble(2, spent);
			pst.setString(3, username);
			pst.setString(4, stockname);
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
	
	public static boolean SellStock(User user, String ticker, double CurrentSellingPrice)
	{
		Connection connection = dbconnection();
		int oldestTime = 0;
		
		//first get the oldest time from the database
		String lowSQL = "SELECT MIN(date) AS date FROM OwnedStock WHERE username = ? AND Ticker = ?";
		PreparedStatement oldPST;
		
		try {
			oldPST = connection.prepareStatement(lowSQL);
			oldPST.setString(1, user.getUsername());
			oldPST.setString(2, ticker);
			ResultSet rs = oldPST.executeQuery();
			oldestTime = rs.getInt("date");
			oldPST.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//then decremement the number of oldest stock from the oldest time
		String sql = "UPDATE OwnedStock SET numberOwned = numberOwned - 1 WHERE username = ? AND Ticker = ? AND date = ?";
		
		PreparedStatement pst;
		
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, user.getUsername());
			pst.setString(2, ticker);
			pst.setInt(3, oldestTime);
			pst.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		YahooClient yClient = new YahooClient();
//		double balanceAdd = yClient.getCurrentSellingPrice(ticker); too slow to do it this way
		String balanceSQL = "UPDATE Users SET Balance = Balance + ?";
		try {
			PreparedStatement balancePST = connection.prepareStatement(balanceSQL);
			balancePST.setDouble(1, CurrentSellingPrice);
			balancePST.execute();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		//delete any stocks that we own 0 of.
		String delSQL = "DELETE FROM OwnedStock WHERE numberOwned = 0";
		PreparedStatement delPst;
		
		try {
			delPst = connection.prepareStatement(delSQL);
			delPst.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	public boolean doesAlertAlreadyExist(User user, String ticker, String alertType, String tracking)
	{
		Connection connection = dbconnection();
		String sql = "SELECT * FROM Alerts WHERE username = ? AND ticker = ? AND type = ? AND tracking = ?";
		
		try {
			PreparedStatement pst = connection.prepareStatement(sql);
			pst.setString(1, user.getUsername());
			pst.setString(2, ticker);
			pst.setString(3, alertType);
			pst.setString(4, tracking);
			ResultSet rs = pst.executeQuery();
			boolean returnValue = rs.next();
			pst.close();
			connection.close();
			return returnValue;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public boolean saveAlert(User user, String ticker, String publicORprivate, String alertType, double value, double startingValue)
	{
		if(doesAlertAlreadyExist(user, ticker, publicORprivate, alertType)) return false;
		
		Connection connection = dbconnection();
		String sql = "INSERT INTO Alerts (username, ticker, type, tracking, value, startingValue) VALUES (?,?,?,?,?,?)";
		PreparedStatement pst;
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, user.getUsername());
			pst.setString(2, ticker);
			pst.setString(3, publicORprivate);
			pst.setString(4, alertType);
			pst.setDouble(5, value);
			pst.setDouble(6, startingValue);
			
			pst.execute();
			pst.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
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

public static double getSpentOnStock(User user, String ticker)
{
	Connection connection = dbconnection();
	int count = 0;
	String sql = "SELECT numberOwned, spent FROM OwnedStock WHERE Username = ? AND Ticker = ?";
	PreparedStatement pst;
	try {
		pst = connection.prepareStatement(sql);
		pst.setString(1, user.getUsername());
		pst.setString(2, ticker);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			count += rs.getDouble("spent");
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println(count);
	return count;
}

public static int getCountOfStockForUser(User user, String ticker)
{
	Connection connection = dbconnection();
	int count = 0;
	String sql = "SELECT numberOwned FROM OwnedStock WHERE Username = ? AND Ticker = ?";
	PreparedStatement pst;
	try {
		pst = connection.prepareStatement(sql);
		pst.setString(1, user.getUsername());
		pst.setString(2, ticker);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			count = count +  rs.getInt("numberOwned");
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return count;
}

public static String[] getAllStockNamesForUser(User user)
{
	Connection connection = dbconnection();
	ArrayList<String> arr = new ArrayList<>();
	String sql = "SELECT Ticker FROM OwnedStock WHERE Username = ?";
	PreparedStatement pst;
	try {
		pst = connection.prepareStatement(sql);
		pst.setString(1, user.getUsername());
		ResultSet rs = pst.executeQuery();

		while(rs.next())
		{
			if(!arr.contains(rs.getString("Ticker")))
			arr.add(rs.getString("Ticker"));
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return convertObjectArrToStringArr(arr.toArray());
}

public static String[] getAllStockNamesForAlertUser(User user)
{
	Connection connection = dbconnection();
	ArrayList<String> arr = new ArrayList<>();
	String sql = "SELECT ticker FROM Alerts";
	PreparedStatement pst;
	
	try {
		pst = connection.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			arr.add(rs.getString("ticker"));
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return convertObjectArrToStringArr(arr.toArray());
}

static private String[] convertObjectArrToStringArr(Object[] oArr)
{
	String[] sArr = new String[oArr.length];
	for(int i = 0; i < oArr.length; i++)
	{
		sArr[i] = (String) oArr[i];
	}
	return sArr;
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