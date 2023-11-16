package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class DeckOfCards {
	private ArrayList<Card> deck;

    
	public DeckOfCards (Integer numberOfDecks) {
		this.deck = new ArrayList<>();
		for (int i=0; i < numberOfDecks; i++) {
			for (Rank rank:Rank.values() ) {
				for (Suits suit: Suits.values()) {
					deck.add(new Card(rank,suit));
				}
			}
		}
		deck = (ArrayList<Card>) deck.stream().limit(deck.size() -2 ).collect(Collectors.toList());
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
