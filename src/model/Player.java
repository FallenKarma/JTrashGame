package model;

import utilites.LoggerUtil;

/**
 * Represents a player in the card game.
 */
public class Player {

	private User user;
    private Card[] tableCards;
    private Integer tableCardsNumber;
    private Card cardInHand;

    private static Integer startingTableCardsNumber = 10;

    /**
     * Constructor for a real player.
     *
     * @param username The username of the player.
     */
    public Player(User user) {
    	this.user = user;
        this.tableCardsNumber = startingTableCardsNumber;
        this.tableCards = new Card[startingTableCardsNumber];
    }

    /**
     * Constructor for a bot player.
     */
    public Player() {
    	this.user = null;
        this.tableCardsNumber = startingTableCardsNumber;
        this.tableCards = new Card[startingTableCardsNumber];
    }

    /**
     * Sets the table cards for the player.
     *
     * @param tableCards The array of table cards.
     */
    public void setTableCards(Card[] tableCards) {
        this.tableCards = tableCards;
    }

    /**
     * Gets the card in the player's hand.
     *
     * @return The card in the hand.
     */
    public Card getCardInHand() {
        return cardInHand;
    }

    /**
     * Draws a card into the player's hand.
     *
     * @param cardInHand The card to be drawn.
     * @return The drawn card.
     */
    public Card draw(Card cardInHand) {
        LoggerUtil.logInfo("The current player draws a : " + cardInHand.toString());
        this.cardInHand = cardInHand;
        return cardInHand;
    }

    /**
     * Gets the number of table cards for the player.
     *
     * @return The number of table cards.
     */
    public Integer getTableCardsNumber() {
        return tableCardsNumber;
    }

    /**
     * Gets the array of table cards for the player.
     *
     * @return The array of table cards.
     */
    public Card[] getTableCards() {
        return tableCards;
    }

    /**
     * Checks if the player can play the card in hand based on the game rules.
     *
     * @return true if the card can be played, false otherwise.
     */
    public boolean canPlayHandCard() {
        int position = cardInHand.getValue();
        if (position == 0 || cardInHand.isKingOrJoker() || position > tableCardsNumber || (!tableCards[position - 1].isFaceDown() && !tableCards[position - 1].isKingOrJoker()))
            return false;
        else {
            tableCards[position - 1].setFaceUp();
            return true;
        }
    }

    /**
     * Checks if the player can play a specific card based on the game rules.
     *
     * @param card The card to be played.
     * @return true if the card can be played, false otherwise.
     */
    public boolean canPlayCard(Card card) {
        int position = card.getValue();
        return !(position == 0 || position > tableCardsNumber || !tableCards[position - 1].isFaceDown());
    }

    /**
     * Switches the player's card in hand with the corresponding table card.
     *
     * @return true if the switch is successful, false otherwise.
     */
    public boolean switchTableCard() {
        int position = cardInHand.getValue();
        cardInHand.setFaceUp();
        Card relativeTableCard = tableCards[position - 1];
        LoggerUtil.logInfo("The current player just switched a " + cardInHand.toString() + " with a " + relativeTableCard.toString());
        Card nextCardOnTheTable = this.cardInHand;
        nextCardOnTheTable.setFaceUp();
        this.cardInHand = relativeTableCard;
        this.tableCards[position - 1] = nextCardOnTheTable;
        return true;
    }

    /**
     * Handles a special switch for a king card.
     *
     * @param position The position of the the table card
     */
    public void specialKingSwitch(int position) {
        cardInHand.setFaceUp();
        Card relativeTableCard = tableCards[position];
        relativeTableCard.setFaceUp();
        tableCards[position] = cardInHand;
        setCardInHand(relativeTableCard);
    }

    /**
     * Gets the position of the first face-down card in the player's table cards.
     *
     * @return The position of the first face-down card, or null if not found.
     */
    public Integer getFirstFaceDownCard() {
        for (int i = 0; i < tableCardsNumber; i++) {
            if (tableCards[i].isFaceDown())
                return i;
        }
        return null;
    }

    /**
     * Discards the card in the player's hand.
     *
     * @return The discarded card.
     */
    public Card discard() {
        LoggerUtil.logInfo("The current player just discarded a " + cardInHand.toString());
        Card temporary = cardInHand;
        cardInHand = null;
        return temporary;
    }

    /**
     * Sets the card in the player's hand.
     *
     * @param cardInHand The card to be set in hand.
     */
    public void setCardInHand(Card cardInHand) {
        this.cardInHand = cardInHand;
    }

    /**
     * Checks if the player is a bot.
     *
     * @return true if the player is a bot, false otherwise.
     */
    public boolean isBot() {
        return user == null ;
    }


	/**
     * Checks if the player has won the round.
     *
     * @return true if the player has won the round, false otherwise.
     */
    public boolean hasWonRound() {
        for (int i = 0; i < tableCardsNumber; i++) {
            if (tableCards[i].isFaceDown())
                return false;
        }
        return true;
    }

    /**
     * Checks if the player has won the game.
     *
     * @return true if the player has won the game, false otherwise.
     */
    public boolean wonGame() {
        return (tableCardsNumber == 1 && !tableCards[0].isFaceDown() );
    }

    /**
     * Resets the table cards for the player.
     */
    public void resetTableCards() {
        for (int i = 0; i < startingTableCardsNumber; i++) {
            tableCards[i] = null;
        }
    }

    /**
     * Adds the result of the last game to the player's statistics.
     */
    public void addLastGameToStats() {
        if (!isBot()) {
        	if (wonGame())
        		user.addGameWon();
        	else
            	user.addGameLost();
        }
    }

    /**
     * Decreases the number of table cards by one when the player wins a round.
     */
    public void wonRound() {
        this.tableCardsNumber -= 1;
    }

	public User getUser() {
		return user;
	}
    
    
}
