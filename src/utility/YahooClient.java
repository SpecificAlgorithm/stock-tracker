package utility;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import model.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class YahooClient {
	
	String[] columns = {"Name", "Ticker", "Current Selling $",
			"Net Gain Today", "Additional Information"};
	public String[][] searchStock(String searchStr) throws IOException
	{
		yahoofinance.Stock stock = getCurrentStockInfo(searchStr);
		if(stock.getName() == null)
		{
			return null;
		}

		double netGain = getNetGainFromYesterday(stock);
		
		//TODO: need to add meaningful website.
		String[][] data = {{stock.getName(), stock.getSymbol().toUpperCase(), stock.getQuote(true).getPrice().toString(), String.valueOf(netGain), getURL(searchStr)}};
		
		return data;
	}
	public double getCurrentSellingPrice(String ticker)
	{
		yahoofinance.Stock stock = getCurrentStockInfo(ticker);
		try {
			return stock.getQuote(true).getPrice().doubleValue();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (Double) null;
	}
	
	public String getURL(String stockName)
	{
		
		String startingPoint = "http://finance.yahoo.com/quote/";
		return startingPoint + stockName;
	}
	
	
	
	
	public double getNetGainFromYesterday(yahoofinance.Stock stock) throws IOException
	{
//		HistoricalQuote quote = getPreviousQuote(stock, 1);
//		HistoricalQuote recent = getPreviousQuote(stock, 1);
//		double yesterdaysPrice = recent.getClose().doubleValue();
//		double todaysPrice = stock.getQuote(true).getPrice().doubleValue();
//		double netGain = todaysPrice - yesterdaysPrice;
//		return netGain;
		return stock.getQuote(true).getPrice().doubleValue() - stock.getQuote().getPreviousClose().doubleValue();
	}
	private HistoricalQuote getPreviousQuote(yahoofinance.Stock stock, int prevDays) throws IOException
	{
		
		
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.add(Calendar.YEAR, -1); // from 1 year ago
		 
		yahoofinance.Stock google = YahooFinance.get("YHOO");
		List<HistoricalQuote> temp = google.getHistory(from);
		List<HistoricalQuote> googleHistQuotes = google.getHistory(from, to, Interval.WEEKLY);
//		return google.getQuote(true);
		
		return googleHistQuotes.get(1);
		
		
//		from = java.util.Calendar.getInstance();
//		to = java.util.Calendar.getInstance();
//		from.add(java.util.Calendar.DAY_OF_YEAR, -20); //this gets the last 20 days but we only need the last recorded day
//														//I ran into the issue of the stock market not running on weekends:).
//														//they might close on holidays too, so the 20 days *should* cover it.
//		
//		List<HistoricalQuote> hist = stock.getHistory();
//		System.out.println(hist.get(1).getDate());
//		HistoricalQuote recent = hist.get(1);//prevDays - 1);
//		return recent;
	}
	private yahoofinance.Stock getCurrentStockInfo(String stockName)
	{
		yahoofinance.Stock stock = null;
		try {
			stock = YahooFinance.get(stockName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stock;
	}
	
}
