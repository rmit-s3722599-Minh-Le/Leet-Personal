package view;

import javax.swing.JMenuBar;

public class PullMenu extends JMenuBar {
	private MenuElement menuElement;

	public PullMenu(ViewModel viewModel) {
		menuElement = new MenuElement(viewModel);
		add(menuElement);

	}
}
