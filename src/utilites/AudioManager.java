package utilites;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * The AudioManager class is responsible for managing audio in a JavaFX application.
 * It follows the singleton pattern to provide a single point of control for audio playback.
 */
public class AudioManager {
    // Singleton instance
    private static AudioManager instance;
    
    // Directory for audio files
    private static final String audioDirectory = "./src/resources/audio/";
    
    // Audio file for card flipping
    private static final String cardFlippingFileName = "flipCard.wav";

    // Private constructor to enforce singleton pattern
    private AudioManager() {}

    /**
     * Retrieves the singleton instance of AudioManager.
     * If the instance does not exist, a new one is created.
     * @return The singleton instance of AudioManager.
     */
    public static AudioManager getInstance() {
        if (instance == null)
            instance = new AudioManager();
        return instance;
    }

    /**
     * Plays audio from the specified file.
     * @param filename The path to the audio file.
     */
    public void play(String filename) {
        Media hit = new Media(new File(filename).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
    }

    /**
     * Reproduces the card flipping sound.
     */
    public void reproduceCardFlippingSound() {
        play(audioDirectory + cardFlippingFileName);
    }
}
