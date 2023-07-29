package model;

public class Card {
	
	private Suits suit;
   	private Rank rank;

	public Card (Rank rank,Suits suit)  {
	    this.suit = suit;
	    this.rank = rank;
	}

	public int getValue () {
		return rank.getValue();
	}
	@Override
	public String toString() {
		return rank+"_of_"+suit;
	}
	
}
