package view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;

import controller.ButtonListener;
import controller.ComboboxListener;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.MessageEnum.message;

public class Toolbar extends JToolBar implements Observer {
	private JComboBox<Player> comboPlayer = new JComboBox<Player>();
	private GameEngine ge;
	private Collection<Player> players;
	private Collection<Player> player1;
	private ArrayList<String> players_String;
	private Dcb model;
	private ButtonListener bl;
	private ViewModel view;

	public Toolbar(GameEngine ge, ViewModel view) {
		this.view = view;
		this.ge = ge;
		bl = new ButtonListener(view);
		add(Box.createHorizontalGlue());
		players = ge.getAllPlayers();

		model = new Dcb(players);
		comboPlayer.setModel(model);
		if (!players.isEmpty()) {
			comboPlayer.setRenderer(new MyRenderer());
		}
		add(comboPlayer);

		// buttons are created/listeners added
		JButton deal = new JButton("deal");
		deal.addActionListener(bl);
		add(deal);

		JButton placebet = new JButton("placebet");
		placebet.addActionListener(bl);
		add(placebet);

		comboPlayer.revalidate();
		comboPlayer.addActionListener(new ComboboxListener(view));

		view.currentPlayer((Player) comboPlayer.getSelectedItem());

	}

	public void updateComboPlayer() {

		model = new Dcb(players);
		comboPlayer.setModel(model);
		if (!players.isEmpty()) {
			comboPlayer.setRenderer(new MyRenderer());
			if ((Player) comboPlayer.getSelectedItem() != null) {
				view.currentPlayer((Player) comboPlayer.getSelectedItem());
			}
		}

	}

	// adding players or removing them will update the player combo box
	@Override
	public void update(Observable o, Object arg) {
		if (arg.equals(message.add_player)
					|| arg.equals(message.remove_player)) {
			updateComboPlayer();
		}

	}

}
