package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import customExceptions.IncorrectUserNameException;
import customExceptions.UnexistingUserException;
import customExceptions.UserAlreadyExistsException;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.User;
import utilites.LoggerUtil;

/**
 * Controller class responsible for handling user login and registration actions.
 * Manages the interaction between the user interface and the User model.
 */
public class LoginController implements Initializable{
	
	private Parent root;
	private Scene scene;
	private User user;
	
	@FXML
	TextField usernameTF;
	
	@FXML
	Label secondLabel;
	
    /**
     * Registers a new user with the provided username.
     * If successful, switches to the user view.
     * Shows a warning if the username is not valid or if the user already exists.
     */
	public void register () {
		String username = usernameTF.getText();
		usernameTF.setText("");
		try {
			user = User.register(username);
			switchToUserView();
		} catch (IncorrectUserNameException e) {
			showUsernameNotValidWarning();
		} catch (UserAlreadyExistsException e) {
			showUserAlreadyExistsWarning();
		}
	}
	
	 /**
     * Logs in the user with the provided username.
     * If successful, switches to the user view.
     * Shows a warning if the username is not valid or if the user does not exist.
     */
	public void login () {
		String username = usernameTF.getText();
		try {
			user = User.login(username);
			usernameTF.setText("");
			switchToUserView();
		} catch (IncorrectUserNameException e) {
			showUsernameNotValidWarning();
		} catch (UnexistingUserException e) {
			showUnexistingUsernameWarning();
		}
	}
	
    /**
     * Switches the application view to the user view after a successful login or registration.
     * Loads the corresponding FXML file and sets the user information in the UserController.
     */
	public void switchToUserView()  {
		Stage stage;
		FXMLLoader loader =  new FXMLLoader(getClass().getResource("../view/userView.fxml"));
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		UserController userController = loader.getController();
		userController.setUserView(user);
		stage = (Stage) usernameTF.getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

    /**
     * Displays a warning dialog for an invalid username during login or registration.
     */
	@FXML
	public void showUsernameNotValidWarning () {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Errore");
		alert.setHeaderText("Username non valido!");
		alert.setContentText("Inserisci uno username valido che contenga solo lettere o numeri!");
		alert.showAndWait();
	}
	
    /**
     * Displays a warning dialog for a non-existing username during login.
     */
	@FXML
	public void showUnexistingUsernameWarning () {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Errore");
		alert.setHeaderText("Utente inesistente!");
		alert.setContentText("Lo username che hai inserito non ha un utente associato, inserisci uno username corretto o crea un nuovo utente.");
		alert.showAndWait();
	}
	
    /**
     * Displays a warning dialog for an already existing username during registration.
     */
	@FXML
	private void showUserAlreadyExistsWarning() {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Errore");
		alert.setHeaderText("Questo username è già stato utilizzato!");
		alert.setContentText("Lo username che hai inserito ha già un utente associato, inserisci uno username diverso.");
		alert.showAndWait();
	}
	
    /**
     * Unloads the current user information.
     */
	public void unloadUser () {
		this.user = null;
	}
	
    /**
     * Handles the login action when the Enter key is pressed.
     * Calls the login method.
     *
     * @param event The KeyEvent triggered by pressing a key.
     */
	@FXML
	private void loginFromEnterKey(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER)
			login();
	}

	/**
	 * actions on start
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		playFadeInTransitionForGameTitle();
	}
	
	/**
	 * Start the fade in transition for the game title
	 */
	public void playFadeInTransitionForGameTitle() {
		FadeTransition transition = new FadeTransition(Duration.seconds(3.5), secondLabel);
		transition.setByValue(0.0);
		transition.setToValue(1.0);
		transition.play();
	}
}
