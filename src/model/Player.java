package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author admin
 *
 */

public class Player extends User{
	
	private List<Card> tableCards;
	private Integer tableCardsNumber;
	private Card cardInHand;
	
	//costruttore per giocatore reale
	public Player (String username) {
		super(username);
		this.tableCardsNumber=10;
		this.tableCards = new ArrayList<>();
	}
	
	//costruttore per bot
	public Player () {
		super();
		this.tableCardsNumber=10;
		this.tableCards = new ArrayList<>();
	}
	
	
	public Card getCardInHand() {
		return cardInHand;
	}

	public Card draw(Card cardInHand) {
		this.cardInHand = cardInHand;
		return cardInHand;
	}

	public Integer getTableCardsNumber() {
		return tableCardsNumber;
	}
	public List<Card> getTableCards() {
		return tableCards;
	}

	public void setTableCards(ArrayList<Card> tableCards) {
		this.tableCards = tableCards;
	}

	public void setTableCards (Collection<Card> collection) {
		for (Card card:collection) {
			this.tableCards.add(card);
		}
	}

	
    public void setCardInHand(Card cardInHand) {
		this.cardInHand = cardInHand;
	}

	/**
     * Diminuisce di uno il numero di carte sul tavolo, da usare quando il player vince un turno
     * 
     */
	public void reduceTableCardsNumber() {
		this.tableCardsNumber -= 1 ;
	}

	
	
}
