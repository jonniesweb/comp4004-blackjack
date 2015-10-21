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

	public void clearCards() {
		cards = new ArrayList<Card>();
	}
	
	public int getCardTotal() {
		int total = 0;
		for (Card card : cards) {
			total += card.getValue();
		}
		
		return total;
	}
	
	public boolean isBust() {
		return getCardTotal() > 21;
	}
	
}
