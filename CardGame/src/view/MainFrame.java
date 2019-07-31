package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import model.interfaces.GameEngine;

public class MainFrame extends JFrame {

	private Toolbar toolBar;
	private Statusbar Statusbar;
	private JMenuBar menuBar;
	private MainPanel mainPanel;

	public MainFrame(GameEngine ge, ViewModel viewModel) {
		super("CardGame");
		// bounds to set
		setBounds(800, 800, 700, 700);
		// creating items
		toolBar = new Toolbar(ge, viewModel);
		Statusbar = new Statusbar();
		menuBar = new PullMenu(viewModel);
		mainPanel = new MainPanel(ge, viewModel);
		// toolbar to viewModel (as observer)
		viewModel.addObserver(toolBar);
		viewModel.addObserver(Statusbar);
		// adds the items
		setJMenuBar(menuBar);
		add(toolBar, BorderLayout.NORTH);
		add(Statusbar, BorderLayout.SOUTH);
		add(mainPanel, BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

}
