package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.UpdateBalanceController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpdateBalanceView {

	private final JButton btnSaveChanges = new JButton("Save Changes");
	private final JPanel mainPanel = new JPanel();
	private UpdateBalanceController controller;

	/**
	 * Create the application.
	 */
	public UpdateBalanceView() {
		controller = new UpdateBalanceController();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		
		JFrame frame = new JFrame();
		frame.getContentPane().setPreferredSize(new Dimension(750, 80));
		frame.setResizable(false);
		frame.pack();
		JPanel panel = new JPanel(new GridLayout(2, 2));
		frame.getContentPane().add(mainPanel, BorderLayout.NORTH);
		mainPanel.add(panel);
		
		JLabel currentBalanceText = new JLabel("Current balance:");
		
		// Get current balance in database in display in GUI
		JTextField currentBalance = new JTextField(controller.getCurrentBalance());
		currentBalance.setEditable(false);
		
		JLabel userText = new JLabel("Enter how much money you would like to add:");
		JTextField userInput = new JTextField();
		
		panel.add(currentBalanceText);
		panel.add(currentBalance);
		panel.add(userText);
		panel.add(userInput);
		btnSaveChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isUpdatedBalanceSuccessful = controller.updateBalanceHandler(userInput.getText());
				JOptionPane optionPane;
				if (isUpdatedBalanceSuccessful) {
					optionPane = new JOptionPane("Updated balance successfully!",JOptionPane.INFORMATION_MESSAGE);
					// Display new balance to text field
					currentBalance.setText(controller.getCurrentBalance());
					userInput.setText("");
				} else {
					optionPane = new JOptionPane("Balance should include only numerical characters!",JOptionPane.ERROR_MESSAGE);
				}
				JDialog dialog = optionPane.createDialog("");
				dialog.setAlwaysOnTop(true); // to show top of all other application
				dialog.setVisible(true);
			}
		});
		mainPanel.add(btnSaveChanges);
		frame.setVisible(true);
	}

}
