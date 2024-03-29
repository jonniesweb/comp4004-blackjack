package ca.jonsimpson.comp4004.blackjack;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class Blackjack {
	
	public static final String STATUS_GO = "go";
	public static final String STATUS_WAITING = "waiting";
	
	@Autowired
	private PlayerManager playerManager;
	
	private State state;
	
	// the game's deck of cards to pull from
	private CardDeck deck = CardDeck.fullDeck();
	
	public Blackjack() {
		state = new PlayerJoinState(this);
	}
	
	public void hit(Player player) throws InvalidStateException {
		if (waitingFor(player)) {
			state.hit(player);
		} else {
			throw new InvalidStateException();
		}
		
	}
	
	public void stay(Player player) throws InvalidStateException {
		if (waitingFor(player)) {
			state.stay(player);
		} else {
			throw new InvalidStateException();
		}
		
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
	public void reset() {
		resetDeck();
		
		getPlayerManager().removeAllPlayers();
//		for (Player player : getPlayerManager().getAllPlayers()) {
//			player.reset();
//		}
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
	
	/**
	 * Change the state of the game to the {@link PlayerJoinState}.
	 */
	public void newGame() {
		reset();
		state = new PlayerJoinState(this);
	}

	/**
	 * Change the state of the game to the {@link GameInProgressState} state.
	 */
	public void startGame() {
		List<Player> playerOrder = getPlayerManager().getPlayerOrder();
		state = new GameInProgressState(this, playerOrder);
	}
	
	/**
	 * Change the state of the game to the {@link GameEndState}.
	 */
	public void finishGame() {
		state = new GameEndState(this);
	}
	
	public boolean isPlayerJoinState() {
		return state instanceof PlayerJoinState;
	}

	public boolean isGameInProgressState() {
		return state instanceof GameInProgressState;
	}

	public boolean isGameOverState() {
		return state instanceof GameEndState;
	}
	
	/**
	 * For testing only
	 * @param player
	 * @throws InvalidStateException
	 */
	public void setCurrentPlayer(Player player) throws InvalidStateException {
		if (isGameInProgressState()) {
			GameInProgressState state = (GameInProgressState) this.state;
			state.setCurrentPlayer(player);
		} else {
			throw new InvalidStateException();
		}
	}
	
	/**
	 * For testing only
	 * @throws InvalidStateException
	 */
	public void nextPlayersTurn() throws InvalidStateException {
		if (isGameInProgressState()) {
			GameInProgressState state = (GameInProgressState) this.state;
			state.nextPlayer();
		} else {
			throw new InvalidStateException();
		}
	}
	
}
