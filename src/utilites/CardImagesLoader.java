package utilites;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.image.Image;

public class CardImagesLoader {
	private static String imagesExtension = ".png";
	private static String  imagesRootPath = null;
	private static String backOfCardsImageName = "backOfCards";
	
	private CardImagesLoader () {};	
	
	public static Image getImageFromCardName (String cardToString) {
		if (imagesRootPath == null) {
			String currentDirectory = "file:\\" + System.getProperty("user.dir");
			imagesRootPath = currentDirectory + "\\src\\resources\\carte\\";
		}
		return new Image(imagesRootPath + cardToString + imagesExtension);
	}
	
	public static Image getBackOfCardImage () {
		return new Image(imagesRootPath + backOfCardsImageName + imagesExtension);
	}

}
