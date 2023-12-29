package application;
	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * The main class responsible for launching the JavaFX application.
 */
public class Main extends Application {

    /**
     * The entry point of the JavaFX application.
     *
     * @param primaryStage The primary stage for the application.
     * @throws Exception If an error occurs during the application startup.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../view/loginView.fxml"));
        Scene start = new Scene(root, 1000, 600);
        primaryStage.setScene(start);
        primaryStage.setTitle("JTrash");
        primaryStage.show();
    }

    /**
     * The main method that launches the JavaFX application.
     *
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
