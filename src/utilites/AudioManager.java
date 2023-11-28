package utilites;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class AudioManager {
	private static AudioManager instance;
	private static final String audioDirectory = "./src/resources/audio/";
	private static final String cardFlippingFileName = "flipCardV2.mp3";

	public static AudioManager getInstance() {
		if (instance == null)
			instance = new AudioManager();
		return instance;
	}
	
	private AudioManager() {}
	
	public void play(String filename) {
		Media hit = new Media(new File(filename).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();
	}
	
	public void reproduceCardFlippingSound() {
		play(audioDirectory + cardFlippingFileName);
	}
}

