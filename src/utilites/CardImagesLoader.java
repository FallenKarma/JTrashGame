package utilites;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.image.Image;

/**
 * The CardImagesLoader class provides methods for loading card images.
 */
public class CardImagesLoader {
    private static String imagesExtension = ".png";
    private static String imagesRootPath = null;
    private static String backOfCardsImageName = "backOfCards";

    private CardImagesLoader() {
    }

    /**
     * Gets the Image object corresponding to the given card name.
     *
     * @param cardToString The string representation of the card.
     * @return The Image object representing the card.
     */
    public static Image getImageFromCardName(String cardToString) {
        setRootPath();
        return new Image(imagesRootPath + cardToString + imagesExtension);
    }

    /**
     * Gets the Image object for the back of a card.
     *
     * @return The Image object representing the back of a card.
     */
    public static Image getBackOfCardImage() {
        setRootPath();
        return new Image(imagesRootPath + backOfCardsImageName + imagesExtension);
    }

    /**
     * Sets the root path
     */
    private static void setRootPath() {
        String currentDirectory = "file:\\" + System.getProperty("user.dir");
        imagesRootPath = currentDirectory + "\\src\\resources\\carte\\";
    }
}
