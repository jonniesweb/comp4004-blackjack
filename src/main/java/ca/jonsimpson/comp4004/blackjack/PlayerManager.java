package ca.jonsimpson.comp4004.blackjack;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
	private static Map<String, Player> players = new HashMap<String, Player>();

	public static Player getPlayer(String id) throws PlayerDoesntExistException {
		Player player = players.get(id);
		
		if (player == null) {
			throw new PlayerDoesntExistException();
		}
		
		return player;
	}
	
	
}
