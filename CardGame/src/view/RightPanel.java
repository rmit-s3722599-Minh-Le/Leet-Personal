package view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import model.interfaces.GameEngine;

public class RightPanel extends JPanel {

	public RightPanel(GameEngine ge, ViewModel view) {
		setLayout(new GridLayout(2, 1));

		PlayerPanel playerPanel = new PlayerPanel(view);
		view.addObserver(playerPanel);

		playerPanel.setOpaque(true);
		playerPanel.setBackground(Color.YELLOW);

		SummaryPanel summaryPanel = new SummaryPanel(ge);

		view.addObserver(summaryPanel);
		summaryPanel.setOpaque(true);
		summaryPanel.setBackground(Color.CYAN);

		// adding both panels
		add(playerPanel);
		add(summaryPanel);

	}

}
