package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DeckOfCards {
	private ArrayList<Card> deck;
	
    String[] Ranks = {
            "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "jack", "queen", "king", "ace"
        };
    
	public DeckOfCards (Integer numberOfDecks) {
		this.deck = new ArrayList<>();
		for (int i=0; i<numberOfDecks; i++) {
			for (Suits suit: Suits.values()) {
				for (String rank:Ranks) {
					deck.add(new Card(rank,suit));
				}
			}
		}
		shuffle();
	}
	
	public void shuffle() {
		Collections.shuffle(this.deck);
	}
	
	
	public Card drawACard () {
		Card carta = null;
		try {
	            carta = this.deck.remove(0);
	        }
		catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return carta;
	}
	
	public Collection<Card> drawCards (Integer numberOfCardsToDraw) {
		ArrayList<Card> cards = new ArrayList<>();
		for (int i=0; i<numberOfCardsToDraw; i++) {
			cards.add(drawACard());
		}
		return cards;
	}
	
	
}
