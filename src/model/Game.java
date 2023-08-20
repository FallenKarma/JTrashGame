package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Stack;

import controller.GameController;

public class Game {
	
	private ArrayList<Player> players;
	private Integer numberOfPlayers;
	private DeckOfCards deckOfCards;
	private Stack<Card> wastePile;
	private Boolean gameOver;
	private int currentPlayer;
	
	public Game (Integer numberOfPlayers,User user) {
		this.numberOfPlayers = numberOfPlayers;
		this.players = new ArrayList<Player>();
		this.wastePile = new Stack<>();
		this.gameOver = false;
		initializeGame(user);
	}
	
	public void initializeGame(User user) {
		initializeDeck(numberOfPlayers);
		initalizePlayers(user);
		assignCards();
	}
	public void initializeDeck (Integer numberOfPlayers) {
		if (numberOfPlayers <=2 ) {
			this.deckOfCards = new DeckOfCards(1);
		}
		else {
			this.deckOfCards = new DeckOfCards(2);
		}
		wastePile.add(this.deckOfCards.drawACard());
	}
	
	public void initalizePlayers (User user) {
		players.add(new Player(user.getNickname()));
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
	
	public void NextRound() {
		currentPlayer += 1;
		roundWonCheck();
	}

	public Player getCurrentPlayer() {
		return players.get(currentPlayer);
	}


	private void roundWonCheck() {
		for (Card card: getCurrentPlayer().getTableCards() ) {
			if (card.isFaceDown()) 
				return ;
		}
		gameOver = true;
		///
		//IMPLEMENTARE FUNZIONE PER GAMEWONCHECK
		//IMPLEMENTA FUNZIONE CHE MOSTRA MESSAGGIO DI VITTORIA O SCONFITTA
		///
	}
	
 	private void restoreTable() {
		initializeDeck(numberOfPlayers);
		assignCards();
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
