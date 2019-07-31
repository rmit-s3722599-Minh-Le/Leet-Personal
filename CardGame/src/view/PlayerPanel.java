package view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.interfaces.Player;

public class PlayerPanel extends JPanel implements Observer {
	private String details = "<html><div style='text-align: center;'>Selected-Player Panel:<br/>";
	private ViewModel view;
	private Player player;
	private JLabel playerInfo;

	// info of the player selected to the player panel
	public PlayerPanel(ViewModel view) {
		this.view = view;
		if (view.getCurrentPlayer() != null) {
			player = view.getCurrentPlayer();
			details += player.toString() + "<br/>Bet: " + player.getBet();
		}
		details += "</div></html>";
		playerInfo = new JLabel(details);
		add(playerInfo);

	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		remove(playerInfo);
		details = "<html><div style='text-align: center;'>Selected-Player Panel:<br/>";
		if (view.getCurrentPlayer() != null) {
			player = view.getCurrentPlayer();
			details += player.toString() + "<br/>Bet: " + player.getBet()
						+ "<br/>results: " + player.getResult();
		}
		details += "</div></html>";
		playerInfo = new JLabel(details);
		add(playerInfo);
		repaint();
		revalidate();
	}

}
