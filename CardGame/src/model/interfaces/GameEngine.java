package model.interfaces;

import java.util.Collection;
import java.util.Deque;

import view.interfaces.GameEngineCallback;

/**
 * Assignment interface for SADI providing main Card Game model functionality
 * 
 * @author Caspar Ryan
 * 
 */
public interface GameEngine
{
   public static final int BUST_LEVEL = 21;

   /**
    * Deal cards to the player, increments of delay are in milliseconds (ms)
    * 
    * 1. deal a card to the player
    * 2. call GameEngineCallback.nextCard(...)
    * 3. continue looping until the player busts 
    * (default value of GameEngine.BUST_TOTAL=21)
    * 4. GameEngineCallback.bustCard(...)
    * 5. call {@link GameEngineCallback#result(Player, int, GameEngine)}
    * with final result for player (the pre bust total) 
    * 6. update the player with final result so it can be retrieved later
    * 
    * @param player
    *            the current player who will have their result set at the end
    *            of the hand
    * @param delay
    *            the delay between cards being dealt
    * @see view.interfaces.GameEngineCallback
    * 
    */
   public abstract void dealPlayer(Player player, int delay);

   /**
    * Same as dealPlayer() but deals for the house and calls the house versions
    * of the callback methods on GameEngineCallback, no player parameter is
    * required
    * 
    * After the house deal has finished but BEFORE calling houseResult() win/loss values
    * are applied to all players based on their bet
    * 
    * @see view.interfaces.GameEngineCallback
    * 
    * @param delay
    *   the delay between cards being dealt
    * 
    * @see GameEngine#dealPlayer(Player, int)
    */
   public abstract void dealHouse(int delay);

   /**
    * @param player
    *            to add to game
    */
   public abstract void addPlayer(Player player);

   /**
    * @param id
    *            id of player to retrieve (null if not found)
    * @return the Player or null if Player does no exist
    */
   public abstract Player getPlayer(String id);

   /**
    * @param player
    *            to remove from game
    * @return true if the player existed
    */
   public abstract boolean removePlayer(Player player);

   /**
    * @param gameEngineCallback
    *            a client specific implementation of GameEngineCallback used to
    *            perform display updates etc.
    * 
    *            you will use a different implementation of the
    *            GameEngineCallback for GUI and console versions
    * 
    */
   public abstract void addGameEngineCallback(GameEngineCallback gameEngineCallback);

   /**
    * @param gameEngineCallback
    *            called when a player quits the game to remove no longer needed
    *            UI updates
    * @return true if the gameEngineCallback existed
    * 
    */
   public abstract boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback);

   /**
    * 
    * @return an unmodifiable collection (or a copy) of all Players
    * @see model.interfaces.Player
    */
   public abstract Collection<Player> getAllPlayers();

   /**
    * the implementation should forward the call to the Player class to handle
    * 
    * @param player 
    *            the Player who is placing the bet
    * @param bet
    *            the bet in points
    * @return true 
    *            if bet is greater than or equal to 0 and player had sufficient points to place the bet
    */
   public abstract boolean placeBet(Player player, int bet);

   /**
    * A debug method to return a deck of cards containing 52 unique cards in
    * random/shuffled order
    * 
    * @return a Deque (type of Collection) of PlayingCard
    * @see model.interfaces.PlayingCard
    */
   public abstract Deque<PlayingCard> getShuffledDeck();
}