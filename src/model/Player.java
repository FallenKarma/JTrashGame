package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author admin
 *
 */

public class Player extends User{
	
	private Card[] tableCards;
	private Integer tableCardsNumber;
	private Card cardInHand;
	
	//costruttore per giocatore reale
	public Player (String username) {
		super(username);
		this.tableCardsNumber = 10;
		this.tableCards = new Card[tableCardsNumber];
	}
	
	public void setTableCards(Card[] tableCards) {
		this.tableCards = tableCards;
	}

	//costruttore per bot
	public Player () {
		super();
		this.tableCardsNumber=10;
		this.tableCards = new Card[tableCardsNumber];
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
	public Card[] getTableCards() {
		return tableCards;
	}



	/**
	 * This method places the player's card in hand
	 * into his corresponding position and take the 
	 * face down card in hand
	 */
	public void switchTableCard () {
		int position = cardInHand.getValue();
		if (position<11 && this.tableCards[position].isFaceDown()) {
			Card nextCardOnTheTable = this.cardInHand;
			nextCardOnTheTable.setFaceUp();
			cardInHand = this.tableCards[position];
			this.tableCards[position] = nextCardOnTheTable;
		}
	}

	public Card discard() {
		return cardInHand;
	}
	
    public void setCardInHand(Card cardInHand) {
		this.cardInHand = cardInHand;
	}

    public boolean isBot() {
    	return this.getNickname().equals("");
    }
	/**
     * Diminuisce di uno il numero di carte sul tavolo, da usare quando il player vince un turno
     * 
     */
	public void reduceTableCardsNumber() {
		this.tableCardsNumber -= 1 ;
	}

	////////////////////////////////////////
	//DEBUGGING METHODS
	////////////////////////////////////////ù
	void printCardsInHand () {
		for (int i=0; i<tableCardsNumber; i++) {
			System.out.println(this.tableCards[i]);
		}
	}
	

	
}
