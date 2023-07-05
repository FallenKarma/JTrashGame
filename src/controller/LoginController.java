package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

public class LoginController {
	
	private Stage stage;
	private Parent root;
	private Scene scene;
	private User user;
	
	@FXML
	TextField usernameTF;
	
	
	public void login (ActionEvent event) throws IOException {
		String username = usernameTF.getText();
		if (userNameValidator(username)) {
			loadUser(username);
			switchToUserView(event);
		}
		else usernameNotValidWarning(event);
	}
	
	public void switchToUserView(ActionEvent event) throws IOException {
		FXMLLoader loader =  new FXMLLoader(getClass().getResource("../view/userView.fxml"));
		root = loader.load();
		UserController userController = loader.getController();
		userController.setUserView(user);
		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void loadUser (String username){
			user = new User(username);
	}
	
	public boolean userNameValidator (String username) {
		if (username != null && username.length() != 0) {
			for (int i = 0; i < username.length(); i++) {
				if ((Character.isLetterOrDigit(username.charAt(i)) == false)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	@FXML
	public void usernameNotValidWarning (ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Errore");
		alert.setHeaderText("Username non valido!");
		alert.setContentText("Inserisci uno username valido che contenga solo lettere o numeri!");
		alert.showAndWait();
	}
	
	public void unloadUser () {
		this.user = null;
	}
}
