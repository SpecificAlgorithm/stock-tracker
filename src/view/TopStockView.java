package view;

//package components;

/*
* SimpleTableDemo.java requires no other files.
*/

import javax.print.DocFlavor.STRING;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
// Temporary change
public class TopStockView extends JPanel {
  private boolean DEBUG = false;

  public TopStockView(){
      super(new GridLayout(1,0));
      
      /*Stock stock = YahooFinance.get("AAPL");
		String stockname = stock.getName();
		BigDecimal stockprice = stock.getQuote().getPrice();
*/
      Stock stock1 = null;
      Stock stock2 = null;
      Stock stock3 = null;
      Stock stock4 = null;
      Stock stock5 = null;
      
      final String stockSymbols[] = {"AAPL", "NVDA", "INTC", "XOM", "NKE"};

      Object[][] data = new Object[30][30];
      
      try {
			Map<String, Stock> stocks = YahooFinance.get(stockSymbols);
			Iterator iterator = stocks.entrySet().iterator();
			int row = 0;
			int col = 0;
			while (iterator.hasNext()) {
				@SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)iterator.next();
				Stock stock = (Stock) pair.getValue();
				Object[] temp = {stock.getName(), stock.getQuote().getPrice(), null, null};
				for (int i = 0; i < 4; i ++) {
					data[row][i] = temp[i]; 
				}
				row ++;
			}
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
      String[] columnNames = {"Company",
                              "Price ",
                              "Net Gain",
                              "Chart/Link",};

      final JTable table = new JTable(data, columnNames);
      table.setPreferredScrollableViewportSize(new Dimension(800, 100));
      table.setFillsViewportHeight(true);

      if (DEBUG) {
          table.addMouseListener(new MouseAdapter() {
              public void mouseClicked(MouseEvent e) {
                  printDebugData(table);
              }
          });
      }

      //Create the scroll pane and add the table to it.
      JScrollPane scrollPane = new JScrollPane(table);

      //Add the scroll pane to this panel.
      add(scrollPane);
  }

  private void printDebugData(JTable table) {
      int numRows = table.getRowCount();
      int numCols = table.getColumnCount();
      javax.swing.table.TableModel model = table.getModel();

      System.out.println("Value of data: ");
      for (int i=0; i < numRows; i++) {
          System.out.print("    row " + i + ":");
          for (int j=0; j < numCols; j++) {
              System.out.print("  " + model.getValueAt(i, j));
          }
          System.out.println();
      }
      System.out.println("--------------------------");
  }

  /**
   * Create the GUI and show it.  For thread safety,
   * this method should be invoked from the
   * event-dispatching thread.
   */
  public void createAndShowGUI() {
      //Create and set up the window.
      JFrame frame = new JFrame("Top 30 Stock");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      //Create and set up the content pane.
      TopStockView newContentPane = new TopStockView();
      newContentPane.setOpaque(true); //content panes must be opaque
      frame.setContentPane(newContentPane);

      //Display the window.
      frame.pack();
      frame.setVisible(true);
  }
}