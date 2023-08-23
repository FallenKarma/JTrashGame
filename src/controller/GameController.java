package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Observable;
import java.util.Observer;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import model.Card;
import model.Game;
import model.Player;
import model.User;

public class GameController implements Initializable {
	
	private Game game;
	private final Integer cardSpotsPerPlayer = 10;
	
	List<ImageView> imageViews;
	
	
	@FXML
	Button wastePileButton;
	@FXML
	Button deckButton;
	
	
	@FXML
	GridPane cardsPane;
	
	@FXML
	ImageView wastePile;
	
	@FXML
	ImageView cardInHand;
	
	@FXML
	Label userMessages;
	
	@FXML
	ImageView userSlot1;
	@FXML
	ImageView userSlot2;
	@FXML
	ImageView userSlot3;
	@FXML
	ImageView userSlot4;
	@FXML
	ImageView userSlot5;
	@FXML
	ImageView userSlot6;
	@FXML
	ImageView userSlot7;
	@FXML
	ImageView userSlot8;
	@FXML 
	ImageView userSlot9;
	@FXML
	ImageView userSlot10;
	
	@FXML
	ImageView p1slot1;
	@FXML
	ImageView p1slot2;
	@FXML
	ImageView p1slot3;
	@FXML
	ImageView p1slot4;
	@FXML
	ImageView p1slot5;
	@FXML
	ImageView p1slot6;
	@FXML
	ImageView p1slot7;
	@FXML
	ImageView p1slot8;
	@FXML
	ImageView p1slot9;
	@FXML
	ImageView p1slot10;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		imageViews = new ArrayList<>();
		intializeImageViews();
	}
	
	public void setUpGame (Integer numberOfPlayers,User user) {
		game = new Game (numberOfPlayers,user );
		int i = 0;
		for (Player player: game.getPlayers() ) {
			assignCards (player,i);
			i++;
		}
		start();
	}
	
	
	private void start() {
		drawPhase();
		gamePhase();
		endRoundPhase();
	}

	private void endRoundPhase() {
		// TODO Auto-generated method stub
		
	}

	private void gamePhase() {
		
	}

	private void drawPhase() {
		if (!game.getCurrentPlayer().isBot()) {
			enableGameButtons();
		}
		else {
			drawFromDeck();
		}
	}

	public void assignCards (Player player,int playerNumber) {
		for (int i=0; i < player.getTableCardsNumber(); i++) {
			int position = playerNumber * cardSpotsPerPlayer + i;
			imageViews.get(position).setImage(CardImagesLoader.getBackOfCardImage());
		}
		wastePile.setImage(CardImagesLoader.getImageFromCardName(game.getWastePile().peek().toString()));
	}
	
	
	public void drawFromDeck () {
		Player currentPlayer = game.getCurrentPlayer();
		currentPlayer.setCardInHand(game.getDeckOfCards().drawACard());
    	if (!currentPlayer.isBot()) {
    		disableGameButtons();
    		cardInHand.setImage(CardImagesLoader.getImageFromCardName(currentPlayer.getCardInHand().toString()));
    	}
	}
	
	private void enableGameButtons() {
		wastePileButton.setDisable(false);
		deckButton.setDisable(false);			
	}
	
	private void disableGameButtons() {
		wastePileButton.setDisable(true);
		deckButton.setDisable(true);
	}

	public void drawFromWastePile() {
		disableGameButtons();
    	Player currentPlayer = game.getCurrentPlayer();
    	try {
    		Card popped = game.getWastePile().pop();
    		currentPlayer.setCardInHand(popped);
    		setWastePileView();
    		cardInHand.setImage(CardImagesLoader.getImageFromCardName(currentPlayer.getCardInHand().toString()));   		
    	}
    	catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Errore");
			alert.setHeaderText("La pila di carte scartate è vuota!");
			alert.setContentText("Per continuare a giocare pesca dal mazzo");
			alert.showAndWait();
    	}
	}
	
	private void setWastePileView() {
		try {
			wastePile.setImage(CardImagesLoader.getImageFromCardName(game.getWastePile().peek().toString()));
		}
		catch (Exception e) {
			wastePile.setImage(null);
			wastePileButton.setDisable(true);
		}
	}

	
	public void intializeImageViews () {
		
		this.imageViews = new ArrayList<>();
		
		imageViews.add(userSlot1);
		imageViews.add(userSlot2);
		imageViews.add(userSlot3);
		imageViews.add(userSlot4);
		imageViews.add(userSlot5);
		imageViews.add(userSlot6);
		imageViews.add(userSlot7);
		imageViews.add(userSlot8);
		imageViews.add(userSlot9);
		imageViews.add(userSlot10);
		imageViews.add(p1slot1);
		imageViews.add(p1slot2);
		imageViews.add(p1slot3);
		imageViews.add(p1slot4);
		imageViews.add(p1slot5);
		imageViews.add(p1slot6);
		imageViews.add(p1slot7);
		imageViews.add(p1slot8);
		imageViews.add(p1slot9);
		imageViews.add(p1slot10);
		
		
	}





}
