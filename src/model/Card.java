package model;

public class Card {
	
	private Suits suit;
   	private Rank rank;
   	private Boolean faceDown;

	public Card (Rank rank,Suits suit)  {
	    this.suit = suit;
	    this.rank = rank;
	    faceDown = true;
	}

	public int getValue () {
		return rank.getValue();
	}
	@Override
	public String toString() {
		if (rank == Rank.joker) {
			if (suit == Suits.clubs)
				return "black_" + rank;
			return "red_" + rank;
		}
		return rank+ "_of_" + suit;
	}
	
	public void setFaceUp () {
		faceDown = false;
	}
	
	public boolean isFaceDown() {
		return faceDown;
	}
	
	public boolean isKingOrJoker () {
		return getValue() == 11;
	}
	
	public boolean isQueenOrJ () {
		return getValue() == 0;
	}
	
}
