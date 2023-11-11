package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import utilites.LoggerUtil;

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
		LoggerUtil.logInfo("The current player draws a : " + cardInHand.toString());
		this.cardInHand = cardInHand;
		return cardInHand;
	}

	public Integer getTableCardsNumber() {
		return tableCardsNumber;
	}
	public Card[] getTableCards() {
		return tableCards;
	}


	public boolean canPlayHandCard() {
		int position = cardInHand.getValue();
		if (position == 0 || cardInHand.isKing() || !tableCards[position-1].isFaceDown())
			return false;	
		else {
			tableCards[position-1].setFaceUp();
			return true;
		}
	}
	
	
	/**
	 * This method places the player's card in hand
	 * into his corresponding position and take the 
	 * face down card in hand
	 */
	public boolean switchTableCard () {
		int position = cardInHand.getValue();
		Card relativeTableCard = tableCards[position-1];
		LoggerUtil.logInfo("The current player just switched a " + cardInHand.toString() + " with a " + relativeTableCard.toString());
		Card nextCardOnTheTable = this.cardInHand;
		nextCardOnTheTable.setFaceUp();
		this.cardInHand = relativeTableCard;
		this.tableCards[position-1] = nextCardOnTheTable;
		return true;
	}

	public void specialKingSwitch(int position) {
		cardInHand.setFaceUp();
		Card relativeTableCard = tableCards[position];
		tableCards[position] = cardInHand;
		setCardInHand(relativeTableCard);
	}
	
	public Card discard() {
		LoggerUtil.logInfo("The current player just discarded a " + cardInHand.toString());
		Card temporary = cardInHand;
		cardInHand = null;
		return temporary;
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
