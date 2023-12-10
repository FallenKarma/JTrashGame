package controller;

import java.io.IOException;

import CustomExceptions.IncorrectUserNameException;
import CustomExceptions.UnexistingUserException;
import CustomExceptions.UserAlreadyExistsException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.User;

public class LoginController {
	
	private Parent root;
	private Scene scene;
	private User user;
	
	@FXML
	TextField usernameTF;
	
	public void register () {
		String username = usernameTF.getText();
		try {
			user = User.register(username);
			switchToUserView();
		} catch (IncorrectUserNameException e) {
			showUsernameNotValidWarning();
		} catch (UserAlreadyExistsException e) {
			showUserAlreadyExistsWarning();
		}
	}
	
	public void login () {
		String username = usernameTF.getText();
		try {
			user = User.login(username);
			switchToUserView();
		} catch (IncorrectUserNameException e) {
			showUsernameNotValidWarning();
		} catch (UnexistingUserException e) {
			showUnexistingUsernameWarning();
		}
	}
	
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

	@FXML
	public void showUsernameNotValidWarning () {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Errore");
		alert.setHeaderText("Username non valido!");
		alert.setContentText("Inserisci uno username valido che contenga solo lettere o numeri!");
		alert.showAndWait();
	}
	
	@FXML
	public void showUnexistingUsernameWarning () {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Errore");
		alert.setHeaderText("Utente inesistente!");
		alert.setContentText("Lo username che hai inserito non ha un utente associato, inserisci uno username corretto o crea un nuovo utente.");
		alert.showAndWait();
	}
	
	@FXML
	private void showUserAlreadyExistsWarning() {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Errore");
		alert.setHeaderText("Questo username è già stato utilizzato!");
		alert.setContentText("Lo username che hai inserito ha già un utente associato, inserisci uno username diverso.");
		alert.showAndWait();
	}
	
	public void unloadUser () {
		this.user = null;
	}
	
	@FXML
	private void loginFromEnterKey(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER)
			login();
	}
}
