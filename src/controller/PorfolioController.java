package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javax.swing.JOptionPane;
import model.Stock;
import view.PorfolioView;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.util.regex.*;

public class PorfolioController extends IController {

	PorfolioView View = new PorfolioView();

	public void sellStock(List<Stock> stocks) {
		// Listeners and handlers for selling Stocks functionality


		// When Selling Stocks
		btnSell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (rdbtnMultiple.isSelected()) {
					String Num_Pat = "^[0-9]{1,2}$"; // Pattern set for integers
														// FIXME : fix it and
														// make more than 1
					String Input = NumOFStocks.getText();
					if (expobj.regexChecker(Num_Pat, Input)) {
						int multiple = Integer.parseInt(Input); // convert to
																// integer INPUT
						// stop
						try {

							// get number of stocks of a specific company out of
							// the DB
							// Subtract and make sure the user have enough
							// update the Number of stocks

							String query = "SELECT COUNT(*) FROM OwnedStocks WHERE Price = ?"; // un--tested
							PreparedStatement pst = connection.prepareStatement(query);
							pst.setString(1, Price.getText());
							int Number = pst.getUpdateCount();
							System.out.println(Number);
							ResultSet rs = pst.executeQuery();
							int count = 0;
							while (rs.next())
								count++;
							if (count >= multiple)
								JOptionPane.showMessageDialog(null, "Selling Proccesed");
							else
								JOptionPane.showMessageDialog(null, "You Dont Have enough Stocks");

							System.out.println(count);
							rs.close();
							pst.close();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "Cant COUNT!!!");
						}

						// end of "if multiple is chosen."

					} else
						JOptionPane.showMessageDialog(null, "Please enter 2 to 99 for Number of Stocks"); // When
																											// the
																											// pattern
																											// does
																											// not
																											// match

				} else if (!rdbtnMultiple.isSelected()) {
					// if multiple is not selected
					// just subtract one from the NumOfStocks Column of the
					// company specified
					// OR delete the the row if one stock FOUND!
					//
					// Steps followed:
					// 1 - type a selection query
					// 2- Pull the number of the stocks for the company
					// 3- Decide If you would update " more than one found CASE"
					// or delete "just one found CASE"

					//
					//   

					int NumOFStocks = 0;
					try {
						// This query is unTESTED
						String query = "SELECT NumOfStocks FROM OwnedStocks WHERE Company = ? AND Price = ?"; // FIXME!!
						PreparedStatement pst = connection.prepareStatement(query); // ERROR
																					// because
																					// the
																					// SQL
																					// not
																					// Linked
																					// to
																					// this
																					// class
																					// yet
						pst.setString(1, Company.getText());
						pst.setString(2, Price.getText());

						ResultSet rs = pst.executeQuery();
						pst.execute();

						NumOFStocks = rs.getInt("NumOfStocks");

						int count = 0;
						while (rs.next()) {

							//  
						}

						rs.close();
						pst.close();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Error !!!");
					}

					if (NumOFStocks > 1) { // This case means the user have more
											// than one
											// stock of the company so we would
											// just decrement

						try {

							String query = "UPDATE OwnedStocks WHERE Company = ? SET NumOfStocks = NumOfStocks - 1";
							PreparedStatement pst = connection.prepareStatement(query);
							pst.setString(1, Company.getText());
							pst.execute();
							pst.close();

						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "Cant Subtract one");
						}

					} else if (NumOFStocks == 1) { // This case means the user
													// just have one stock so we
													// would delete the row

						try {

							String query = "DELETE FROM OwnedStocks WHERE Company = ? AND BoughtPrice = ?AND ";
							// first check if subtract
							PreparedStatement pst = connection.prepareStatement(query);

							pst.setString(1, Company.getText());
							pst.setString(2, Price.getText());

							pst.execute();
							pst.close();

						} catch (Exception e1) {

							JOptionPane.showMessageDialog(null, "CANT DELETE !!!");
						}

					} else { // This case runs when the user us trying to sell
								// stocks he d

						JOptionPane.showMessageDialog(null, "Company not found !!!");
					}

				}

			}
		});

	} // bracket ends the Selling function

	public void buyStock(List<Stock> stocks) {
		// Buying a Stock

		// when cancel button pressed
		btnCancel1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				contentPane.setVisible(false);
				// find buy sell FIXME !!!!!!! FIX ME
			}
		});

		// when buying multiple
		rdbtnMultiple.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnMultiple.isSelected()) { // the condition
					lblNumOfStocks.setVisible(true);
					NumOFStocks.setVisible(true);
				} else if (!rdbtnMultiple.isSelected()) {
					lblNumOfStocks.setVisible(false);
					NumOFStocks.setVisible(false);
				}

			}
		});

		// Buying Stocks
		// Move to Handlers
		btnSell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnMultiple.isSelected()) {
					String Num_Pat = "^[0-9]{1,2}$"; // Reg exp pattern
					String Input = NumOFStocks.getText(); // getting User input
					int multiple = Integer.parseInt(Input); // change the input
															// to int
					if (expobj.regexChecker(Num_Pat, Input)) { // check if the
																// pattern is
																// right
						JOptionPane.showMessageDialog(null, "Match"); // send a
																		// message
																		// that
																		// the
																		// pattern
																		// matches
						try {
							String query = "INSERT INTO OwnedStocks (Company,BoughtPrice,NumOfStocks) VALUES (?,?,?)";
							PreparedStatement pst = connection.prepareStatement(query);
							pst.setString(1, Company.getText());
							pst.setString(2, Price.getText());
							pst.setInt(3, multiple);
							pst.execute();
							pst.close();

						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, "be Patient !!!");
						}
					} else
						JOptionPane.showMessageDialog(null, "Input should be 2-99");

				} else {
					JOptionPane.showMessageDialog(null, "Thank god");
					try {
						String query = "INSERT INTO OwnedStocks (Company,BoughtPrice,NumOfStocks) VALUES (?,?,?)";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, Company.getText());
						pst.setString(2, Price.getText());
						pst.setInt(3, 1);
						pst.execute();
						pst.close();

					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "be Patient !!!");
					}

				}

			}
		});

	}

}