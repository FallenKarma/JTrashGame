package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

/**
 * Represents a card game with players, a deck of cards, and game logic.
 */
public class Game {

    private static Game game = null;

    private ArrayList<Player> players;
    private Integer numberOfPlayers;
    private DeckOfCards deckOfCards;
    private Stack<Card> wastePile;
    private Boolean gameOver;
    private int currentPlayer;

    /**
     * Creates a new instance of the game with the specified number of players and a user.
     * Only one instance of the game is allowed.
     *
     * @param numberOfPlayers The number of players in the game.
     * @param user            The user representing the human player.
     * @return The created or existing instance of the game.
     */
    public static Game getGame(Integer numberOfPlayers, User user) {
        if (game == null) {
            game = new Game(numberOfPlayers, user);
        }
        else
        	game.resetGame(numberOfPlayers, user);
        return game;
    }

    private Game(Integer numberOfPlayers, User user) {
        this.numberOfPlayers = numberOfPlayers;
        this.players = new ArrayList<>();
        this.wastePile = new Stack<>();
        this.gameOver = false;
        initializeGame(user);
    }

    /**
     * Initializes the game by setting up the deck, discard pile, players, and assigning cards.
     *
     * @param user The user representing one of the players.
     */
    public void initializeGame(User user) {
        initializeDeck(numberOfPlayers);
        initializeDiscardPile();
        initalizePlayers(user);
        assignCards();
    }
    
    /**
     * Resets the game by setting up the deck, discard pile, players, and assigning cards.
     *
     * @param user The user representing one of the players.
     */
    public void resetGame(Integer numberOfPlayers, User user) {
    	this.numberOfPlayers = numberOfPlayers;
    	initializeGame(user);
    }

    /**
     * Initializes the deck based on the number of players.
     *
     * @param numberOfPlayers The number of players in the game.
     */
    public void initializeDeck(Integer numberOfPlayers) {
        if (numberOfPlayers <= 2) {
            this.deckOfCards = new DeckOfCards(1);
        } else {
            this.deckOfCards = new DeckOfCards(2);
        }
    }

    /**
     * Initializes the discard pile with a card drawn from the deck.
     */
    public void initializeDiscardPile() {
        wastePile.removeAllElements();
        wastePile.add(deckOfCards.drawACard());
    }

    /**
     * Initializes the players, with one player being the specified user and others as default players.
     *
     * @param user The user representing one of the players.
     */
    public void initalizePlayers(User user) {
    	players.clear();
        players.add(new Player(user));
        this.currentPlayer = 0;
        for (int i = 1; i < numberOfPlayers; i++) {
            players.add(new Player());
        }
    }

    /**
     * Assigns cards to each player.
     */
    public void assignCards() {
        for (Player player : players) {
            player.setTableCards(deckOfCards.drawCards(player.getTableCardsNumber()).toArray(player.getTableCards()));
        }
    }

    /**
     * The current player draws a card from the waste pile.
     */
    public void currentPlayerDrawsFromWastePile() {
        getCurrentPlayer().draw(wastePile.pop());
    }

    /**
     * The current player draws a card from the deck.
     */
    public void currentPlayerDrawsFromDeck() {
        getCurrentPlayer().draw(deckOfCards.drawACard());
    }

    /**
     * The current player discards a card to the waste pile.
     */
    public void currentPlayerDiscards() {
        wastePile.push(getCurrentPlayer().discard());
    }

    /**
     * Moves to the next player in the turn order.
     */
    public void nextPlayer() {
        if (currentPlayer < numberOfPlayers - 1)
            currentPlayer += 1;
        else
            currentPlayer = 0;
    }

    /**
     * Gets the current player.
     *
     * @return The current player.
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    /**
     * Gets the next player in the turn order.
     *
     * @return The next player.
     */
    public Player getNextPlayer() {
        if (currentPlayer < numberOfPlayers - 1)
            return players.get(currentPlayer + 1);
        else
            return players.get(0);
    }

    /**
     * Gets the current player's number in the turn order.
     *
     * @return The current player's number.
     */
    public int getCurrentPlayerNumber() {
        return currentPlayer;
    }

    /**
     * Ends the current round, updates the winning player, and initializes the table for the next round.
     */
    public void nextRound() {
        getCurrentPlayer().wonRound();
        initializeTable();
    }

    /**
     * Initializes the game table, including the deck, discard pile,
     * player table cards, and assigns cards to players.
     */
    private void initializeTable() {
        initializeDeck(numberOfPlayers);
        initializeDiscardPile();
        initializePlayersTableCards();
        assignCards();
    }

    /**
     * Initializes the table cards for each player by resetting their
     * current table card state.
     */
    private void initializePlayersTableCards() {
        for (Player player : players)
            player.resetTableCards();
    }

    /**
     * Gets the collection of players in the game.
     *
     * @return The collection of players.
     */
    public Collection<Player> getPlayers() {
        return players;
    }

    /**
     * Sets the players in the game.
     *
     * @param players The collection of players.
     */
    public void setPlayers(Collection<Player> players) {
        this.players = (ArrayList<Player>) players;
    }

    /**
     * Gets the waste pile stack.
     *
     * @return The waste pile stack.
     */
    public Stack<Card> getWastePile() { 
        return wastePile;
    }

    /**
     * Sets the waste pile stack.
     *
     * @param wastePile The waste pile stack.
     */
    public void setWastePile(Stack<Card> wastePile) {
        this.wastePile = wastePile;
    }

    /**
     * Gets the number of players in the game.
     *
     * @return The number of players.
     */
    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Sets the number of players in the game.
     *
     * @param numberOfPlayers The number of players.
     */
    public void setNumberOfPlayers(Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Gets the deck of cards used in the game.
     *
     * @return The deck of cards.
     */
    public DeckOfCards getDeckOfCards() {
        return deckOfCards;
    }

    /**
     * Sets the deck of cards used in the game.
     *
     * @param deckOfCards The deck of cards.
     */
    public void setdeckOfCards(DeckOfCards deckOfCards) {
        this.deckOfCards = deckOfCards;
    }
    
    /**
     * Retrieves the human player from the list of players.
     *
     * @return The human player.
     */
    public Player getHumanPlayer() {
    	return players.get(0);
    }
}
