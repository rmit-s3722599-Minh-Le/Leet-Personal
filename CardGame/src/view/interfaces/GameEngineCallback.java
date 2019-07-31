package view.interfaces;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;

/**
 * Assignment interface for SADI to notify client of GameEngine events e.g.
 * cards being dealt
 * 
 * @author Caspar Ryan
 * 
 */
public interface GameEngineCallback
{
   /**
    * called for each card as the house is dealing to a Player, use this to
    * update your display for each card or log to console
    * 
    * @param player
    *            the Player who is receiving cards
    * @param card
    *            the next card that was dealt
    * @param engine
    *            a convenience reference to the engine so the receiver can call
    *            methods if necessary
    * @see model.interfaces.GameEngine
    */
   public void nextCard(Player player, PlayingCard card, GameEngine engine);

   /**
    * called when the card causes the player to bust 
    * this method is called instead of {@link #nextCard(Player, PlayingCard, GameEngine)} 
    * this method is called before {@link #result(Player, int, GameEngine)} 
    * use this to update your display for each card or log to console
    * 
    * @param player
    *            the Player who is receiving cards
    * @param card
    *            the bust card that was dealt
    * @param engine
    *            a convenience reference to the engine so the receiver can call
    *            methods if necessary
    * @see model.interfaces.GameEngine
    */
   public void bustCard(Player player, PlayingCard card, GameEngine engine);

   /**
    * 
    * called when the player has bust with final result (result is score prior
    * to the last card that caused the bust)
    * 
    * @param player
    *            the current Player
    * @param result
    *            the final score of the hand
    * @param engine
    *            a convenience reference to the engine so the receiver can call
    *            methods if necessary
    * @see model.interfaces.GameEngine
    */
   public void result(Player player, int result, GameEngine engine);

   /**
    * called as the house is dealing their own hand, use this to update your
    * display for each card or log to console
    * 
    * @param card
    *            the next card that was dealt
    * @param engine
    *            a convenience reference to the engine so the receiver can call
    *            methods if necessary
    * @see model.interfaces.GameEngine
    */
   public void nextHouseCard(PlayingCard card, GameEngine engine);

   /**
    * HOUSE version of 
    * {@link GameEngineCallback#bustCard(Player, PlayingCard, GameEngine)}
    * 
    * @param card
    *            the bust card that was dealt
    * @param engine
    *            a convenience reference to the engine so the receiver can call
    *            methods if necessary
    * @see model.interfaces.GameEngine
    */
   public void houseBustCard(PlayingCard card, GameEngine engine);

   /**
    * called when the HOUSE has bust with final result (result is score prior to the last card
    * that caused the bust)
    * 
    * PRE-CONDITION: This method should only be called AFTER bets have been updated on all Players 
    * so this callback can log Player results
    * 
    * Called from {@link GameEngine#dealHouse(int)} 
    * 
    * @param result
    *            the final score of the dealers (house) hand
    * @param engine
    *            a convenience reference to the engine so the receiver can call
    *            methods if necessary
    * @see model.interfaces.GameEngine
    */
   public void houseResult(int result, GameEngine engine);
}
