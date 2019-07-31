package view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import view.MessageEnum.message;

public class Statusbar extends JPanel implements Observer {
	private JLabel status1;
	private JLabel status2;

	public Statusbar() {
		status1 = new JLabel("Action:", SwingConstants.LEFT);
		status2 = new JLabel("Link Start!");

		add(status1);
		add(status2);
	}

	// updates itself when action occurs
	@Override
	public void update(Observable arg0, Object amessage) {
		if (amessage != null) {
			if (amessage.equals(message.add_player)) {
				status2.setText("A summoner has joined the game");
			}
			if (amessage.equals(message.remove_player)) {
				status2.setText("a summoner has disconnected");
			}
			if (amessage instanceof String) {

				status2.setText((String) amessage);
			}
			repaint();
			revalidate();
		}
	}

}
