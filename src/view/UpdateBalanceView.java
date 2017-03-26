package view;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.IController;

public class UpdateBalanceView implements IView {

	public UpdateBalanceView() {
		// TODO Auto-generated constructor stub
	}
	
	public void switchContext()
	{
		frame.getContentPane().removeAll();
		JPanel panel = new JPanel(new GridLayout(2, 2));
		
		JTextField currentBalanceText = new JTextField("Current balance:");
		currentBalanceText.setEditable(false);
		JTextField currentBalance = new JTextField("$xxx.xx");
		JTextField userText = new JTextField("Enter how much money you would like to add:");
		userText.setEditable(false);
		JTextField userInput = new JTextField();
		
		panel.add(currentBalanceText);
		panel.add(currentBalance);
		panel.add(userText);
		panel.add(userInput);
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		
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
