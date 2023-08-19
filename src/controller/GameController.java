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


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.Card;
import model.Game;
import model.Player;
import model.User;

public class GameController implements Initializable{
	
	private final String imagesExtension = ".png";
	private final String  imagesRootPath = "C:/Users/admin/eclipse-workspace/JTrashGame/src/resources/carte/";
	private Game game;
	
	List<ImageView> imageViews;
	
	
	@FXML
	ImageView wastePile;
	
	@FXML
	GridPane cardsPane;
	
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
	
	public void setUpGameView (Integer numberOfPlayers,User user) {
		game = new Game (numberOfPlayers,user );
		int i = 0;
		for (Player player: game.getPlayers() ) {
			assignCards (player,i);
			i++;
		}
	}
	
	
	public void assignCards (Player player,int playerNumber) {
		for (int i=0; i < player.getTableCardsNumber(); i++) {
			int position = playerNumber * 10 + i;
			imageViews.get(position).setImage(new Image(imagesRootPath + "backOfCards.png"));
		}
		wastePile.setImage(new Image(imagesRootPath + game.getLastDiscardedCard().toString() + ".png"));
	}
	
	public void drawFromWastePile() {
		
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
