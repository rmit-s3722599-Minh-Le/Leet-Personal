package view;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;
import java.util.logging.ConsoleHandler;

/**
 * 
 * Skeleton/Partial example implementation of GameEngineCallback showing Java
 * logging behaviour
 * 
 * @author Caspar Ryan
 * @see view.interfaces.GameEngineCallback
 * 
 */
public class GameEngineCallbackImpl implements GameEngineCallback {

	//This is used to set the message levels 
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	ConsoleHandler handler = new ConsoleHandler();

	public GameEngineCallbackImpl() {
		LogManager.getLogManager().reset();
		// FINE shows dealing output, INFO only shows result
		logger.setLevel(Level.FINE);
		handler.setLevel(Level.FINER);
		logger.addHandler(handler);
	}

	@Override
	public void nextCard(Player player, PlayingCard card, GameEngine engine) {
		// intermediate results logged at Level.FINE
		String playerName = player.getPlayerName();
		dealingCard(card, playerName, false);
	}

	@Override
	public void result(Player player, int result, GameEngine engine) {
		// final results logged at Level.INFO
		logger.log(Level.INFO, String.format("%s%s", player.getPlayerName(),
					", final result=" + result));
	}

	@Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) {
		String playerName = player.getPlayerName();
		dealingCard(card, playerName, true);
	}

	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) {
		dealingCard(card, "house", false);
	}

	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) {
		dealingCard(card, "house", true);
	}

	@Override
	public void houseResult(int result, GameEngine engine) {
		// String that will contain the results of all players
		String finalPlayerResults = "";
		for (Player player : engine.getAllPlayers()) {
			finalPlayerResults += String.format("\n%s%s", "Player=",
						player.getPlayerId()) + ", "
						+ String.format("%s%s", "name=", player.getPlayerName())
						+ ", "
						+ String.format("%s%s", "points=", player.getPoints());
		}
		// house results
		logger.log(Level.INFO,
					String.format("%s%s", "House", ", final result=" + result));
		// final results of players
		logger.log(Level.INFO, String.format("%s%s", "Final Player Results",
					finalPlayerResults));

	}

	// TODO implement the rest of the GameEngineCallback interface
	private void dealingCard(PlayingCard card, String name, boolean bust) {
		if (bust) {
			logger.log(Level.FINE, String.format("%-3s %s", "Card Dealt to",
						name + " .. " + card.toString() + " .. YOU BUSTED!"));
		} else
			logger.log(Level.FINE, String.format("%-3s %s", "Card Dealt to",
						name + " .. " + card.toString()));
	}
}
