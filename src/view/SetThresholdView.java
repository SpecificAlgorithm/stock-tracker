package view;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;

import controller.SearchStockController;
import model.User;


public class SetThresholdView {
	
	private static SearchStockController bsCont = new SearchStockController();
	
	
	   public static void setThresh(User user, String ticker, double price) {
		   		ImageIcon image = new ImageIcon("img/Threshold.jpg");
		   		JLabel label = new JLabel("", image, JLabel.CENTER);
		   		JPanel panel = new JPanel(new BorderLayout());
		   		panel.add( label, BorderLayout.CENTER );
		   		JButton backBtn = new JButton("Go Back");
				backBtn.setText("Close Alert");
		        JFrame frame = new JFrame("Set Threshold");
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        JPanel flowLayoutPanel = new JPanel(new FlowLayout());
		        flowLayoutPanel.add(backBtn);
		        frame.setPreferredSize(new Dimension(500, 400));
		        frame.pack();
		        panel.add(flowLayoutPanel,BorderLayout.SOUTH);
		        frame.getContentPane().removeAll();
		        frame.getContentPane().add(panel);
		        frame.setVisible(true);
		        
		        
		        backBtn.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	JOptionPane.showMessageDialog(backBtn, "Setting Threshold Cancelled...");
		            	frame.setVisible(false);
		            	bsCont = new SearchStockController();
		            	bsCont.goBackToHome();
		            }
		        });
		    }
		 
		    public static void main(String[] args) {
		    	setThresh(null, null, 0);
		      
		            }

			public void initalize() {
				// TODO Auto-generated method stub
				
			}
		    }
    

