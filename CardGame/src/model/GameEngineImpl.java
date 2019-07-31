package model;

import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;

public class GameEngineImpl implements GameEngine {

	private LinkedList<Player> players = new LinkedList<Player>();
	private Deque<PlayingCard> deck = new LinkedList<PlayingCard>();
	private HashSet<GameEngineCallback> gecb = new HashSet<GameEngineCallback>();
	private GameEngineCallback thisConsole;
	private final boolean FOREVERTRUE = true;
	// total Score of the players
	private int totalScore;

	@Override
	public void dealPlayer(Player player, int delay) {
			dealCard(player, delay);
	}

	@Override
	public void dealHouse(int delay) {
			dealCard(null, delay);

	}

	@Override
	public void addPlayer(Player player) {
		// checks each player ID of each player in the collections and compares
		// with the inputed player's ID. Existing ID (duplicate) will replace
		// the old player.
		for (Player aPlayer : players) {
			// compares a player's id with the inputed player's id
			// removes player if true
			if (player.getPlayerId().equals(aPlayer.getPlayerId())) {
				players.remove(aPlayer);
			}
		}
		// adds the new player to the collection
		this.players.add(player);

	}

	@Override
	public Player getPlayer(String id) {
		// checks for inputed id that exists in the collections. Returns player
		// if match
		for (Player player : players) {
			if (player.getPlayerId().equals(id)) {
				return player;
			}
		}
		return null;
	}

	@Override
	public boolean removePlayer(Player player) {
		// checks in the players Linked List to see a duplicate
		for (Player checkPlayer : players) {
			// compares the id's, remove player if true
			if (checkPlayer.getPlayerId().equals(player.getPlayerId())) {
				players.remove(player);
				return true;
			}
		}
		return false;
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		gecb.add(gameEngineCallback);
	}

	@Override
	public boolean removeGameEngineCallback(
				GameEngineCallback gameEngineCallback) {
		// searches inputed gameEngineCallback in the collections.
		if (gecb.contains(gameEngineCallback)) {
			gecb.remove(gameEngineCallback);
			return true;
		}
		return false;
	}

	@Override
	public Collection<Player> getAllPlayers() {
		return players;
	}

	@Override
	public boolean placeBet(Player player, int bet) {
		return player.placeBet(bet);
	}


	@Override
	public Deque<PlayingCard> getShuffledDeck() {
		Deque<PlayingCard> newDeck = new LinkedList<PlayingCard>();
		// each value (13 of them) is combined with each suit (4)
		for (PlayingCard.Suit suit : PlayingCard.Suit.values()) {
			for (PlayingCard.Value value : PlayingCard.Value.values()) {
				newDeck.add(new PlayingCardImpl(suit, value));
			}
		}
		// deck is shuffled
		Collections.shuffle((List<?>) newDeck);
		deck = newDeck;
		return deck;
	}

	// activates when deck is empty
	private void outOfCards() {
		if (deck.isEmpty()) {
			getShuffledDeck();
		}
	}

	// this private method deals with dealing card actions to the player/house
	private void dealCard(Player player, int delay) {
		totalScore = 0;
		PlayingCard latestCard = null;
		while (FOREVERTRUE) {
			// delay
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			outOfCards();
			// gets card from the top of the deck
//			latestCard = deck.getFirst();
			latestCard = deck.poll();
			// breaks loop if the card drawn will bust the player/house
			if (totalScore + latestCard.getScore() > BUST_LEVEL) {
				if (player != null) {
				player.setResult(totalScore);
				}
				break;
			}
			for (GameEngineCallback aConsoleFromCollections : gecb) {
				thisConsole = aConsoleFromCollections;
			// for house
			if (player == null) {

				thisConsole.nextHouseCard(latestCard, this);
			}
			// for player
			else {
				thisConsole.nextCard(player, latestCard, this);
			}
			// score gets added
			
			}
			totalScore += latestCard.getScore();
		}

		for (GameEngineCallback aConsoleFromCollections : gecb) {
			thisConsole = aConsoleFromCollections;
		// when player/house are bust
		if (player == null) {
	
			thisConsole.houseBustCard(latestCard, this);
			// Loops for each player
			// results section
			for (Player player1 : players) {
				int pointsEarnt = 0;
				// Compares the totalScore of house with player
				// points (the doubled amount from the player's bet) are given
				// to player as player win
				if (player1.getResult() > totalScore) {
					pointsEarnt = player1.getBet() * 2;
					player1.setPoints(pointsEarnt);
				}
				// points (the amount from the player's bet) are given to player
				// as a draw
				else if (player1.getResult() == totalScore) {
					pointsEarnt = player1.getBet();
					player1.setPoints(pointsEarnt);
				}
				// 0 points are given to player as player lost
				else
					{player1.setPoints(pointsEarnt);}
				player1.resetBet();
			}
			aConsoleFromCollections.houseResult(totalScore, this);
		} else {

			thisConsole.bustCard(player, latestCard, this);
			thisConsole.result(player, totalScore, this);
		}
		}
	}

}
