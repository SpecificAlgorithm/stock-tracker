package view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.HomeController;
import controller.IController;
import controller.SearchStockController;
import controller.UpdateBalanceController;

public class HomeView   implements IView { 
	 
	 
//		private TreeTableView stockListView; idk about this 
	 
		private PorfolioView portfolioView; 
	 
		private TransactionHistoryView transactionHistoryView; 
	 
		private LoginView loginView; 
	 
		private SettingsView settingsView; 
	 
		private StockDetailsView stockDetailsView;  
	 
		private HomeController hCont;
	 
//		public final TreeTableView getStockListView() {
//			return this.stockListView;
//		} 
//		public final void setStockListView(final TreeTableView someStockListView) {
//			this.stockListView = someStockListView;
//		} 
	 
		public final PorfolioView getPortfolioView() {
			return this.portfolioView;
		} 
		public final void setPortfolioView(final PorfolioView some) {
			this.portfolioView = some;
		} 
	 
		public final TransactionHistoryView getTransactionHistoryView() {
			return this.transactionHistoryView;
		} 
		public final void setTransactionHistoryView(final TransactionHistoryView some) {
			this.transactionHistoryView = some;
		} 
	 
		public final LoginView getLoginView() {
			return this.loginView;
		} 
		public final void setLoginView(final LoginView some) {
			this.loginView = some;
		} 
	 
		public final SettingsView getSettingsView() {
			return this.settingsView;
		} 
		public final void setSettingsView(final SettingsView some) {
			this.settingsView = some;
		} 
	 
		public final StockDetailsView getStockDetailsView() {
			return this.stockDetailsView;
		} 
		public final void setStockDetailsView(final StockDetailsView some) {
			this.stockDetailsView = some;
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
		
		public void start(HomeController hCont)
		{
			this.hCont = hCont;
			frame.getContentPane().removeAll();
			JPanel panel = new JPanel();
			
			JButton searchStockButton = new JButton();
			searchStockButton.setText("Search Stock");
			addActionListenerSearch(searchStockButton);
			
			JButton topStockButton = new JButton();
			topStockButton.setText("Top 30 stock");
			addActionListenerTopStock(topStockButton);
			
			JButton updateBalanceButton = new JButton();
			updateBalanceButton.setText("Update balance");
			addActionListenerUpdateBalance(updateBalanceButton);
			
			JButton portfolioButton = new JButton();
			portfolioButton.setText("Portfolio");
			addActionListenerPortfolio(portfolioButton);
			
			
			
			
			
			
			panel.setLayout(new FlowLayout());
			panel.add(searchStockButton);
			panel.add(topStockButton);
			panel.add(updateBalanceButton);
			panel.add(portfolioButton);
			
			frame.add(panel);
			frame.pack();
			frame.setVisible(true);
		}
		
		
		private void addActionListenerSearch(JButton searchStockButton)
		{
			searchStockButton.addActionListener(new ActionListener()
		    {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent action) {
					hCont.switchToSearchContext();
				}
		    });
		}
		
		private void addActionListenerTopStock(JButton topStockButton)
		{
			topStockButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					hCont.switchToTopStockContext();
					
				}
			});
		}
		
		private void addActionListenerUpdateBalance(JButton updateBalanceButton)
		{
			updateBalanceButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					hCont.switchToUpdateBalanceContext();					
				}
			});
		}
		
		private void addActionListenerPortfolio(JButton portfolioButton)
		{
			portfolioButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					hCont.switchToPortfolioContext();
					
				}
			});
		}
	 
} 