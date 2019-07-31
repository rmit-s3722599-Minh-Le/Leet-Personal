package view;

import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;

import model.interfaces.GameEngine;
import model.interfaces.Player;

public class ComboPlayer extends DefaultComboBoxModel {
	private ArrayList<String> stringPlayerList = new ArrayList<String>();
	private ArrayList<Player> playerList = new ArrayList<Player>();

	public ComboPlayer(GameEngine engine) {
		engine.getAllPlayers();
	}

}
