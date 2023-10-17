package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import model.Card;
import model.Game;
import model.Player;
import model.User;
import utilites.CardImagesLoader;
import java.lang.Math;

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
		startRound();
	}
	
	
	private void startRound() {
		drawPhase();
	}

	private void endRoundPhase() {
		if (game.roundWon()) {
			//TO DO
		}
		else {
			game.nextPlayer();
			startRound();
		}
	}

	private void drawPhase() {
		if (!game.getCurrentPlayer().isBot()) {
			enableGameButtons();
		}
		else {
			drawFromDeck();
		}
	}
	
	private void gamePhase() {
		Player currentPlayer = game.getCurrentPlayer();
		Card card = currentPlayer.getCardInHand();
		while (card!=null) {
			final Card currentHandCard = card;
			if (!currentPlayer.switchTableCard()) {
				if (currentPlayer.getCardInHand().getValue()==11) {
					///
					/// IMPLEMENTARE KING 
					///
				}
				else
					playerDiscard();
			}
			updateSingleCardTableView(currentHandCard.getValue()-1);
			updateCardInHandView();
			card = currentPlayer.getCardInHand();
		}
	}

//	private void makeCardsClickable() {
//		for (int i=0; i<10; i++) {
//			this.imageViews.get(i).setOnMouseClicked(specialKingSwitch());
//		}
//	}
//
//
//
//	private EventHandler<? super MouseEvent> specialKingSwitch() {
//		game.getCurrentPlayer().specialKingSwitch(position);
//		updateViewAndWait();
//		makeCardsNonClickable();
//		gamePhase();
//		return null;
//	}
//
//	private void makeCardsNonClickable() {
//		for (int i=0; i<10; i++) {
//			this.imageViews.get(i).setOnMouseClicked(null);
//		}
//	}


	private void endGamePhase() {
		updateGameView();
		endRoundPhase();
	}

	public void playerDiscard() {
		game.currentPlayerDiscards();
		updateCardInHandView();
		updatetWastePileView();
	}

	public void assignCards (Player player,int playerNumber) {
		for (int i=0; i < player.getTableCardsNumber(); i++) {
			int position = playerNumber * cardSpotsPerPlayer + i;
			imageViews.get(position).setImage(CardImagesLoader.getBackOfCardImage());
		}
		wastePile.setImage(CardImagesLoader.getImageFromCardName(game.getWastePile().peek().toString()));
	}
	
	
	
	private void enableGameButtons() {
		wastePileButton.setDisable(false);
		deckButton.setDisable(false);			
	}
	
	private void disableGameButtons() {
		wastePileButton.setDisable(true);
		deckButton.setDisable(true);
	}

	public void drawFromDeck () {
		game.currentPlayerDrawsFromDeck();
		updateCardInHandView();
		disableGameButtons();
		gamePhase();
	}
	
	public void drawFromWastePile() {
//		cardsSwitchAnimation(wastePile, 5);
		disableGameButtons();
    	game.currentPlayerDrawsFromWastePile();
		updateCardInHandView();
		updatetWastePileView();
    	gamePhase();
	}
	
	private void updatetWastePileView() {
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(1), e->{
					try {
						wastePile.setImage(CardImagesLoader.getImageFromCardName(game.getWastePile().peek().toString()));
					}
					catch (Exception exception) {
						System.out.println("Errore: " + exception.getMessage());
						wastePileButton.setDisable(true);
					}
				})
		);
		timeline.play();
	}

	public void updateGameView() {
			updateCardInHandView();
			updateTableView();
			updatetWastePileView();
	}
	
	private void updateSingleCardTableView(int position) {
		if (position>=0) {
			
			Timeline timeline = new Timeline(
					new KeyFrame(Duration.seconds(1), e->{
						Card currentCard = game.getCurrentPlayer().getTableCards()[position];
						if (!currentCard.isFaceDown()) {
							imageViews.get(position).setImage(CardImagesLoader.getImageFromCardName(currentCard.toString()));
						}
					})
					);
			timeline.play();
		}
	}

	
	
	private void updateTableView() {
    	Card currentCard = null;
		for (int i=0; i < game.getCurrentPlayer().getTableCardsNumber(); i++) {
			int position = game.getCurrentPlayerNumber() * cardSpotsPerPlayer + i;
			currentCard = game.getCurrentPlayer().getTableCards()[i];
			if (!currentCard.isFaceDown()) {
	    			Card card = game.getCurrentPlayer().getTableCards()[i];
	    			imageViews.get(position).setImage(CardImagesLoader.getImageFromCardName(card.toString()));
			}
		}
	}

	public void updateCardInHandView() {
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(1), e->{
						Card cardInHandEntity = game.getCurrentPlayer().getCardInHand();
						if (cardInHandEntity!=null) {
							cardInHand.setImage(CardImagesLoader.getImageFromCardName(cardInHandEntity.toString()));
							cardInHand.setVisible(true);
						}
						else {
							cardInHand.setVisible(false);
						}
					}
				)
		);
		timeline.play();
	}
	
	
	public void cardsSwitchAnimation (ImageView image,Integer imageViewPosition) {
		double xPosition = imageViews.get(imageViewPosition).getBoundsInParent().getMinX();
		double yPosition = imageViews.get(imageViewPosition).getBoundsInParent().getMinY();
		TranslateTransition translate= new TranslateTransition(Duration.millis(1000), image);
		translate.setToX( xPosition-image.getLayoutX() );
		translate.setToY( yPosition-image.getLayoutY() );
		translate.play();
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
