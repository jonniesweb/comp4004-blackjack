package ca.jonsimpson.comp4004.blackjack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.TreeSet;

/**
 * The state that allows the players to view who won.
 * 
 */
public class GameEndState extends State {
	
	public GameEndState(Blackjack blackjack) {
		super(blackjack);
		
		calculateWinner();
	}
	
	private void calculateWinner() {
		
		TreeSet<PlayerResult> playerResults = new TreeSet<PlayerResult>();
		
		// create a PlayerResult object for each player and add it to the
		// playerResults
		for (Player p : getBlackjack().getPlayerManager().getAllPlayers()) {
			
			PlayerResult playerResult = new PlayerResult();
			playerResult.player = p;
			playerResult.score = p.getCardTotal();
			
			playerResults.add(playerResult);
		}
		
		ArrayList<Player> winners = new ArrayList<Player>();
		
		// add the first player from the set since they have the highest score
		Iterator<PlayerResult> iterator = playerResults.iterator();
		Player highestScorePlayer = iterator.next().player;
		highestScorePlayer.setWinner();
		winners.add(highestScorePlayer);
		
		while (iterator.hasNext()) {
			GameEndState.PlayerResult playerResult = (GameEndState.PlayerResult) iterator.next();
			
			if (winners.get(0).getCardTotal() == playerResult.score) {
				winners.add(playerResult.player);
				playerResult.player.setWinner();
			} else {
				playerResult.player.setLoser();
			}
		}
		
		// set any bust players
		for (PlayerResult playerResult : playerResults) {
			if (playerResult.player.isBust()) {
				playerResult.player.setLoser();
				
			}
		}
	}
	
	@Override
	public StateType getState() {
		return StateType.GAME_END;
	}
	
	private static class PlayerResult implements Comparable<PlayerResult> {
		Player player;
		int score;
		
		@Override
		public int compareTo(PlayerResult other) {
			// if player is bust, they always lose
			if (score > 21) {
				return 1;
			}
			
			return other.score - score;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj instanceof PlayerResult) {
				return ((PlayerResult) obj).player == player;
			}
			
			return false;
		}
		
		@Override
		public int hashCode() {
			return 31 * player.hashCode();
		}
	}
	
}
