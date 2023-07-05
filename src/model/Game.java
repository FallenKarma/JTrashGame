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
	
	public Game (Integer numberOfPlayers,User user) {
		this.numberOfPlayers = numberOfPlayers;
		this.players = new ArrayList<Player>();
		this.lastDiscardedCard = null;
		start(user);
	}
	
	public void start(User user) {
		initializeDeck(numberOfPlayers);
		initalizePlayers(user);
		assignCards();
	}
	public void initializeDeck (Integer numberOfPlayers) {
		if (numberOfPlayers <=2 ) {
			this.deckOfCards= new DeckOfCards(1);
		}
		else {
			this.deckOfCards= new DeckOfCards(2);
		}
	}
	
	public void initalizePlayers (User user) {
		players.add(new Player(user.getNickname()));
		for (int i = 1; i < numberOfPlayers ; i++) {
			players.add(new Player());
		}
	}
	
	public void assignCards () {
		for (Player player : players) {
			player.setTableCards(deckOfCards.drawCards(player.getTableCardsNumber()));
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
