package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;

public class ViewModel extends Observable {
	// the Arraylist of observers
	private ArrayList<Observer> users = new ArrayList<Observer>();
	private GameEngine ge;
	// player currently selected
	private Player currentPlayer;
	// keeps track of players haven't dealt, but is cleared when every player is
	// dealt
	private LinkedList<Player> haventDealtAllPlayers = new LinkedList<Player>();
	// keeping track of the last card dealt
	private Map<Player, PlayingCard> playerBust = new HashMap<Player, PlayingCard>();
	// the player that is dealt
	private Player dealingPlayer;
	// keeps track of increasing score for status to read
	private int score;
	// to notify the status
	private String notif;

	// adds the gameEngine parameter
	public ViewModel(GameEngine ge) {
		this.ge = ge;
	}

	// adds observers
	public void addObservers(Observer o) {
		users.add(o);
	}

	// removes observers when needed
	public void removeObservers(Observer o) {
		users.remove(o);
	}

	// addPlayer button calls this
	public void addPlayer(Player player) {
		ge.addPlayer(player);
		setChanged();
		notifyObservers(MessageEnum.message.add_player);
	}

	// removePlayer button calls this
	public void removePlayer(String id) {
		ge.removePlayer(ge.getPlayer(id));
		setChanged();
		notifyObservers(MessageEnum.message.remove_player);
	}

	// gets the current player that is selected in the player-tool-bar
	public void currentPlayer(Player player) {
		currentPlayer = player;
	}

	// calls when bet is placed
	public void placeBet(int bet) {
		score = 0;
		setChanged();
		if (currentPlayer != null) {
			if (currentPlayer.getBet() == 0 && currentPlayer.placeBet(bet)) {
				reset();
				notif = currentPlayer.getPlayerName() + " has placed a bet of: "
							+ bet;
				notifyStatus(notif);
				haventDealtAllPlayers.add(currentPlayer);
			} else {
				notif = currentPlayer.getPlayerName()
							+ " has failed to place a bet";
				notifyStatus(notif);
			}
		}
	}

	// works when all players are not dealt
	// does not work if players dealt once; that is player cannot be dealt if
	// their bets have 'reset'
	public void dealPlayer() {
		setChanged();
		if (haventDealtAllPlayers.contains(currentPlayer)) {
			if (currentPlayer.getBet() > 0) {
				new Thread() {
					@Override
					public void run() {
						ge.dealPlayer(currentPlayer, 1000);
					}
				}.start();
			}

		} else {
			notif = currentPlayer.getPlayerName() + " cannot be dealt";
			notifyStatus(notif);
		}
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	// summary panel gets updated
	public void clicked() {
		setChanged();
		int num = 1;
		notifyObservers(num);
	}

	// activates when card is being dealt to player
	public void cardDealt(PlayingCard card, Player player) {
		// player panel gets updated
		setChanged();
		notifyObservers(MessageEnum.message.player_turn);
		// score resets to 0 if no players being dealt yet
		if (dealingPlayer == null) {
		} else {
			if (!dealingPlayer.equals(player)) {
				score = 0;
			}
		}
		dealingPlayer = player;
		// changes card if player selected is the player dealt, else nothing
		// changes
		if (currentPlayer.equals(dealingPlayer)) {
			setChanged();
			notifyObservers(card);
		}
		score += card.getScore();
		notif = player.getPlayerName() + " has a score of: " + score;
		notifyStatus(notif);
	}

	// when player has busted
	public void hasBust(PlayingCard card, Player player) {
		playerBust.put(player, card);
		notif = player.getPlayerName() + " has a score of: " + score
					+ "...BUSTED!";
		notifyStatus(notif);
		if (currentPlayer.equals(player)) {
			setChanged();
			notifyObservers(card);
		}
		int count = 0;
		// deals house if all players are bust
		for (Player aplayer : haventDealtAllPlayers) {
			if (playerBust.containsKey(aplayer)) {
				count++;
			}
			if (haventDealtAllPlayers.size() == count) {
				haventDealtAllPlayers.clear();
				new Thread() {
					@Override
					public void run() {
						ge.dealHouse(1000);
					}
				}.start();
			}
		}
		score = 0;
	}

	public void callBustCard(Player selectedPlayer) {
		if (haventDealtAllPlayers.contains(selectedPlayer)) {
			if (playerBust.containsKey(selectedPlayer)) {
				setChanged();
				notifyObservers(playerBust.get(selectedPlayer));
			} else {
				setChanged();
				notifyObservers(MessageEnum.message.reset_panel);
			}
		}

	}

	public void houseCardDealt(PlayingCard card) {
		setChanged();
		notifyObservers(MessageEnum.message.house_turn);
		setChanged();
		notifyObservers(card);
		score += card.getScore();
		notif = "HOUSE has a score of: " + score;
		notifyStatus(notif);
	}

	public void houseHasBust(PlayingCard card) {
		notif = "HOUSE has a score of: " + score + "...BUSTED!";
		notifyStatus(notif);
		setChanged();
		notifyObservers(card);
	}

	public void getHouseResult(int result) {
		notif = "HOUSE RESULT: " + result;
		notifyStatus(notif);
		dealingPlayer = null;
		playerBust.clear();
	}

	private void reset() {
		setChanged();
		notifyObservers(MessageEnum.message.reset_panel);
		setChanged();
		notifyObservers(MessageEnum.message.player_turn);
		setChanged();
		notifyObservers(MessageEnum.message.reset_panel);
	}

	private void notifyStatus(String notif) {
		setChanged();
		notifyObservers(notif);
	}

}
