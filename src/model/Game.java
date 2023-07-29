package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

import controller.GameController;

public class Game{
	
	private Collection<Player> players;
	private Integer numberOfPlayers;
	private DeckOfCards deckOfCards;
	private Card lastDiscardedCard;
	private Boolean gameNotOver;
	
	public Game (Integer numberOfPlayers,User user) {
		this.numberOfPlayers = numberOfPlayers;
		this.players = new ArrayList<Player>();
		this.lastDiscardedCard = null;
		this.gameNotOver = true;
		initializeGame(user);
		start();
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
		lastDiscardedCard = this.deckOfCards.drawACard();
	}
	
	public void initalizePlayers (User user) {
		players.add(new Player(user.getNickname()));
		for (int i = 1; i < numberOfPlayers ; i++) {
			players.add(new Player());
		}
	}
	
	public void assignCards () {
		for (Player player : players) {
			deckOfCards.drawCards(player.getTableCardsNumber()).toArray(player.getTableCards());
		}
	}
	
	public void start() {
		while (gameNotOver) {
			playRound();
		}
	}

	public void playRound() {
		for (Player player: players) {
			int cardValue = player.getCardInHand().getValue();
			while (cardValue >0 && cardValue <= player.getTableCardsNumber() ) {
				player.switchTableCard();
			}
		}
	}
 	public Collection<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Collection<Player> players) {
		this.players = players;
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
