package view; 
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.*;
public class AlertSettingsView extends JFrame  implements IView { 
	
	JRadioButton rdbtnAlertWhenProfitable = null;
	JTextField textField;
	 
	 
		private TextField valueThreshold; 
	 
		private TextField combinedValueThreshold; 
	 
		private TextField netProfitThreshold; 
		private AlertSettingsController asCont;
		
		public AlertSettingsView(AlertSettingsController Cont) {
			this.asCont = Cont;
		}
	 
	 
		public final TextField getValueThreshold() {
			return this.valueThreshold;
		} 
		public final void setValueThreshold(final TextField someValueThreshold) {
			this.valueThreshold = someValueThreshold;
		} 
	 
		public final TextField getCombinedValueThreshold() {
			return this.combinedValueThreshold;
		} 
		public final void setCombinedValueThreshold(final TextField someCombinedValueThreshold) {
			this.combinedValueThreshold = someCombinedValueThreshold;
		} 
	 
		public final TextField getNetProfitThreshold() {
			return this.netProfitThreshold;
		} 
		public final void setNetProfitThreshold(final TextField someNetProfitThreshold) {
			this.netProfitThreshold = someNetProfitThreshold;
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
		
		private void setupPrivateStockAlert()
		{
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			JPanel contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			GridBagLayout gbl_contentPane = new GridBagLayout();
			gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
			gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
			contentPane.setLayout(gbl_contentPane);
			
//			JButton btnGoBack = new JButton("Go Back");
//			GridBagConstraints gbc_btnGoBack = new GridBagConstraints();
//			gbc_btnGoBack.insets = new Insets(0, 0, 5, 5);
//			gbc_btnGoBack.gridx = 1;
//			gbc_btnGoBack.gridy = 0;
//			contentPane.add(btnGoBack, gbc_btnGoBack);
			

			
			JButton btnApply = new JButton("Apply");
			GridBagConstraints gbc_btnApply = new GridBagConstraints();
			gbc_btnApply.insets = new Insets(0, 0, 5, 5);
			gbc_btnApply.gridx = 1;
			gbc_btnApply.gridy = 1;
			applyAction(btnApply, "private");
			contentPane.add(btnApply, gbc_btnApply);
			
			JLabel lblEnterThreasholdValue = new JLabel("Enter Threashold value: ");
			GridBagConstraints gbc_lblEnterThreasholdValue = new GridBagConstraints();
			gbc_lblEnterThreasholdValue.gridwidth = 2;
			gbc_lblEnterThreasholdValue.insets = new Insets(0, 0, 0, 5);
			gbc_lblEnterThreasholdValue.gridx = 1;
			gbc_lblEnterThreasholdValue.gridy = 6;
			contentPane.add(lblEnterThreasholdValue, gbc_lblEnterThreasholdValue);
			
			
			textField = new JTextField();
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.gridwidth = 2;
			gbc_textField.insets = new Insets(0, 0, 0, 5);
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 4;
			gbc_textField.gridy = 6;
			contentPane.add(textField, gbc_textField);
			textField.setColumns(10);
			setVisible(true);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			rdbtnAlertWhenProfitable = new JRadioButton("Alert when Profitable");
			JRadioButton rdbtnValueThreashold = new JRadioButton("$ value threashold");
			GridBagConstraints gbc_rdbtnAlertWhenProfitable = new GridBagConstraints();
			gbc_rdbtnAlertWhenProfitable.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnAlertWhenProfitable.gridx = 5;
			gbc_rdbtnAlertWhenProfitable.gridy = 1;
			rdbtnAlertWhenProfitable.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					rdbtnValueThreashold.setSelected(false);
					textField.setEnabled(false);
				}
			});
			contentPane.add(rdbtnAlertWhenProfitable, gbc_rdbtnAlertWhenProfitable);
			
			
			
			GridBagConstraints gbc_rdbtnValueThreashold = new GridBagConstraints();
			gbc_rdbtnValueThreashold.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnValueThreashold.gridx = 5;
			gbc_rdbtnValueThreashold.gridy = 0;
			rdbtnValueThreashold.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					textField.setEnabled(true);
					rdbtnAlertWhenProfitable.setSelected(false);
				}
			});
			contentPane.add(rdbtnValueThreashold, gbc_rdbtnValueThreashold);

			

		}
		public void init(boolean privateStock) {
			if(privateStock)
			{
				
				setupPrivateStockAlert();
			}
			else
			{
				setupPublicStock();
			}
			
		}
		
		private void setupPublicStock()
		{
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			JPanel contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			GridBagLayout gbl_contentPane = new GridBagLayout();
			gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
			gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
			contentPane.setLayout(gbl_contentPane);
			
			
			JButton btnApply = new JButton("Apply");
			GridBagConstraints gbc_btnApply = new GridBagConstraints();
			gbc_btnApply.insets = new Insets(0, 0, 5, 5);
			gbc_btnApply.gridx = 1;
			gbc_btnApply.gridy = 1;
			applyAction(btnApply, "public");
			contentPane.add(btnApply, gbc_btnApply);
			
			JLabel lblEnterThreasholdValue = new JLabel("Enter Threashold value: ");
			GridBagConstraints gbc_lblEnterThreasholdValue = new GridBagConstraints();
			gbc_lblEnterThreasholdValue.gridwidth = 2;
			gbc_lblEnterThreasholdValue.insets = new Insets(0, 0, 0, 5);
			gbc_lblEnterThreasholdValue.gridx = 1;
			gbc_lblEnterThreasholdValue.gridy = 6;
			contentPane.add(lblEnterThreasholdValue, gbc_lblEnterThreasholdValue);
			
			textField = new JTextField();
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.gridwidth = 2;
			gbc_textField.insets = new Insets(0, 0, 0, 5);
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 4;
			gbc_textField.gridy = 6;
			contentPane.add(textField, gbc_textField);
			textField.setColumns(10);
			
			setVisible(true);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		
		
		private void applyAction(JButton applyButton, String publicORprivate)
		{
			applyButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String alertType = "value";
					double value = 0.0;
					if(rdbtnAlertWhenProfitable != null && rdbtnAlertWhenProfitable.isSelected()) 
					{
						alertType = "profit";
					}
					if(alertType == "value")
					{
						value =  Double.parseDouble(textField.getText());
					}
					try
					{
							
						boolean couldSave = asCont.saveAlert(publicORprivate, alertType, value);
						if(couldSave) JOptionPane.showMessageDialog(frame, "Save alert!");
						else JOptionPane.showMessageDialog(frame, "This type of alert already exists");
					}
					catch (Exception e1)
					{
						JOptionPane.showMessageDialog(frame, "Must enter a real number");
					}
						

					
				}
			});
		}


} 