package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.HistoryController;

public class HistoryView {
	
	JPanel panel;
	JFrame frame;
	JTable table;
	JScrollPane scroll;
	JButton goBackButt;
	HistoryController hcCont;
	public HistoryView(String ticker, HistoryController hc)
	{
		this.hcCont = hc;
		init(ticker);
	}
	
	public void init(String ticker)
	{
		frame = new JFrame("History of " + ticker);
		
		table = new JTable(hcCont.getData(), hcCont.getColumns());
		scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(500, 250));
//		scroll.add(table);
		
		goBackButt = new JButton();
		goBackButt.setText("go back");
		addActionListenerToGoBackButt(goBackButt);
		
		panel = new JPanel();
//		panel.add(goBackButt);
		panel.add(scroll);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
	
	private void addActionListenerToGoBackButt(JButton goBackButt)
	{
		goBackButt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				hcCont.goBackToHome();
				
			}
		});
	}

}
