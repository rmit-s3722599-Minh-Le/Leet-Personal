package view;

import java.util.Collection;

import javax.swing.DefaultComboBoxModel;

import model.interfaces.Player;

public class Dcb extends DefaultComboBoxModel {

	public Dcb(Collection<Player> players) {
		for (Player player : players) {
			addElement(player);
		}
	}

}
