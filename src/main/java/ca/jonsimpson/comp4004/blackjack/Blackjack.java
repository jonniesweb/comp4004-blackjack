package ca.jonsimpson.comp4004.blackjack;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class Blackjack {
	
	private static final String STATUS_GO = "go";
	private static final String STATUS_WAITING = "waiting";
	
	@Autowired
	private PlayerManager playerManager;
	
	private State state;
	
	// the game's deck of cards to pull from
	private CardDeck deck = CardDeck.fullDeck();
	
	public Blackjack() {
		state = new PlayerJoinState(this);
	}
	
	public void hit(Player player) throws InvalidStateException {
		state.hit(player);
		
	}
	
	public void stay(Player player) throws InvalidStateException {
		state.stay(player);
		
	}
	
	public String getStatus(Player player) {
		if (waitingFor(player)) {
			return STATUS_GO;
		} else {
			return STATUS_WAITING;
		}
		
	}
	
	/**
	 * Returns true if the game is waiting for this player to go.
	 * 
	 * @param player
	 * @return
	 */
	private boolean waitingFor(Player player) {
		try {
			return player.equals(state.getWaitingForPlayer());
		} catch (InvalidStateException e) {
			e.printStackTrace();
		}
		
		// return false if the state is invalid
		return false;
	}
	
	public PlayerManager getPlayerManager() {
		return playerManager;
	}
	
	private void resetDeck() {
		deck = CardDeck.fullDeck();
	}
	
	/**
	 * Remove all the cards from every player and reset the deck to a full 52
	 * cards.
	 */
	public void newGame() {
		resetDeck();
		
		for (Player player : getPlayerManager().getAllPlayers()) {
			player.reset();
		}
	}
	
	public Card takeCard() {
		return deck.takeCard();
	}
	
	/**
	 * @return a new playerId if the player has joined the game, null if the
	 *         player must wait for a new game to start
	 */
	public String newPlayer() {
		try {
			return state.newPlayer();
			
		} catch (InvalidStateException e) {
			return null;
			// e.printStackTrace();
		}
		
	}
	
	public void startGame() {
		List<Player> playerOrder = getPlayerManager().getPlayerOrder();
		state = new GameInProgressState(this, playerOrder);
	}
	
	public void finishGame() {
		state = new GameEndState(this);
	}
	
	public void endGame() {
		state = new PlayerJoinState(this);
	}

	public boolean isGameOver() {
		return state instanceof GameEndState;
	}
	
}
