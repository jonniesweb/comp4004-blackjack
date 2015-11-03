package ca.jonsimpson.comp4004.blackjack;

import java.util.List;

public class GameInProgressState extends State {
	
	private List<Player> playerOrder;
	private Player currentPlayer;
	private Blackjack blackjack;
	
	public GameInProgressState(Blackjack blackjack, List<Player> playerOrder) {
		super(blackjack);
		// validate input
		if (playerOrder == null) {
			throw new NullPointerException("playerOrder cannot be null");
		} else if (playerOrder.size() == 0) {
			throw new NotEnoughPlayersException("not enough players to play a game");
		}
		
		this.playerOrder = playerOrder;
		
		// set the first player to go
		currentPlayer = playerOrder.get(0);
		
	}
	
	public void nextPlayer() {
		// get the current index
		int index = playerOrder.indexOf(currentPlayer);
		
		// get the logical next index, rolling over if larger than the list
		index++;
		index %= playerOrder.size();
		
		// set the next player at the new index
		currentPlayer = playerOrder.get(index);
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
		
		player.addCard(blackjack.takeCard());
		
	}
	
	public void stay(Player player) throws InvalidStateException {
		checkNotStay(player);
		
		player.stay();
		
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

}
