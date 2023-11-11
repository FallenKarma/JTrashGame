package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.User;

public class UserController {
	private User user;
	@FXML
	Label welcomeUserLabel;
	@FXML
	Label gamesPlayedLabel;
	@FXML
	Label gamesWonLabel;
	@FXML
	Label gamesLostLabel;
	@FXML
	Label levelLabel;
	@FXML
	ChoiceBox<Integer> numberOfBots; 
	@FXML
	Button startGameButton;
	
	private Integer[] numberOfBotsOptions = { 1 , 2 , 3 };

	public void setUserView (User user) {
		this.user = user;
		if (user.getGamesPlayed()==0) {
			welcomeUserLabel.setText("Benvenuto " + user.getNickname());
		}
		else welcomeUserLabel.setText("Ciao " + user.getNickname());
		gamesPlayedLabel.setText("Partite giocate: " + user.getGamesPlayed());
		gamesWonLabel.setText("Partite vinte: " + user.getGamesWon());
		gamesLostLabel.setText("Partite perse: " + user.getGamesLost());
		levelLabel.setText("Livello: " + user.getLevel());
		numberOfBots.getItems().addAll(numberOfBotsOptions);
	}
	
	public void logout (ActionEvent event) {
		FXMLLoader loader =  new FXMLLoader(getClass().getResource("../view/welcomeView.fxml"));
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LoginController loginController = loader.getController();
		loginController.unloadUser();
		Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void startGame (ActionEvent event) {
		if (numberOfBots.getValue()==null) {
			showInvalidNumberOfPlayersError();
		}
		else {
			FXMLLoader loader =  new FXMLLoader(getClass().getResource("../view/gameView.fxml"));
			Parent root = null;
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			GameController gameController = loader.getController();
			Integer numberOfPlayers = numberOfBots.getValue()+1;
			gameController.setUpGame(numberOfPlayers,user);
			Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setFullScreen(true);
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
		}
		
	}
	
	
	public void showInvalidNumberOfPlayersError() {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Errore");
		alert.setHeaderText("Numero di giocatori non valido!");
		alert.setContentText("Non puoi giocare da solo! Scegli se avere uno, due o tre avversari.");
		alert.showAndWait();
	}
}
