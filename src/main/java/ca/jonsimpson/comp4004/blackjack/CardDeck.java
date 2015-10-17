package ca.jonsimpson.comp4004.blackjack;

import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import ca.jonsimpson.comp4004.blackjack.Card.Rank;
import ca.jonsimpson.comp4004.blackjack.Card.Suit;

/**
 * A representation of a set of playing cards. Does not contain duplicate cards.
 */
public class CardDeck extends AbstractSet<Card> {
	
	private Set<Card> cards = new HashSet<Card>();
	
	@Override
	public Iterator<Card> iterator() {
		return cards.iterator();
	}
	
	@Override
	public int size() {
		return cards.size();
	}
	
	@Override
	public boolean add(Card e) {
		return cards.add(e);
	}
	
	/**
	 * Get a full deck of 52 unique cards.
	 * 
	 * @return
	 */
	public static CardDeck fullDeck() {
		
		CardDeck cardDeck = new CardDeck();
		
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				cardDeck.add(new Card(rank, suit));
			}
		}
		
		return cardDeck;
	}

	public Card takeCard() {
		Random random = new Random();
		
		int randomIndex = random.nextInt(size());
		int currentIndex = 0;
		
		Iterator<Card> iterator = iterator();
		while (iterator.hasNext()) {
			Card c = iterator.next();
			if (currentIndex++ == randomIndex) {
				iterator.remove();
				return c;
			}
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
