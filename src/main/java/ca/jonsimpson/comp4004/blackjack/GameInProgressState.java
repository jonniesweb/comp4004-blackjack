package ca.jonsimpson.comp4004.blackjack;

import java.util.ArrayList;
import java.util.List;

public class GameInProgressState extends State {
	
	/**
	 * All players that are in the game, in proper order
	 */
	private List<Player> playerOrder;
	
	/**
	 * Players that are able to play in the next round
	 */
	private List<Player> livePlayers;
	
	/**
	 * The current player that the game is waiting for to make a move
	 */
	private Player currentPlayer;
	
	public GameInProgressState(Blackjack blackjack, List<Player> playerOrder) {
		super(blackjack);
		
		// validate input
		if (playerOrder == null) {
			throw new NullPointerException("playerOrder cannot be null");
		} else if (playerOrder.size() == 0) {
			throw new NotEnoughPlayersException("not enough players to play a game");
		}
		
		this.playerOrder = playerOrder;
		
		// initialize the livePlayers list with all the current players
		livePlayers = new ArrayList<Player>(playerOrder);
		
		// set the first player to go
		currentPlayer = playerOrder.get(0);
		
		// give two cards to each player
		for (Player player : playerOrder) {
			doHit(player);
			doHit(player);
		}
		
	}
	
	void nextPlayer() {
		// get the current index
		int index = livePlayers.indexOf(currentPlayer);
		
		// get the logical next index, rolling over if larger than the list
		index++;
		index %= livePlayers.size();
		
		// set the next player at the new index
		currentPlayer = livePlayers.get(index);
	}
	
	@Override
	public StateType getState() {
		return StateType.GAME_IN_PROGRESS;
	}
	
	@Override
	public Player getWaitingForPlayer() {
		return currentPlayer;
	}
	
	public void hit(Player player) throws InvalidStateException {
		// only HIT if the player's hand isn't bust and they haven't declared
		// STAY
		checkNotBusted(player);
		checkNotStay(player);
		
		
		// take a card
		doHit(player);
		
		nextPlayer();
		
		// if player is busted, remove player
		try {
			checkNotBusted(player);
		} catch (InvalidStateException e) {
			playerFinished(player);
		}
		
	}
	
	/**
	 * Actually give the player a card
	 * 
	 * @param player
	 */
	private void doHit(Player player) {
		player.addCard(getBlackjack().takeCard());
	}
	
	public void stay(Player player) throws InvalidStateException {
		checkNotStay(player);
		
		player.stay();
		
		nextPlayer();
		
		// remove the player from the live player list
		playerFinished(player);
		
	}
	
	private void playerFinished(Player player) {
		
		livePlayers.remove(player);
		
		if (livePlayers.isEmpty()) {
			currentPlayer = null;
			
			doGameEndState();
		}
	}
	
	/**
	 * Called to end the current game and show the winners.
	 */
	private void doGameEndState() {
		getBlackjack().finishGame();
	}

	/**
	 * Check that the player isn't stay. Throw an error otherwise.
	 * 
	 * @param player
	 * @throws InvalidStateException
	 */
	protected void checkNotStay(Player player) throws InvalidStateException {
		if (player.isStay()) {
			throw new InvalidStateException("player is staying");
		}
	}
	
	/**
	 * Check that the player isn't busted. Throw an error otherwise.
	 * 
	 * @param player
	 * @throws InvalidStateException
	 */
	protected void checkNotBusted(Player player) throws InvalidStateException {
		if (player.isBust()) {
			throw new InvalidStateException("player is bust");
		}
	}

	public void setCurrentPlayer(Player player) {
		currentPlayer = player;
	}
	
}
