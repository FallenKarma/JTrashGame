package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Represents a deck of playing cards.
 */
public class DeckOfCards {
    private ArrayList<Card> deck;

    /**
     * Constructs a deck of cards with the specified number of decks.
     *
     * @param numberOfDecks The number of decks to include in the deck.
     */
    public DeckOfCards(Integer numberOfDecks) {
        this.deck = new ArrayList<>();
        for (int i = 0; i < numberOfDecks; i++) {
            for (Rank rank : Rank.values()) {
                for (Suits suit : Suits.values()) {
                    deck.add(new Card(rank, suit));
                }
            }
            // Limit the deck size, removing the last two cards in each deck
            deck = (ArrayList<Card>) deck.stream().limit(deck.size() - 2).collect(Collectors.toList());
        }
        shuffle();
    }

    /**
     * Shuffles the deck of cards.
     */
    public void shuffle() {
        Collections.shuffle(this.deck);
    }

    /**
     * Draws a single card from the deck.
     *
     * @return The drawn card.
     */
    public Card drawACard() {
        Card card = null;
        try {
            card = this.deck.remove(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return card;
    }

    /**
     * Draws a specified number of cards from the deck.
     *
     * @param numberOfCardsToDraw The number of cards to draw.
     * @return A collection of drawn cards.
     */
    public Collection<Card> drawCards(Integer numberOfCardsToDraw) {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < numberOfCardsToDraw; i++) {
            cards.add(drawACard());
        }
        return cards;
    }
}
