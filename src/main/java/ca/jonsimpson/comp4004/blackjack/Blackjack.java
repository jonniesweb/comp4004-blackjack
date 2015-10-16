package ca.jonsimpson.comp4004.blackjack;

import org.springframework.beans.factory.annotation.Autowired;

public class Blackjack {

	private static final String STATUS_GO = "go";
	private static final String STATUS_WAITING = "waiting";
	
	@Autowired
	private PlayerManager playerManager;

	public void hit(Player player) {
		
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
	
}
