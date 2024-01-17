package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Card;
import model.Game;
import model.Player;
import model.User;
import utilites.AudioManager;
import utilites.CardImagesLoader;
import utilites.LoggerUtil;

/**
 * Controller class for the game interface.
 */
public class GameController implements Initializable {
	
	private Game game;
	
	private static final Integer CARDSPOTSPERPLAYER = 10;
	private static final Integer deckImageViewPosition = -1;
	private static final Integer userCardInHandImageViewPosition = -2;
	
	private static final String userTurnOverMessage = "YOUR TURN IS OVER";
	private static final String userPlayerTurnMessage = "NOW IS YOUR TURN";
	private static final String nextPlayerTurnMessage = "NEXT PLAYER TURN";
	private static final String roundLostMessage = "YOU LOST THE ROUND!";
	private static final String roundWonMessage = "YOU WON THE ROUND!";
	private static final String gameLostMessage = "YOU LOST THE GAME!";
	private static final String gameWonMessage = "YOU WON THE GAME!";
	
	private ImageView currentPlayerCardInHandImageView;
	
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
	ImageView secondWastePileCard;
	
	@FXML
	ImageView userCardInHand;
	
	@FXML
	ImageView p1cardInHand;
	
	@FXML
	ImageView p2cardInHand;
	
	@FXML
	ImageView p3cardInHand;
	
	@FXML
	ImageView movingDeckImage;

	@FXML
	ImageView deck;
	
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
	
	@FXML
	ImageView p2slot1;
	@FXML
	ImageView p2slot2;
	@FXML
	ImageView p2slot3;
	@FXML
	ImageView p2slot4;
	@FXML
	ImageView p2slot5;
	@FXML
	ImageView p2slot6;
	@FXML
	ImageView p2slot7;
	@FXML
	ImageView p2slot8;
	@FXML
	ImageView p2slot9;
	@FXML
	ImageView p2slot10;
	
	@FXML
	ImageView p3slot1;
	@FXML
	ImageView p3slot2;
	@FXML
	ImageView p3slot3;
	@FXML
	ImageView p3slot4;
	@FXML
	ImageView p3slot5;
	@FXML
	ImageView p3slot6;
	@FXML
	ImageView p3slot7;
	@FXML
	ImageView p3slot8;
	@FXML
	ImageView p3slot9;
	@FXML
	ImageView p3slot10;
	

    /**
     * Initializes the controller.
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		imageViews = new ArrayList<>();
		intializeImageViews();
	}
	
    /**
     * Sets up the game with the specified number of players and user.
     *
     * @param numberOfPlayers Number of players in the game.
     * @param user            The user playing the game.
     */
	public void setUpGame (Integer numberOfPlayers,User user) {
		game = Game.getGame(numberOfPlayers, user) ;
		startRound();
	}
	
    /**
     * Initiates the start of a new round in the game.
     */
	private void startRound() {
		resetImageViews();
		assignCards();
		startRoundAnimation();
		drawPhase();
	}


    /**
     * Initiates the draw phase of the current player.
     */
	private void drawPhase() {
		if (!game.isRunning())
			return;
		if (!game.getCurrentPlayer().isBot()) {
			enableGameButtons();
		}
		else {
			botDrawPhase();
		}
	}
	
    /**
     * Draw phase for bot players
     */
	private void botDrawPhase() {
		if (game.getCurrentPlayer().canPlayCard(game.getWastePile().peek())) {
			drawFromWastePile();
		}
		else
			drawFromDeck();
	}

    /**
     * Initiates the game phase for the current player.
     */
	private void gamePhase() {
		if (!game.isRunning())
			return;
		Player currentPlayer = game.getCurrentPlayer();
		Card currentHandCard = currentPlayer.getCardInHand();
		if (currentHandCard != null) {
			//If the card in hand is not a figure, the player switches it
			if (currentPlayer.canPlayHandCard()) {
				updateSingleCardTableView(currentHandCard.getValue() - 1);
				switchCards();
			}
			// If it is a figure, if it's a king he can choose where to put it;
			// otherwise he has to discard
			else {
				if (currentHandCard.isKingOrJoker()) {
					kingOrJokerSpecialPlay();
				}
				else {
					playerDiscard();
				}
			}
		}
	}
	
    /**
     * Initiates the next round in the game.
     */
	private void nextRound() {
		if (game.getCurrentPlayer().wonGame()) {
			game.getHumanPlayer().addLastGameToStats();
			endOfGameAnimation();
		}
		else {
			endOfRoundAnimation();
			showRoundResult();
		}
	}
	

    /**
     * Initiates the card switch animation for the current player.
     */
	private void switchCards() {
		Card currentHandCard = game.getCurrentPlayer().getCardInHand();
		cardsSwitchAnimation(currentPlayerCardInHandImageView, currentHandCard.getValue() - 1);
	}

	/**
	 * Retrieves the ImageView for the current player's card in hand.
	 *
	 * @return The ImageView for the current player's card in hand.
	 */
	private ImageView getCurrentPlayerCardInHandImageView() {
		Integer currentPlayerNumber = game.getCurrentPlayerNumber();
		switch (currentPlayerNumber) {
		  case 0:
		    return userCardInHand;
		  case 1:
		    return p1cardInHand;
		  case 2:
			  return p2cardInHand;
		  case 3:
			  return p3cardInHand;
	      default:
	    	  return userCardInHand;
		}
	}

	/**
	 * Initiates the next turn in the game.
	 */
	private void nextTurn() {
		showChangeOfTurnTransition();
		game.nextPlayer();
		currentPlayerCardInHandImageView = getCurrentPlayerCardInHandImageView();
	}
	
	/**
	 * Initiates the special play for a king or joker card.
	 */
	private void kingOrJokerSpecialPlay() {
		if (game.getCurrentPlayer().isBot()) {
			Integer firstFaceDownCardPosition = game.getCurrentPlayer().getFirstFaceDownCard();
			updateSingleCardTableView(firstFaceDownCardPosition);
			game.getCurrentPlayer().specialKingSwitch(firstFaceDownCardPosition);
			kingSwitchAnimation(getImageViewFromCardValue(firstFaceDownCardPosition), currentPlayerCardInHandImageView);
		}
		else
			makeCardsClickable();
	}

	
	/**
	 * Makes the cards clickable, setting up a callback for each card's click event.
	 */
	private void makeCardsClickable() {
		for (int i = 0; i < imageViews.size(); i ++) {
			this.imageViews.get(i).setOnMouseClicked( e-> {
				makeCardsNonClickable();
				ImageView clickedImageView = (ImageView) e.getSource();
				specialKingSwitch ( imageViews.indexOf(clickedImageView) );
			});
		}
	}


	/**
	 * Initiates the king switch animation for the clicked and player's card in hand ImageViews.
	 *
	 * @param clickedImageView    The ImageView of the clicked card.
	 * @param userCardInHand      The ImageView of the player's card in hand.
	 */
	private void kingSwitchAnimation(ImageView clickedImageView, ImageView userCardInHand) {
		Double firstImageAngle = clickedImageView.getRotate();
		Double secondImageAngle = userCardInHand.getRotate();
		
		TranslateTransition translateFirstImage = getTranslateAnimation(clickedImageView, userCardInHand);
		TranslateTransition translateSecondImage = getTranslateAnimation(userCardInHand, clickedImageView);
		RotateTransition rotateFirstImage = getRotateAnimation(clickedImageView, userCardInHand);
		RotateTransition rotateSecondImage = getRotateAnimation(userCardInHand, clickedImageView);
		
		ParallelTransition pt = new ParallelTransition();
		pt.setDelay(Duration.millis(1000));
		pt.getChildren().add(translateFirstImage);
		pt.getChildren().add(translateSecondImage);
		pt.getChildren().add(rotateFirstImage);
		pt.getChildren().add(rotateSecondImage);
		pt.setOnFinished(e -> {
			switchImages(clickedImageView, userCardInHand, firstImageAngle, secondImageAngle);
			if (game.getCurrentPlayer().hasWonRound()) 
				nextRound();
			else
				gamePhase();
		});
		pt.play();
	}


/**
 * Initiates the special king switch for the clicked card position.
 *
 * @param clickedImagePosition The position of the clicked card in the table.
 */
	private void specialKingSwitch(Integer clickedImagePosition) {
		Card chosenCard = game.getCurrentPlayer().getTableCards()[clickedImagePosition];
		if ( !chosenCard.isFaceDown() ) {
			showInvalidCardChosenError();
		}
		else {
			updateSingleCardTableView(clickedImagePosition);
			game.getCurrentPlayer().specialKingSwitch(clickedImagePosition);
			kingSwitchAnimation(getImageViewFromCardValue(clickedImagePosition), currentPlayerCardInHandImageView );
		}
	}
	
	/**
	 * Makes the cards non-clickable.
	 */
	private void makeCardsNonClickable() {
		for (int i=0; i<10; i++) {
			this.imageViews.get(i).setOnMouseClicked(null);
		}
	}
	
	/**
	 * Initiates the player discard action.
	 */
	public void playerDiscard() {
		game.currentPlayerDiscards();
		playerDiscardAnimation();
	}

	/**
	 * Assigns card images to the ImageViews based on the game state, representing each player's table cards.
	 */
	public void assignCards () {
		int playerNumber = 0;
		for (Player player:game.getPlayers()) {
			for (int cardPosition=0; cardPosition < 10; cardPosition++) {
				int position = playerNumber * CARDSPOTSPERPLAYER + cardPosition;
				if (cardPosition < player.getTableCardsNumber() )
					imageViews.get(position).setImage(CardImagesLoader.getBackOfCardImage());
				else
					imageViews.get(position).setImage(null);
			}
			playerNumber++;
		}
		wastePile.setImage(CardImagesLoader.getImageFromCardName(game.getWastePile().peek().toString()));
	}
	
	

	/**
	 * Enables game buttons (waste pile and deck).
	 */
	private void enableGameButtons() {
		wastePileButton.setDisable(false);
		deckButton.setDisable(false);			
	}
	
	/**
	 * Disables game buttons (waste pile, deck, and table cards).
	 */
	private void disableGameButtons() {
		wastePileButton.setDisable(true);
		deckButton.setDisable(true);
		for (int i=0; i<10; i++) {
			this.imageViews.get(i).setOnMouseClicked(null);
		}
	}


	/**
	 * Initiates the process of the current player drawing a card from the deck.
	 */
	public void drawFromDeck () {
		disableGameButtons();
		game.currentPlayerDrawsFromDeck();
		drawFromDeckAnimation();
	}
	
	/**
	 * Initiates the animation for drawing a card from the deck.
	 */
	private void drawFromDeckAnimation() {
		
		movingDeckImage.setImage(CardImagesLoader.getImageFromCardName(game.getCurrentPlayer().getCardInHand().toString()));
		
		Double deckImageAngle = movingDeckImage.getRotate();
		Double userCardInHandAngle = currentPlayerCardInHandImageView.getRotate();
		
		TranslateTransition translateAnimation = getTranslateAnimation(movingDeckImage, currentPlayerCardInHandImageView);
		RotateTransition rotateTransition = getRotateAnimation(movingDeckImage, currentPlayerCardInHandImageView);
		ParallelTransition pt = new ParallelTransition();
		pt.getChildren().add(translateAnimation);
		pt.getChildren().add(rotateTransition);
		pt.setOnFinished(e->{
			switchImages(movingDeckImage, currentPlayerCardInHandImageView,deckImageAngle,userCardInHandAngle);
			movingDeckImage.setImage(null);
			gamePhase();
		});
		pt.play();

	}

	/**
	 * Initiates the process of the current player drawing a card from the waste pile.
	 */
	public void drawFromWastePile() {
		disableGameButtons();
    	game.currentPlayerDrawsFromWastePile();
    	drawFromWastePileAnimation();
	}
	
	/**
	 * Updates the view for a single card in the table.
	 *
	 * @param position The position of the card in the table.
	 */
	private void updateSingleCardTableView(int position) {
		Card tableCard = game.getCurrentPlayer().getTableCards()[position];
		getImageViewFromCardValue(position).setImage(CardImagesLoader.getImageFromCardName(tableCard.toString()));
	}

	/**
	 * Updates the view for the current player's card in hand.
	 */
	public void updateCardInHandView() {
		Card userCardInHandEntity = game.getCurrentPlayer().getCardInHand();
		if (userCardInHandEntity != null) {
			currentPlayerCardInHandImageView.setImage(CardImagesLoader.getImageFromCardName(userCardInHandEntity.toString()));
		}
		else 
			currentPlayerCardInHandImageView.setImage(null);
	}
	
	/**
	 * Initiates the animation for drawing a card from the waste pile.
	 */
	private void drawFromWastePileAnimation() {
		setSecondWastePileCard();
		
		Double wastePileAngle = wastePile.getRotate();
		Double userCardInHandAngle = currentPlayerCardInHandImageView.getRotate();
		
		RotateTransition rotateAnimation = getRotateAnimation(wastePile, currentPlayerCardInHandImageView);
		TranslateTransition translateAnimation = getTranslateAnimation(wastePile, currentPlayerCardInHandImageView);
		ParallelTransition pt = new ParallelTransition(rotateAnimation, translateAnimation);
		pt.setOnFinished(e->{
			switchImages(wastePile, currentPlayerCardInHandImageView,wastePileAngle,userCardInHandAngle);
			gamePhase();
		});
		AudioManager.getInstance().reproduceCardFlippingSound();
		pt.play();

	}
	
	/**
	 * Sets the second waste pile card ImageView based on the current state of the game.
	 */
	private void setSecondWastePileCard () {
		if (!game.getWastePile().isEmpty()) {
			Card card = game.getWastePile().peek();
			secondWastePileCard.setImage(CardImagesLoader.getImageFromCardName(card.toString()));
		}
		else
			secondWastePileCard.setImage(null);
	}
	
	/**
	 * Initiates the animation for a player discarding a card.
	 */
	private void playerDiscardAnimation() {
		Double userCardInHandAngle = currentPlayerCardInHandImageView.getRotate();
		Double wastePileAngle = wastePile.getRotate();
		RotateTransition rotateAnimation = getRotateAnimation(currentPlayerCardInHandImageView, wastePile);
		TranslateTransition translateAnimation = getTranslateAnimation(currentPlayerCardInHandImageView, wastePile);
		ParallelTransition pt = new ParallelTransition(rotateAnimation, translateAnimation);
		pt.setOnFinished(e->{
			switchImages(wastePile, currentPlayerCardInHandImageView,wastePileAngle, userCardInHandAngle);
			updateCardInHandView();
			nextTurn();
		});
		AudioManager.getInstance().reproduceCardFlippingSound();
		pt.play();
	}
	
	/**
	 * Initiates the animation for switching two cards on the table.
	 *
	 * @param image            The ImageView of the card being switched.
	 * @param imageViewPosition The position of the target ImageView for the switch.
	 */
	public void cardsSwitchAnimation (ImageView image,Integer imageViewPosition) {

		ImageView destinationImage = getImageViewFromCardValue(imageViewPosition);
		Double firstImageAngle = image.getRotate();
		Double secondImageAngle = destinationImage.getRotate();
		
		TranslateTransition translateFirstImage = getTranslateAnimation(image, destinationImage);
		TranslateTransition translateSecondImage = getTranslateAnimation(destinationImage, image);
		RotateTransition rotateFirstImage = getRotateAnimation(image, destinationImage);
		RotateTransition rotateSecondImage = getRotateAnimation(destinationImage, image);
		
		ParallelTransition pt = new ParallelTransition(translateFirstImage,translateSecondImage,
														rotateFirstImage,rotateSecondImage);
		pt.setDelay(Duration.millis(1000));
		pt.setOnFinished(e -> {
			switchImages(image, destinationImage, firstImageAngle, secondImageAngle);
			game.getCurrentPlayer().switchTableCard();
			if (game.getCurrentPlayer().hasWonRound()) 
				nextRound();
			else
				gamePhase();
		});
		AudioManager.getInstance().reproduceCardFlippingSound();
		pt.play();
	}
	
	/**
	 * Creates a TranslateTransition for image translation animation.
	 *
	 * @param movingImage      The ImageView to be moved.
	 * @param destinationImage The ImageView representing the destination.
	 * @return The TranslateTransition instance.
	 */
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
	
	/**
	 * Switches images between two ImageViews.
	 *
	 * @param image              The source ImageView.
	 * @param destinationImage   The destination ImageView.
	 * @param firstImageAngle    The initial rotation angle of the source ImageView.
	 * @param secondImageAngle   The initial rotation angle of the destination ImageView.
	 */
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

	/**
	 * Creates a RotateTransition for image rotation animation.
	 *
	 * @param movingImage      The ImageView to be rotated.
	 * @param destinationImage The ImageView representing the destination.
	 * @return The RotateTransition instance.
	 */
	public RotateTransition getRotateAnimation(ImageView movingImage, ImageView destinationImage) {
		RotateTransition rotate = new RotateTransition(Duration.millis(1000), movingImage);
		rotate.setToAngle(destinationImage.getRotate());
		return rotate;
	}
	

	/**
	 * Initiates the end-of-game animation and displays the corresponding message.
	 */
	private void endOfGameAnimation() {
		if (game.getCurrentPlayer().isBot())
			userMessages.setText(gameLostMessage);
		else
			userMessages.setText(gameWonMessage);
		FadeTransition fadeInTransition = getFadeInTransition(userMessages);
		FadeTransition fadeOutTransition = getFadeOutTransition(userMessages);
		fadeInTransition.setOnFinished(e-> fadeOutTransition.play());
		fadeOutTransition.setOnFinished(e-> {
			Platform.runLater( ()-> showEndOfGameAlert() );
		});
		fadeInTransition.play();
	}
	
	/**
	 * Displays the end-of-game alert, allowing the user to exit the game or go to the main page.
	 */
	private void showEndOfGameAlert() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("END OF THE GAME");
		alert.setHeaderText("THE GAME FINISHED!");
		
        ButtonType playAgainButton = new ButtonType("Exit game");
        ButtonType goToMainButton = new ButtonType("Go to Main Page");

        alert.getButtonTypes().setAll(playAgainButton, goToMainButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == playAgainButton) {
                System.exit(0);
            } else if (response == goToMainButton) {
                try {
					switchToUserView();
				} catch (IOException e1) {
					LoggerUtil.logError(e1.getMessage());
				}
            }
        });
	}

	/**
	 * Displays the round result message and initiates the next round.
	 */
	private void showRoundResult () {
		if (game.getCurrentPlayer().isBot())
			userMessages.setText(roundLostMessage);
		else
			userMessages.setText(roundWonMessage);
		FadeTransition fadeInTransition = getFadeInTransition(userMessages);
		FadeTransition fadeOutTransition = getFadeOutTransition(userMessages);
		fadeOutTransition.setOnFinished( e-> {
			game.nextRound();
			startRound();
		});
		fadeInTransition.setOnFinished(e-> fadeOutTransition.play());
		fadeInTransition.play();
	}
	
	/**
	 * Initiates the animation for the end of a round.
	 */
	private void endOfRoundAnimation() {
		ParallelTransition pt = new ParallelTransition();
		for (ImageView image:imageViews) {
	        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), image);
	        fadeTransition.setFromValue(1.0); 
	        fadeTransition.setToValue(0.0); 
	        pt.getChildren().add(fadeTransition);
		}
		pt.play();
	}
	
	/**
	 * Initiates the animation for the start of a round.
	 */
	private void startRoundAnimation () {
		ParallelTransition pt = new ParallelTransition();
		for (ImageView image:imageViews) {
	        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), image);
	        fadeTransition.setFromValue(0.0); 
	        fadeTransition.setToValue(1.0); 
	        pt.getChildren().add(fadeTransition);
		}
		pt.play();
	}
	
	/**
	 * Retrieves the ImageView associated with a card value.
	 *
	 * @param cardValue The value representing the position of the card.
	 * @return The ImageView associated with the card value.
	 */
	public ImageView getImageViewFromCardValue (Integer cardValue) {
		if (cardValue.equals(deckImageViewPosition)) 
			return movingDeckImage;
		if (cardValue.equals(userCardInHandImageViewPosition)) {
			return userCardInHand;
		}
		int position = game.getCurrentPlayerNumber() * CARDSPOTSPERPLAYER + cardValue;
		return imageViews.get(position);
	}
	
	/**
	 * Displays a warning alert for choosing an invalid card.
	 */
	public void showInvalidCardChosenError() {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Errore");
		alert.setHeaderText("La carta che hai selezionato non è valida!");
		alert.setContentText("Scegli una carta che non sia scoperta!");
		alert.showAndWait();
	}
	
	/**
	 * Initiates the change of turn transition, updating the user messages accordingly.
	 */
	public void showChangeOfTurnTransition () {
		if ( !game.getCurrentPlayer().isBot() ) {
			userMessages.setText(userTurnOverMessage);
		}
		else if (game.getNextPlayer().isBot()) {
			userMessages.setText(nextPlayerTurnMessage);
		}
		else 
			userMessages.setText(userPlayerTurnMessage);
        FadeTransition fadeInTransition = getFadeInTransition(userMessages);
        FadeTransition fadeOutTransition = getFadeOutTransition(userMessages);
        fadeOutTransition.setOnFinished(e -> drawPhase());
        fadeInTransition.setOnFinished(e -> fadeOutTransition.play());
        fadeInTransition.play();
        
	}
	
	/**
	 * Creates a FadeTransition for the fadeIn effect on a Node.
	 *
	 * @param node The Node to which the fadeIn effect will be applied.
	 * @return The FadeTransition instance.
	 */
	public FadeTransition getFadeInTransition (Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), node);
        fadeTransition.setFromValue(0.0); 
        fadeTransition.setToValue(1.0); 
        return fadeTransition;
	}
	
	/**
	 * Creates a FadeTransition for the fadeOut effect on a Node.
	 *
	 * @param node The Node to which the fadeOut effect will be applied.
	 * @return The FadeTransition instance.
	 */
	public FadeTransition getFadeOutTransition (Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), node);
        fadeTransition.setFromValue(1.0); 
        fadeTransition.setToValue(0.0); 
        return fadeTransition;
	}
	
	/**
	 * Resets all ImageViews to null, representing an initial state.
	 */
	public void resetImageViews () {
		imageViews.stream().forEach( i -> i .setImage(null));
		deck.setImage(CardImagesLoader.getBackOfCardImage());
	}
	
	/**
	 * Adds all the image views to an Array
	 */
	public void intializeImageViews () {
		
		currentPlayerCardInHandImageView = userCardInHand;
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
		imageViews.add(p2slot1);
		imageViews.add(p2slot2);
		imageViews.add(p2slot3);
		imageViews.add(p2slot4);
		imageViews.add(p2slot5);
		imageViews.add(p2slot6);
		imageViews.add(p2slot7);
		imageViews.add(p2slot8);
		imageViews.add(p2slot9);
		imageViews.add(p2slot10);
		imageViews.add(p3slot1);
		imageViews.add(p3slot2);
		imageViews.add(p3slot3);
		imageViews.add(p3slot4);
		imageViews.add(p3slot5);
		imageViews.add(p3slot6);
		imageViews.add(p3slot7);
		imageViews.add(p3slot8);
		imageViews.add(p3slot9);
		imageViews.add(p3slot10);
		
		imageViews.add(deck);
		imageViews.add(wastePile);
		imageViews.add(secondWastePileCard);
		imageViews.add(userCardInHand);
		imageViews.add(p1cardInHand);
		imageViews.add(p2cardInHand);
		imageViews.add(p3cardInHand);
	}
	
	/**
	 * Switches the application view to the user view, loading the corresponding FXML file.
	 * Retrieves the user information from the game's human player and sets it in the UserController.
	 *
	 * @throws IOException If an error occurs while loading the FXML file.
	 */
	private void switchToUserView () throws IOException {
		Parent root = null;
		Stage stage;
		FXMLLoader loader =  new FXMLLoader(getClass().getResource("../view/userView.fxml"));
		root = loader.load();
		UserController userController = loader.getController();
		userController.setUserView(game.getHumanPlayer().getUser());
		stage = (Stage) wastePileButton.getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}
	
	@FXML
	private void quitGame () {
		game.end();
		try {
			switchToUserView();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	





}
