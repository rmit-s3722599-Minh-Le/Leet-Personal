package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.MenuEvent;

import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.Toolbar;
import view.ViewModel;

public class MenuItemListener implements ActionListener {
	private ViewModel viewModel;
	private int id = 5;

	public MenuItemListener(ViewModel viewModel) {

		this.viewModel = viewModel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// select add player
		if (e.getActionCommand().equals("Add Players")) {
			JTextField field1 = new JTextField();
			JTextField field2 = new JTextField();

			Object[] fields = { "Player Name", field1, "Points", field2 };
			// adding key listener to point textbox to restrict user to insert
			// anything other than num
			FieldKeyListener kl = new FieldKeyListener();
			field2.addKeyListener(kl);

			JOptionPane.showConfirmDialog(null, fields, "Add Player",
						JOptionPane.OK_CANCEL_OPTION);
			String input = field1.getText();
			String spoints = field2.getText();

			// restricts if user enters nothing
			if (!spoints.equals("")) {
				int points = Integer.valueOf(spoints);
				viewModel.addPlayer(new SimplePlayer(String.valueOf(id), input,
							points));

				id++;

			}
		}
		// select remove player
		if (e.getActionCommand().equals("Remove Players")) {
			String id = JOptionPane.showInputDialog("Remove Player's id");

			viewModel.removePlayer(id);

		}

	}

}
