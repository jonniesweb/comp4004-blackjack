package ca.jonsimpson.comp4004.blackjack;

public class Card implements Comparable<Card> {
	
	/**
	 * The suit of this card. Possibilities are Diamond, Heart, Spade and Club.
	 */
	public static enum Suit {
		DIAMOND, HEART, SPADE, CLUB
	};
	
	/**
	 * The part of the card that isn't the suit. It represents numbers two
	 * through 10 and Jack, Queen, King Ace.
	 */
	public static enum Rank {
		TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
	};
	
	private Suit suit;
	private Rank rank;
	
	/**
	 * @param rank
	 * @param suit
	 */
	public Card(Rank rank, Suit suit) {
		
		// check for invalid rank or suit
		if (rank == null) {
			throw new NullPointerException("rank cannot be null");
		} else if (suit == null) {
			throw new NullPointerException("suit cannot be null");
		}
		
		// everything's ok, set the values
		this.rank = rank;
		this.suit = suit;
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Card)) {
			return false;
		}
		
		Card card = (Card) obj;
		return card.rank.equals(rank) && card.suit.equals(suit);
	}
	
	public Suit getSuit() {
		return suit;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	/**
	 * Get the numerical value of this card.
	 * @return the number that this card represents
	 */
	public int getValue() {
		switch (rank) {
		case ACE:
			return 1;
		case TWO:
			return 2;
		case THREE:
			return 3;
		case FOUR:
			return 4;
		case FIVE:
			return 5;
		case SIX:
			return 6;
		case SEVEN:
			return 7;
		case EIGHT:
			return 8;
		case NINE:
			return 9;
		case TEN:
		case JACK:
		case QUEEN:
		case KING:
			return 10;
		
		default:
			throw new RuntimeException("unknown rank encountered");
		}
	}
	
	/**
	 * Compare this card with another card by rank. Returns a number less than
	 * zero, zero or greater than zero for if this card's rank is less than,
	 * equal or greater than the comparison card.
	 */
	@Override
	public int compareTo(Card card) {
		if (card == null) {
			throw new NullPointerException();
		}
		
		int indexOfThisCard = -1;
		int indexOfCompareCard = -1;
		Rank[] ranks = Rank.values();
		
		// iterate over the cards, finding the index position of the ranks
		for (int i = 0; i < ranks.length; i++) {
			if (getRank() == ranks[i]) {
				indexOfThisCard = i;
			}
			if (card.getRank() == ranks[i]) {
				indexOfCompareCard = i;
			}
			if (indexOfThisCard != -1 && indexOfCompareCard != -1) {
				break;
			}
		}
		
		// do the comparison of the indexes
		return indexOfCompareCard - indexOfThisCard;
	}
	
	@Override
	public int hashCode() {
		int result = 37;
		result = 37 * result + suit.hashCode();
		result = 37 * result + rank.hashCode();
		return result;
	}
	
	@Override
	public String toString() {
		return "Card " + rank + " " + suit;
	}
}
