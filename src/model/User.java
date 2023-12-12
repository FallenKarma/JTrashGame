package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONObject;

import customExceptions.IncorrectUserNameException;
import customExceptions.UnexistingUserException;
import customExceptions.UserAlreadyExistsException;
import utilites.JsonManager;
import utilites.LoggerUtil;

public class User {
	
	static String usersFile = "Users.txt";
	private String nickname;
	private Integer gamesPlayed;
	private Integer gamesWon;
	private Integer gamesLost;
	private Integer level;
	
	private static final ArrayList<Integer> LEVELUPSTEPS = new ArrayList<>(List.of(1,3,6,10,15,25,30) );
	private static final String USERNAME_NOT_VALID_MESSAGE = "Username not valid!";
	private static final String UNEXISTING_USER_MESSAGE = "Unexisting user!";
	private static final String ALREADY_EXISTING_USER_MESSAGE= "Username not valid!";
	
	public User(String nickname, Integer gamesPlayed, Integer gamesWon, Integer gamesLost, Integer level) {
		this.nickname = nickname;
		this.gamesPlayed = gamesPlayed;
		this.gamesWon = gamesWon;
		this.gamesLost = gamesLost;
		this.level = level;
	}

	public User(String nickname) {
		this.nickname = nickname;
		this.gamesPlayed = 0;
		this.gamesWon = 0;
		this.gamesLost = 0;
		this.level = 1;
	}

	public static User login(String nickname) {
		if (isUserNameValid(nickname)) {
			if (JsonManager.userExists(nickname))
				return JsonManager.loadUser(nickname);
			else
				throw new UnexistingUserException(UNEXISTING_USER_MESSAGE);
		}
		else {
			throw new IncorrectUserNameException(USERNAME_NOT_VALID_MESSAGE);
		}
	}
	
	public static User register (String nickname) {
		User user = null;
		if (isUserNameValid(nickname)) {
			if (!JsonManager.userExists(nickname)) {
				user = new User(nickname);
				JsonManager.addUser(user);
				return user;
			}
			else
				throw new UserAlreadyExistsException(ALREADY_EXISTING_USER_MESSAGE);
		}
		else {
			throw new IncorrectUserNameException(USERNAME_NOT_VALID_MESSAGE);
		}
	}

	public void updateUserStats () {
		JsonManager.updateUser(this);
	}
	
	
    public static User fromJSON(JSONObject jsonObject) {
        String nickname = (String) jsonObject.get("nickname");
        Integer gamesPlayed = ((Number)jsonObject.get("gamesPlayed")).intValue(); ;
        Integer gamesWon = ((Number)jsonObject.get("gamesWon")).intValue(); ;
        Integer gamesLost = ((Number)jsonObject.get("gamesLost")).intValue(); ;
        Integer level = ((Number)jsonObject.get("level")).intValue(); ;
        
        return new User(nickname, gamesPlayed, gamesWon, gamesLost, level);
    }
    
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nickname", this.nickname);
        jsonObject.put("gamesPlayed", this.gamesPlayed);
        jsonObject.put("gamesWon", this.gamesWon);
        jsonObject.put("gamesLost", this.gamesLost);
        jsonObject.put("level", this.level);
        return jsonObject;
    }
	
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(Integer gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public Integer getGamesWon() {
		return gamesWon;
	}

	public void setGamesWon(Integer gamesWon) {
		this.gamesWon = gamesWon;
	}

	public Integer getGamesLost() {
		return gamesLost;
	}

	public void setGamesLost(Integer gamesLost) {
		this.gamesLost = gamesLost;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public void addGameLost() {
		gamesPlayed += 1;
		gamesLost += 1;
		updateUserStats();
	}
	
	private Integer calculateLevel() {
		if ( LEVELUPSTEPS.contains(gamesWon) ) {
			return level+1;
		}
		else 
			return level;
	}

	public void addGameWon() {
		gamesPlayed += 1;
		gamesWon += 1;
		level = calculateLevel();
		updateUserStats();
	}
	
	public static boolean isUserNameValid (String username) {
		if (username != null && username.length() != 0) {
			for (int i = 0; i < username.length(); i++) {
				if ((!Character.isLetterOrDigit(username.charAt(i)))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	



	
}
