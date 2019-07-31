package view;

import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.interfaces.GameEngine;
import model.interfaces.Player;

public class SummaryPanel extends JPanel implements Observer {
	private Collection<Player> players;
	private String stringSummary = "<html><div style='text-align: center;'>Summary Panel:<br/>";
	private GameEngine ge;
	private JLabel summary;

	public SummaryPanel(GameEngine ge) {
		players = ge.getAllPlayers();
		// condition when the player list is not empty
		if (!players.isEmpty()) {
			for (Player player : players) {

				stringSummary += player.toString() + "<br/>";
			}
		}
		stringSummary += "</div></html>";

		summary = new JLabel(stringSummary);
		add(summary);
		summary.revalidate();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		remove(summary);
		stringSummary = "<html><div style='text-align: center;'>Summary Panel:<br/>";
		if (!players.isEmpty()) {
			for (Player player : players) {

				stringSummary += player.toString() + "<br/>";
			}
		}
		stringSummary += "</div></html>";
		summary = new JLabel(stringSummary);
		add(summary);
		repaint();
		revalidate();
	}

}
