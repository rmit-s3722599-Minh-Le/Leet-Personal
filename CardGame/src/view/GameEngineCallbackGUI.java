package view;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;

public class GameEngineCallbackGUI implements GameEngineCallback {

	private ViewModel viewModel;

	public GameEngineCallbackGUI(ViewModel viewModel) {
		this.viewModel = viewModel;
	}

	@Override
	public void nextCard(Player player, PlayingCard card, GameEngine engine) {
		viewModel.cardDealt(card, player);

	}

	@Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) {
		viewModel.hasBust(card, player);
	}

	@Override
	public void result(Player player, int result, GameEngine engine) {

	}

	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) {
		viewModel.houseCardDealt(card);

	}

	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) {
		viewModel.houseHasBust(card);

	}

	@Override
	public void houseResult(int result, GameEngine engine) {
		viewModel.getHouseResult(result);

	}

}
