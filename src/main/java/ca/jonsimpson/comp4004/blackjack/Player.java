package ca.jonsimpson.comp4004.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
	
	private final String id;
	private List<Card> cards = new ArrayList<Card>();
	private boolean stay = false;
	
	public Player(String id) {
		this.id = id;
	}
	
	public void addCard(Card card) {
		cards.add(card);
	}
	
	public List<Card> getCards() {
		return cards;
	}
	
	private void clearCards() {
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
	
	public void stay() {
		stay = true;
	}
	
	public void reset() {
		stay = false;
		clearCards();
	}
	
	public boolean isStay() {
		return stay;
	}
	
	/**
	 * Return true if the player shouldn't be able to play in a round.
	 * 
	 * @return
	 */
	public boolean isRoundFinished() {
		return isStay() || isBust();
	}
	
	/**
	 * Get the list of cards viewable by others
	 * 
	 * @return
	 */
	public List<Card> getPublicCards() {
		
		if (cards.size() > 1) {
			return cards.subList(1, cards.size());
			
		} else {
			return Collections.EMPTY_LIST;
		}
	}
}
