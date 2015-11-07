package ca.jonsimpson.comp4004.blackjack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PlayerManager {
	private HashMap<String, Player> players = new HashMap<String, Player>();
	private List<Player> playerOrder = new ArrayList<Player>();

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
		// create unique player id
		Random r = new Random();
		int nextInt = r.nextInt();
		String id = Integer.toString(nextInt);
		
		// add player to id/player map
		Player player = new Player(id);
		players.put(id, player);
		
		// add player to playerOrder list
		playerOrder.add(player);
		
		return id;
	}
	
	public int getSize() {
		return players.size();
	}

	public Collection<Player> getAllPlayers() {
		return players.values();
	}

	public List<Player> getPlayerOrder() {
		return playerOrder;
	}
	
	public void removeAllPlayers() {
		players = new HashMap<String, Player>();
		playerOrder = new ArrayList<Player>();
	}

}
