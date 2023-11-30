package model;

/**
 * Represents a playing card with a specific rank, suit, and face orientation.
 */
public class Card {

    private Suits suit;
    private Rank rank;
    private Boolean faceDown;

    /**
     * Constructs a new Card with the specified rank and suit, initially face-down.
     *
     * @param rank The rank of the card.
     * @param suit The suit of the card.
     */
    public Card(Rank rank, Suits suit) {
        this.suit = suit;
        this.rank = rank;
        faceDown = true;
    }

    /**
     * Gets the numerical value associated with the card's rank.
     *
     * @return The numerical value of the card's rank.
     */
    public int getValue() {
        return rank.getValue();
    }

    /**
     * Generates a string representation of the card.
     *
     * @return A string representation of the card.
     */
    @Override
    public String toString() {
        if (rank == Rank.joker) {
            if (suit == Suits.clubs)
                return "black_" + rank;
            return "red_" + rank;
        }
        return rank + "_of_" + suit;
    }

    /**
     * Sets the face orientation of the card to face-up.
     */
    public void setFaceUp() {
        faceDown = false;
    }

    /**
     * Checks if the card is currently face-down.
     *
     * @return true if the card is face-down, false otherwise.
     */
    public boolean isFaceDown() {
        return faceDown;
    }

    /**
     * Checks if the card has a rank value of 11 (King or Joker).
     *
     * @return true if the card is a King or Joker, false otherwise.
     */
    public boolean isKingOrJoker() {
        return getValue() == 11;
    }

    /**
     * Checks if the card has a rank value of 0 (Queen or Jack).
     *
     * @return true if the card is a Queen or Jack, false otherwise.
     */
    public boolean isQueenOrJ() {
        return getValue() == 0;
    }
}
