package model;


import utilites.LoggerUtil;

/**
 * @author admin
 *
 */

public class Player extends User{
	
	private Card[] tableCards;
	private Integer tableCardsNumber;
	private Card cardInHand;
	
	private static Integer startinTableCardsNumber = 10;
	
	//costruttore per giocatore reale
	public Player (String username) {
		super(username);
		this.tableCardsNumber = startinTableCardsNumber;
		this.tableCards = new Card[startinTableCardsNumber];
	}
	

	//costruttore per bot
	public Player () {
		super();
		this.tableCardsNumber=startinTableCardsNumber;
		this.tableCards = new Card[startinTableCardsNumber];
	}
	
	public void setTableCards(Card[] tableCards) {
		this.tableCards = tableCards;
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
		if (position == 0 || cardInHand.isKing() || !tableCards[position-1].isFaceDown() || position > tableCardsNumber)
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
		cardInHand.setFaceUp();
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
		relativeTableCard.setFaceUp();
		tableCards[position] = cardInHand;
		setCardInHand(relativeTableCard);
	}
	
	public Integer getFirstFaceDownCard () {
		for (int i=0; i < tableCardsNumber; i++) {
			if (tableCards[i].isFaceDown() )
				return i;
		}
		return null;
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
    
    public boolean hasWonRound () {
    	for (int i=0; i<tableCardsNumber; i++) {
    		if (tableCards[i].isFaceDown()) 
    			return false;
    	}
    	return true;
    }
    
    public boolean wonGame() {
    	if (tableCards.length == 1 && !tableCards[0].isFaceDown())
    		return true;
    	return false;
    }
    
    public void resetTableCards () {
    	for (int i=0; i<startinTableCardsNumber;i++) {
    		tableCards[i] = null;
    	}
    }
    
	/**
     * Diminuisce di uno il numero di carte sul tavolo, da usare quando il player vince un turno
     * 
     */
	public void wonRound() {
		this.tableCardsNumber -= 1 ;
	}

}
