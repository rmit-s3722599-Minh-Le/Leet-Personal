package model;

import model.interfaces.Player;

public class SimplePlayer implements Player {
	private String playerId;
	private String playerName;
	// points earned
	private int points;
	// total points of player
	private int totalPoints = 0;
	private int bet;
	private int result;

	public SimplePlayer(String playerId, String playerName, int initialPoints) {
		this.playerId = playerId;
		this.playerName = playerName;
		totalPoints = initialPoints;
	}

	@Override
	public String getPlayerName() {
		return playerName;
	}

	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;

	}

	@Override
	public int getPoints() {
		return totalPoints;
	}

	@Override
	public void setPoints(int points) {
		// points are added to total points
		totalPoints += points - bet;
		this.points = points;

	}

	@Override
	public String getPlayerId() {
		return playerId;
	}

	@Override
	public boolean placeBet(int bet) {
		// if the bet placed is at least the amount of the total points
		// available.
		if (totalPoints >= bet) {
			this.bet = bet;
			return true;
		}
		// otherwise placing bet is false
		else
			return false;
	}

	@Override
	public int getBet() {
		return bet;
	}

	@Override
	public void resetBet() {
		placeBet(0);
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public void setResult(int result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "Player: Id=" + playerId + ", name=" + playerName + ", points="
					+ totalPoints;

	}
}
