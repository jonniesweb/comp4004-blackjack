package ca.jonsimpson.comp4004.blackjack;

public class NotEnoughPlayersException extends RuntimeException {

	public NotEnoughPlayersException(String reason) {
		super(reason);
	}
	
}
