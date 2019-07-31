package view;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;

import model.interfaces.Player;

public class MyRenderer extends JLabel implements ListCellRenderer<Player> {
	// for the JComboBox to get Player's names

	// returns names of players
	@Override
	public Component getListCellRendererComponent(JList<? extends Player> list,
				Player value, int index, boolean isSelected,
				boolean cellHasFocus) {
		setText(value.getPlayerName());
		return this;
	}

}
