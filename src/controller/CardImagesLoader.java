package controller;

import javafx.scene.image.Image;

public class CardImagesLoader {
	private static String imagesExtension = ".png";
	private static String  imagesRootPath;
	private static String backOfCardsImageName = "backOfCards";
	
	public static Image getImageFromCardName (String cardToString) {
		String currentDirectory = System.getProperty("user.dir");
		imagesRootPath = currentDirectory + "\\src\\resources\\carte\\";
		return new Image(imagesRootPath + cardToString + imagesExtension);
	}
	
	public static Image getBackOfCardImage () {
		String currentDirectory = System.getProperty("user.dir");
		imagesRootPath = currentDirectory + "\\src\\resources\\carte\\";
		return new Image(currentDirectory + backOfCardsImageName + imagesExtension);
	}
}
