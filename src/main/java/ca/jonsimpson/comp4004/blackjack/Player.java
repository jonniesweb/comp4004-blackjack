package ca.jonsimpson.comp4004.blackjack;

import java.util.ArrayList;
import java.util.List;

public class Player {
	
	private final String id;
	List<Card> cards = new ArrayList<Card>();
	
	public Player(String id) {
		this.id = id;
	}
	
	public void addCard(Card card) {
		cards.add(card);
	}
	
	public List<Card> getCards() {
		return cards;
	}
	
}
