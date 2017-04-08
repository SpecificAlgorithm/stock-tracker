package view;

import java.awt.*;
import javax.swing.*;
import controller.PortfolioController;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class AlertView {
	private static PortfolioController pcCont;
	    static void Alert() {
	   //String IMG_PATH = "file:///C:/Users/jahre/git/ridulousstock-tracker/img/alert.jpeg";
	    
	    	   ImageIcon image = new ImageIcon("img/alert.jpeg");
	    	   JLabel label = new JLabel("", image, JLabel.CENTER);
	    	   JPanel panel = new JPanel(new BorderLayout());
	    	   panel.add( label, BorderLayout.CENTER );
	    	
	    	JButton backBtn = new JButton("Go Back");
			backBtn.setText("Close Alert");
	        JFrame frame = new JFrame("Alert");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        JPanel flowLayoutPanel = new JPanel(new FlowLayout());
	        flowLayoutPanel.add(backBtn);
	        frame.setPreferredSize(new Dimension(500, 300));
	        frame.pack();
	        JTextArea area1 = new JTextArea(1,1);
	        area1.setText("Your Threshold has be reached for:");
	        area1.setEditable(false); 
	    	JTextArea area = new JTextArea(1,2);
	        area.setText("ABB");
	        area.setEditable(false);
	        JTextArea area2 = new JTextArea(1,3);
	        area2.setText("Threshold is: ");
	        area2.setEditable(false);
	        JTextArea area3 = new JTextArea(1,4);
	        area3.setText("$15");
	        area3.setEditable(false);
	        flowLayoutPanel.add(area1);
	        flowLayoutPanel.add(area);
	        flowLayoutPanel.add(area2);
	        flowLayoutPanel.add(area3);
	        panel.add(flowLayoutPanel,BorderLayout.SOUTH);
	        frame.getContentPane().removeAll();
	        frame.getContentPane().add(panel);
	        frame.setVisible(true);
	        
	        
	        backBtn.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	JOptionPane.showMessageDialog(backBtn, "Alert Closed...");
	            	frame.setVisible(false);
	            	pcCont = new PortfolioController();
	            	pcCont.goBackToHome();
	            }
	        });
	    }
	 
	    public static void main(String[] args) {
	     Alert();
	      
	            }

		public void initalize() {
			// TODO Auto-generated method stub
			
		}
	    }
	
