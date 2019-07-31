package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.interfaces.PlayingCard;

public class CardPanel extends JPanel implements Observer {

	private JLabel playerCard;
	private JLabel houseCard;
	// playerPanel is true when playerPanel should be modified, else false
	private boolean playerPanel = true;
	private JPanel jHousePanel = new JPanel();
	private JPanel jPlayerPanel = new JPanel();

	public CardPanel() {
		setLayout(new GridLayout(2, 1));
		playerCard = new JLabel();
		houseCard = new JLabel();
		add(jHousePanel);
		add(jPlayerPanel);
		jPlayerPanel.add(playerCard);
		jPlayerPanel.setBackground(Color.GRAY);
		jHousePanel.add(houseCard);
		jHousePanel.setBackground(Color.PINK);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// declearing if playerpanel or housepanel needs to be edited
		if (arg1.equals(MessageEnum.message.player_turn)
					|| arg1.equals(MessageEnum.message.house_turn)) {
			if (arg1.equals(MessageEnum.message.player_turn)) {
				playerPanel = true;
			}
			if (arg1.equals(MessageEnum.message.house_turn)) {
				playerPanel = false;
			}
		}
		// edit player panel
		if (playerPanel) {
			if (arg1 instanceof PlayingCard) {
				playerCard.setText("");
				playerCard.setIcon(
							ImageIconFactory.getImageIcon((PlayingCard) arg1));

				repaint();
				revalidate();
			}
			if (arg1.equals(MessageEnum.message.reset_panel)) {
				playerCard.setText("");
				playerCard.setIcon(null);
				revalidate();
			}
			// edit house panel
		} else {
			if (arg1 instanceof PlayingCard) {
				houseCard.setText("");
				houseCard.setIcon(
							ImageIconFactory.getImageIcon((PlayingCard) arg1));
				repaint();
				revalidate();
			}
			if (arg1.equals(MessageEnum.message.reset_panel)) {
				houseCard.setText("");
				houseCard.setIcon(null);
				revalidate();
			}
		}
	}

}
