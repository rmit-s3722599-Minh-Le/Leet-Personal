package client;

import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import model.GameEngineImpl;
import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import validate.Validator;
import view.GameEngineCallbackGUI;
import view.GameEngineCallbackImpl;
import view.MainFrame;
import view.ViewModel;

public class MyTestingClient extends SimpleTestClient {
	private static Logger logger = Logger.getLogger("assignment1");

	public static void main(String args[]) {

		final GameEngine gameEngine = new GameEngineImpl();
		final ViewModel viewModel = new ViewModel(gameEngine);
		// call method in Validator.jar to test *structural* correctness
		// just passing this does not mean it actually works .. you need to test
		// yourself!
		// pass false if you want to disable logging .. (i.e. once it passes)
		Validator.validate(true);

		// create two test players
		Player[] players = new Player[] {
				new SimplePlayer("1", "The Shark", 20),
				new SimplePlayer("2", "The Loser", 1000),
				new SimplePlayer("3", "The God", 1200) };

		// add logging callback
		gameEngine.addGameEngineCallback(new GameEngineCallbackImpl());
		gameEngine.addGameEngineCallback(new GameEngineCallbackGUI(viewModel));

		Deque<PlayingCard> shuffledDeck = gameEngine.getShuffledDeck();

		new Thread() {
			@Override
			public void run() {

			}
		}.start();

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// do GUI update on UI thread

				MainFrame mainFrame = new MainFrame(gameEngine, viewModel);
			}
		});

	}

}
