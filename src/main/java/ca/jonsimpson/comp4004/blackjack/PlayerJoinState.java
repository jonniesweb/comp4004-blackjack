package ca.jonsimpson.comp4004.blackjack;

public class PlayerJoinState extends State {
	
	public PlayerJoinState(Blackjack blackjack) {
		super(blackjack);
	}
	
	@Override
	public StateType getState() {
		return StateType.PLAYER_JOIN;
	}
	
	@Override
	public String newPlayer() throws InvalidStateException {
		return getBlackjack().getPlayerManager().newPlayer();
	}
	
}
