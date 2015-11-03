package ca.jonsimpson.comp4004.blackjack;

public abstract class State {
	
	public enum StateType {
		PLAYER_JOIN, GAME_IN_PROGRESS
	}
	
	private final Blackjack blackjack;
	
	public State(Blackjack blackjack) {
		this.blackjack = blackjack;
	}
	
	protected Blackjack getBlackjack() {
		return blackjack;
	}
	
	public abstract StateType getState();
	
	public void hit(Player player) throws InvalidStateException {
		throw new InvalidStateException();
	}
	
	public void stay(Player player) throws InvalidStateException {
		throw new InvalidStateException();
	}
	
	public Player getWaitingForPlayer() throws InvalidStateException {
		throw new InvalidStateException();
	}
	
	public String newPlayer() throws InvalidStateException {
		throw new InvalidStateException();
	}
	
}
