package ca.jonsimpson.comp4004.blackjack;

public class InvalidStateException extends Exception {

	public InvalidStateException(String reason) {
		super(reason);
	}

	public InvalidStateException() {
	}
	
}
