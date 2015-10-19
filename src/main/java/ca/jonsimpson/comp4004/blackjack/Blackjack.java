package ca.jonsimpson.comp4004.blackjack;

import org.springframework.beans.factory.annotation.Autowired;

public class Blackjack {
	
	private static final String STATUS_GO = "go";
	private static final String STATUS_WAITING = "waiting";
	
	@Autowired
	private PlayerManager playerManager;
	
	private CardDeck deck = CardDeck.fullDeck();
	
	public void hit(Player player) {
		player.addCard(deck.takeCard());
	}
	
	public void stay(Player player) {
		
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
		// TODO Auto-generated method stub
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
			player.clearCards();
		}
	}
	
}
