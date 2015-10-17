package ca.jonsimpson.comp4004.blackjack;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PlayerManager {
	private Map<String, Player> players = new HashMap<String, Player>();

	public Player getPlayer(String id) throws UnknownPlayerException {
		Player player = players.get(id);
		
		if (player == null) {
			throw new UnknownPlayerException();
		}
		
		return player;
	}

	/**
	 * Create a new player and add it to the game.
	 * @return The player's id
	 */
	public String newPlayer() {
		Random r = new Random();
		int nextInt = r.nextInt();
		String id = Integer.toString(nextInt);
		
		players.put(id, new Player(id));
		
		return id;
	}
	
	public int getSize() {
		return players.size();
	}
	
}
