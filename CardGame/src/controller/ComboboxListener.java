package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import model.interfaces.Player;
import view.ViewModel;

public class ComboboxListener implements ActionListener {

	private ViewModel view;

	public ComboboxListener(ViewModel view) {

		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox box = (JComboBox) e.getSource();
		Player player = (Player) box.getSelectedItem();
		view.currentPlayer(player);
		view.callBustCard(player);
		view.clicked();
	}

}
