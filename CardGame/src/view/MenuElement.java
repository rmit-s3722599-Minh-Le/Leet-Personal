package view;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import controller.MenuItemListener;
import model.interfaces.GameEngine;

public class MenuElement extends JMenu {
	// the content of the menu
	public MenuElement(ViewModel viewModel) {
		super("menu");
		MenuItemListener mil = new MenuItemListener(viewModel);
		JMenuItem addPlayers = new JMenuItem("Add Players");
		this.add(addPlayers);
		addPlayers.addActionListener(mil);
		JMenuItem removePlayers = new JMenuItem("Remove Players");
		this.add(removePlayers);
		removePlayers.addActionListener(mil);

	}

}
