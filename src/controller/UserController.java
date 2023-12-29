package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.User;

/**
 * Controller class responsible for managing user-related actions and interactions in the user interface.
 * Handles user information display, logout, and game setup.
 */
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
	ImageView star1;
	@FXML
	ImageView star2;
	@FXML
	ImageView star3;
	@FXML
	ImageView star4;
	@FXML
	ImageView star5;
	@FXML
	ChoiceBox<Integer> numberOfBots; 
	@FXML
	Button startGameButton;
	
	String starEndPath = "\\src\\resources\\star.gif";
	
	private Integer[] numberOfBotsOptions = { 1 , 2 , 3 };

    /**
     * Sets up the user view with the provided user information.
     * Displays a welcome message and user statistics.
     *
     * @param user The User object containing user information.
     */
	public void setUserView (User user) {
		initalizeLevelView();
		this.user = user;
		if (user.getGamesPlayed()==0) {
			welcomeUserLabel.setText("BENVENUTO " + user.getNickname().toUpperCase() );
		}
		else welcomeUserLabel.setText("CIAO " + user.getNickname().toUpperCase());
		gamesPlayedLabel.setText( user.getGamesPlayed().toString() );
		gamesWonLabel.setText( user.getGamesWon().toString() );
		gamesLostLabel.setText(user.getGamesLost().toString() );
		numberOfBots.getItems().addAll(numberOfBotsOptions);
		setLevelView(user.getLevel());
	}
    /**
     * Hides all the star gifs until the first one
     *
     */
	private void initalizeLevelView() {
		star2.setVisible(false);
		star3.setVisible(false);
		star4.setVisible(false);
		star5.setVisible(false);
		
	}

    /**
     * Shows the star gifs based on the player level
     *
     */
	public void setLevelView (Integer level) {
		if (level>=2)
			star2.setVisible(true);
		if (level>=3)
			star3.setVisible(true);
		if (level>=4)
			star4.setVisible(true);
		if (level>=5)
			star5.setVisible(true);
	}
	
    /**
     * Logs out the current user and switches to the login view.
     *
     * @param event The ActionEvent triggered by clicking the logout button.
     */
	public void logout (ActionEvent event) {
		FXMLLoader loader =  new FXMLLoader(getClass().getResource("../view/loginView.fxml"));
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
	
    /**
     * Starts a new game with the specified number of players.
     * Switches to the game view after setup.
     * Shows an error if the number of players is not selected.
     *
     * @param event The ActionEvent triggered by clicking the start game button.
     */
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
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.setFullScreen(true);
			stage.show();
		}
		
	}
	
    /**
     * Displays a warning dialog for an invalid number of players during game setup.
     */
	public void showInvalidNumberOfPlayersError() {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Errore");
		alert.setHeaderText("Numero di giocatori non valido!");
		alert.setContentText("Non puoi giocare da solo! Scegli se avere uno, due o tre avversari.");
		alert.showAndWait();
	}

}
