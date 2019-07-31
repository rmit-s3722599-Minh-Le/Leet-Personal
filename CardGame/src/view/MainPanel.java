package view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import model.interfaces.GameEngine;

public class MainPanel extends JPanel {

	public MainPanel(GameEngine ge, ViewModel view) {
		setLayout(new GridLayout(1, 2));

		CardPanel cardPanel = new CardPanel();
		view.addObserver(cardPanel);

		cardPanel.setOpaque(true);
		cardPanel.setBackground(Color.GRAY);

		RightPanel rightPanel = new RightPanel(ge, view);

		rightPanel.setOpaque(true);
		rightPanel.setBackground(Color.BLACK);

		add(cardPanel);
		add(rightPanel);

	}

}
