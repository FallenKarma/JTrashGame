package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;



public class Game {
	
	private static Game game = null;
	
	private ArrayList<Player> players;
	private Integer numberOfPlayers;
	private DeckOfCards deckOfCards;
	private Stack<Card> wastePile;
	private Boolean gameOver;
	private int currentPlayer;
	
	public static Game createGame (Integer numberOfPlayers,User user) {
		if (game==null) {
			game = new Game(numberOfPlayers, user);
		}
		return game;
	}
	
	public static Game getInstance () {
		return game;
	}
	
	private Game (Integer numberOfPlayers,User user) {
		this.numberOfPlayers = numberOfPlayers;
		this.players = new ArrayList<>();
		this.wastePile = new Stack<>();
		this.gameOver = false;
		initializeGame(user);
	}
	
	public void initializeGame(User user) {
		initializeDeckAndDiscardPile(numberOfPlayers);
		initalizePlayers(user);
		assignCards();
	}
	public void initializeDeckAndDiscardPile (Integer numberOfPlayers) {
		if (numberOfPlayers <=2 ) {
			this.deckOfCards = new DeckOfCards(1);
		}
		else {
			this.deckOfCards = new DeckOfCards(2);
		}
		///////
		wastePile.add(new Card(Rank.king, Suits.clubs));
	}
	
	public void initalizePlayers (User user) {
		players.add( (Player) user);
		this.currentPlayer = 0;
		for (int i = 1; i < numberOfPlayers ; i++) {
			players.add(new Player());
		}
	}
	
	public void assignCards () {
		for (Player player : players) {
			player.setTableCards(deckOfCards.drawCards(player.getTableCardsNumber()).toArray(player.getTableCards()));
		}
	}
	
	public void currentPlayerDrawsFromWastePile() {
		getCurrentPlayer().draw(wastePile.pop());
	}
	
	public void currentPlayerDrawsFromDeck() {
		getCurrentPlayer().draw(new Card(Rank.king, Suits.clubs));
	}
	

	public void currentPlayerDiscards() {
		wastePile.push(getCurrentPlayer().discard());
	}
	
	public void nextPlayer() {
		if (currentPlayer<numberOfPlayers-1)
			currentPlayer += 1;
		else
			currentPlayer = 0;
	}
	

	public Player getCurrentPlayer() {
		return players.get(currentPlayer);
	}
	
	public Player getNextPlayer () {
		if (currentPlayer < numberOfPlayers - 1)
			return players.get(currentPlayer + 1);
		else
			return players.get(0);
	}

	public int getCurrentPlayerNumber() {
		return currentPlayer;
	}

	public void nextRound() {
		getCurrentPlayer().wonRound();
		restoreTable();
	}
	
 	private void restoreTable() {
 		initializeDeckAndDiscardPile(numberOfPlayers);
 		initializePlayersTableCards();
		assignCards();
	}

	private void initializePlayersTableCards() {
		for (Player player: players)
			player.resetTableCards();
	}	

	public Collection<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Collection<Player> players) {
		this.players = (ArrayList<Player>) players;
	}

	

	public Stack<Card> getWastePile() {
		return wastePile;
	}

	public void setWastePile(Stack<Card> wastePile) {
		this.wastePile = wastePile;
	}

	public Integer getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public void setNumberOfPlayers(Integer numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	public DeckOfCards getDeckOfCards() {
		return deckOfCards;
	}

	public void setdeckOfCards(DeckOfCards deckOfCards) {
		this.deckOfCards = deckOfCards;
	}

	
	
	
}
