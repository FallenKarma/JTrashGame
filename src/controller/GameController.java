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

	private Game game;
	private Map<Integer, String> playerToTablePortionCornersMap = new HashMap<>();
	
	List<ImageView> imageViews;
	
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
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		imageViews = new ArrayList<>();
		playerToTablePortionCornersMap.put(1, "3-6,7-7");
		playerToTablePortionCornersMap.put(1, "3-8");
		intializeImageViews();
	}
	
	public void setUpGame (Integer numberOfPlayers,User user) {
		game = new Game (numberOfPlayers,user );
		Collection<Player> gamePlayers = game.getPlayers();
		for (Player player: gamePlayers) {
			assignCards (player);
		}
	}
	
	
	public void assignCards (Player player) {
		String rootPath = "/resources/carte/";
			for (int i=0;i<10;i++) {
				Card card = player.getTableCards().get(i);
				imageViews.get(i).setImage(new Image(rootPath+ card.toString()+".png"));
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
		
		ImageView imageView = new ImageView("/resources/carte/2_of_clubs.png");
		imageView.setFitHeight(110);
		imageView.setFitWidth(145);
		imageView.setPreserveRatio(true);
		cardsPane.add(imageView, 8, 1);
		
	}


}
