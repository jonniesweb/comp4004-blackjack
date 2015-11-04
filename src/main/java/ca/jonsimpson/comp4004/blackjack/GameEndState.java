package ca.jonsimpson.comp4004.blackjack;

/**
 * The state that allows the players to view who won.
 * 
 */
public class GameEndState extends State {
	
	public GameEndState(Blackjack blackjack) {
		super(blackjack);
	}
	
	@Override
	public StateType getState() {
		return StateType.GAME_END;
	}
	
	
	
}
