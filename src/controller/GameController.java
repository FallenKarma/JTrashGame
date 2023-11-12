package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
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
import model.Rank;
import model.Suits;
import model.User;
import utilites.CardImagesLoader;
import utilites.LoggerUtil;

public class GameController implements Initializable {
	
	private Game game;
	private final Integer cardSpotsPerPlayer = 10;
	private final Integer deckImageViewPosition = -1;
	private final Integer cardInHandImageViewPosition = -2;
	
	private static String userTurnOverMessage = "YOUR TURN IS OVER";
	private static String userPlayerTurnMessage = "NOW IS YOUR TURN";
	private static String nextPlayerTurnMessage = "NEXT PLAYER TURN";
	
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
	ImageView movingDeckImage;


	
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
		Card currentHandCard = currentPlayer.getCardInHand();
		if (currentHandCard != null) {
			//If the card in hand is not a figure, the player switches it
			if (currentPlayer.canPlayHandCard()) {
				updateSingleCardTableView(currentHandCard.getValue() - 1);
				cardsSwitchAnimation(cardInHand, currentHandCard.getValue() - 1);
				currentPlayer.switchTableCard();
			}
			// If it is a figure, if it's a king he can choose where to put it;
			// otherwise he has to discard
			else {
				if (currentHandCard.isKing()) {
					kingSpecialPlay();
				}
				else {
					playerDiscard();
					nextTurn();
				}
			}
		}
	}
	
	private void nextTurn() {
		showChangeOfTurnTransition();
		game.nextPlayer();
	}
	
	private void kingSpecialPlay() {
		makeCardsClickable();
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

	

	private void makeCardsClickable() {
		for (int i = 0; i < imageViews.size(); i ++) {
			this.imageViews.get(i).setOnMouseClicked( e-> {
				ImageView clickedImageView = (ImageView) e.getSource();
				specialKingSwitch ( imageViews.indexOf(clickedImageView) );
			});
		}
	}



	private void kingSwitchAnimation(ImageView clickedImageView, ImageView cardInHand) {
		Double firstImageAngle = clickedImageView.getRotate();
		Double secondImageAngle = cardInHand.getRotate();
		
		TranslateTransition translateFirstImage = getTranslateAnimation(clickedImageView, cardInHand);
		TranslateTransition translateSecondImage = getTranslateAnimation(cardInHand, clickedImageView);
		RotateTransition rotateFirstImage = getRotateAnimation(clickedImageView, cardInHand);
		RotateTransition rotateSecondImage = getRotateAnimation(cardInHand, clickedImageView);
		
		ParallelTransition pt = new ParallelTransition();
		pt.setDelay(Duration.millis(1000));
		pt.getChildren().add(translateFirstImage);
		pt.getChildren().add(translateSecondImage);
		pt.getChildren().add(rotateFirstImage);
		pt.getChildren().add(rotateSecondImage);
		pt.setOnFinished(e -> {
			switchImages(clickedImageView, cardInHand, firstImageAngle, secondImageAngle);
			gamePhase();
		});
		pt.play();
	}

	private void specialKingSwitch(Integer clickedImagePosition) {
		Card chosenCard = game.getCurrentPlayer().getTableCards()[clickedImagePosition];
		if ( !chosenCard.isFaceDown() ) {
			showInvalidCardChosenError();
		}
		else {
			updateSingleCardTableView(clickedImagePosition);
			game.getCurrentPlayer().specialKingSwitch(clickedImagePosition);
			kingSwitchAnimation(getImageViewFromCardValue(clickedImagePosition), cardInHand);
		}
	}
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
		playerDiscardAnimation();
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
		drawFromDeckAnimation();
	}
	
	private void drawFromDeckAnimation() {
		
		movingDeckImage.setImage(CardImagesLoader.getImageFromCardName(game.getCurrentPlayer().getCardInHand().toString()));
		
		Double deckImageAngle = movingDeckImage.getRotate();
		Double cardInHandAngle = cardInHand.getRotate();
		
		TranslateTransition translateAnimation = getTranslateAnimation(movingDeckImage, cardInHand);
		RotateTransition rotateTransition = getRotateAnimation(movingDeckImage, cardInHand);
		ParallelTransition pt = new ParallelTransition();
		pt.getChildren().add(translateAnimation);
		pt.getChildren().add(rotateTransition);
		pt.setOnFinished(e->{
			switchImages(movingDeckImage, cardInHand,deckImageAngle,cardInHandAngle);
			movingDeckImage.setImage(null);
			gamePhase();
		});
		pt.play();

	}


	public void drawFromWastePile() {
		disableGameButtons();
    	game.currentPlayerDrawsFromWastePile();
    	drawFromWastePileAnimation();
	}
	

	private void updatetWastePileView() {
		if (game.getWastePile().isEmpty()) {
			wastePile.setImage(null);
		}
		else
			wastePile.setImage(CardImagesLoader.getImageFromCardName(game.getWastePile().peek().toString()));
	}

	public void updateGameView() {
			updateCardInHandView();
			updatetWastePileView();
	}
	
	private void updateSingleCardTableView(int position) {
		Card tableCard = game.getCurrentPlayer().getTableCards()[position];
		getImageViewFromCardValue(position).setImage(CardImagesLoader.getImageFromCardName(tableCard.toString()));
	}


	public void updateCardInHandView() {
		Card cardInHandEntity = game.getCurrentPlayer().getCardInHand();
		if (cardInHandEntity != null) {
			cardInHand.setImage(CardImagesLoader.getImageFromCardName(cardInHandEntity.toString()));
		}
		else 
			cardInHand.setImage(null);
	}
	
	private void drawFromWastePileAnimation() {
		Double wastePileAngle = wastePile.getRotate();
		Double cardInHandAngle = cardInHand.getRotate();
		
		RotateTransition rotateAnimation = getRotateAnimation(wastePile, cardInHand);
		TranslateTransition translateAnimation = getTranslateAnimation(wastePile, cardInHand);
		ParallelTransition pt = new ParallelTransition();
		pt.getChildren().add(rotateAnimation);
		pt.getChildren().add(translateAnimation);
		pt.setOnFinished(e->{
			switchImages(wastePile, cardInHand,wastePileAngle,cardInHandAngle);
			gamePhase();
		});
		pt.play();

	}
	
	private void playerDiscardAnimation() {
		Double cardInHandAngle = cardInHand.getRotate();
		Double wastePileAngle = wastePile.getRotate();
		RotateTransition rotateAnimation = getRotateAnimation(cardInHand, wastePile);
		TranslateTransition translateAnimation = getTranslateAnimation(cardInHand, wastePile);
		ParallelTransition pt = new ParallelTransition();
		pt.getChildren().add(rotateAnimation);
		pt.getChildren().add(translateAnimation);
		pt.setOnFinished(e->{
			switchImages(wastePile, cardInHand,wastePileAngle, cardInHandAngle);
			updateCardInHandView();
		});
		pt.play();
	}
	
	public void cardsSwitchAnimation (ImageView image,Integer imageViewPosition) {

		ImageView destinationImage = getImageViewFromCardValue(imageViewPosition);
		Double firstImageAngle = image.getRotate();
		Double secondImageAngle = destinationImage.getRotate();
		
		TranslateTransition translateFirstImage = getTranslateAnimation(image, destinationImage);
		TranslateTransition translateSecondImage = getTranslateAnimation(destinationImage, image);
		RotateTransition rotateFirstImage = getRotateAnimation(image, destinationImage);
		RotateTransition rotateSecondImage = getRotateAnimation(destinationImage, image);
		
		ParallelTransition pt = new ParallelTransition();
		pt.setDelay(Duration.millis(1000));
		pt.getChildren().add(translateFirstImage);
		pt.getChildren().add(translateSecondImage);
		pt.getChildren().add(rotateFirstImage);
		pt.getChildren().add(rotateSecondImage);
		pt.setOnFinished(e -> {
			ImageView destImage = getImageViewFromCardValue(imageViewPosition);
			switchImages(image, destImage, firstImageAngle, secondImageAngle);
			gamePhase();
		});
		pt.play();
	}
	
	public TranslateTransition getTranslateAnimation(ImageView movingImage, ImageView destinationImage) {
		double startX = destinationImage.getBoundsInParent().getCenterX();
		double startY = destinationImage.getBoundsInParent().getCenterY();
		double destinationX = movingImage.getBoundsInParent().getCenterX();
		double destinationY = movingImage.getBoundsInParent().getCenterY();
		TranslateTransition translate = new TranslateTransition(Duration.millis(1000), movingImage);
		translate.setByX( startX - destinationX );
		translate.setByY( startY - destinationY );
		return translate;
	}
	
	
	private void switchImages(ImageView image, ImageView destinationImage, Double firstImageAngle, Double secondImageAngle) {
		Image temporary = image.getImage();
		
		image.setTranslateX(0);
		image.setTranslateY(0);
		image.setRotate(firstImageAngle);
		image.setImage(destinationImage.getImage());
		
		destinationImage.setTranslateX(0);
		destinationImage.setTranslateY(0);
		destinationImage.setRotate(secondImageAngle);
		destinationImage.setImage(temporary);
		
	}

	
	public RotateTransition getRotateAnimation(ImageView movingImage, ImageView destinationImage) {
		RotateTransition rotate = new RotateTransition(Duration.millis(1000), movingImage);
		rotate.setToAngle(destinationImage.getRotate());
		return rotate;
	}
	
	public ImageView getImageViewFromCardValue (Integer cardValue) {
		if (cardValue.equals(deckImageViewPosition)) 
			return movingDeckImage;
		if (cardValue.equals(cardInHandImageViewPosition)) {
			return cardInHand;
		}
		int position = game.getCurrentPlayerNumber() * cardSpotsPerPlayer + cardValue;
		return imageViews.get(position);
	}
	
	public void showInvalidCardChosenError() {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Errore");
		alert.setHeaderText("La carta che hai selezionato non è valido!");
		alert.setContentText("Scegli una carta che non sia scoperta!");
		alert.showAndWait();
	}
	
	public void showChangeOfTurnTransition () {
		if ( !game.getCurrentPlayer().isBot()) {
			userMessages.setText(userTurnOverMessage);
		}
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), userMessages);
        fadeTransition.setFromValue(0.0); // Fully visible
        fadeTransition.setToValue(1.0); 
        fadeTransition.setOnFinished(e -> {
        	 userMessagesFadeOutTransition();
        });
        fadeTransition.play();
        
	}
	public void userMessagesFadeOutTransition() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), userMessages);
        fadeTransition.setFromValue(1.0); 
        fadeTransition.setToValue(0.0); 
        fadeTransition.setOnFinished(e -> {
        	 drawPhase();
        });
        fadeTransition.play();
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
