package model;

import model.interfaces.PlayingCard;

public class PlayingCardImpl implements PlayingCard {

	private Value value;
	private Suit suit;
	private int score = 0;

	public PlayingCardImpl(Suit suit, Value value) {
		this.suit = suit;
		this.value = value;
		switch (value) {
		case ACE:
			score = 1;
			break;
		case TWO:
			score = 2;
			break;
		case THREE:
			score = 3;
			break;
		case FOUR:
			score = 4;
			break;
		case FIVE:
			score = 5;
			break;
		case SIX:
			score = 6;
			break;
		case SEVEN:
			score = 7;
			break;
		case EIGHT:
			score = 8;
			break;
		case NINE:
			score = 9;
			break;
		case TEN:
		case JACK:
		case QUEEN:
		case KING:
			score = 10;
			break;

		}
	}

	@Override
	public Suit getSuit() {
		return suit;
	}

	@Override
	public Value getValue() {
		return value;
	}

	@Override
	public int getScore() {
		return score;

	}

	@Override
	public boolean equals(PlayingCard card) {
		if (card.getValue().equals(value)) {
			if (card.getSuit().equals(suit)) {
				return true;
			}
		}
		return false;
//		return (equals((Object) card));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + score;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayingCardImpl other = (PlayingCardImpl) obj;
		if (score != other.score)
			return false;
		if (suit != other.suit)
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	public String toString() {
		return "Suit: " + suit + ", Value: " + value + ", Score: " + score;
	}

}
