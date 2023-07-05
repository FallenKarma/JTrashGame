package model;

public class Card {
	
	private Suits suit;
   	private String rank;

	public Card (String rank,Suits suit)  {
	    this.suit = suit;
	    this.rank = rank;
	}

	@Override
	public String toString() {
		return rank+"_of_"+suit;
	}
	
}
