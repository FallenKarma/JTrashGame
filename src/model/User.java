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

import CustomExceptions.IncorrectUserNameException;
import CustomExceptions.UnexistingUserException;
import utilites.LoggerUtil;

public class User {
	
	static String usersFile = "Users.txt";
	private String nickname;
	private Integer gamesPlayed;
	private Integer gamesWon;
	private Integer gamesLost;
	private Integer level;
	private static final ArrayList<Integer> LEVELUPSTEPS = new ArrayList<>(List.of(1,3,6,10,15,25,30) );
	
	//Costruttore per bot
	public User () {
		nickname = "bot";
		gamesPlayed = 0;
		gamesWon = 0;
		gamesLost = 0;
		level = 0;
	}

	public User(String nickname) {
		this.nickname = nickname;
		loadUser();
	}

	public static User login (String nickname) throws UnexistingUserException, IncorrectUserNameException {
		if (isUserNameValid(nickname)) {
			if (userExists(nickname)) {
				return new User(nickname);
			}
			else 
				throw new UnexistingUserException("Unexisting user!");
		}
		else {
			 throw new IncorrectUserNameException("Username not valid!");
		}
	}
	
	public static User createUser (String nickname) throws IncorrectUserNameException{  
		if (isUserNameValid(nickname)) {
			try (FileWriter myWriter = new FileWriter("Users.txt",true)){
				myWriter.append(nickname+"\n");
				myWriter.append("0\n");
				myWriter.append("0\n");
				myWriter.append("0\n");
				myWriter.append("1\n");
				myWriter.close();
				LoggerUtil.logInfo("The new user was created succesfully.");
				return new User(nickname);
			} 
			catch (IOException e) {
				LoggerUtil.logError("An error occurred.");
				e.printStackTrace();
			}
		}
		else 
			throw new IncorrectUserNameException("Username not valid!");
		return null;
	}
	


	public boolean checkIfUserExistsAndLoad (String nickname) {
		Boolean userExists = false;
	    try {
	        File file = new File(usersFile);
	        Scanner myReader = new Scanner(file);
	        while (myReader.hasNextLine()) {
	          String line = myReader.nextLine();
	          if (line.contains(nickname)) {
	        	  userExists = true;
	        	  this.gamesPlayed = Integer.valueOf(myReader.nextLine());
	        	  this.gamesWon = Integer.valueOf(myReader.nextLine());
	        	  this.gamesLost = Integer.valueOf(myReader.nextLine());
	        	  this.level = Integer.valueOf(myReader.nextLine());
	        	  break;
	          }
	        }
	        myReader.close();
	    } 
	    catch (FileNotFoundException e) {
	        LoggerUtil.logError("An error occurred.");
	        e.printStackTrace();
	    }
		return userExists;
	}
	
	public static boolean userExists (String username) {
		File file = new File(usersFile);
	    try (Scanner myReader = new Scanner(file)){
	        while (myReader.hasNextLine()) {
	          String line = myReader.nextLine();
	          if (line.contains(username)) {
	        	  return true;
	          }
	        }
	    } 
	    catch (FileNotFoundException e) {
	        LoggerUtil.logError("An error occurred.");
	        e.printStackTrace();
	    }
	    return false;
	}
	
	private void loadUser () {
	    try {
	        File file = new File(usersFile);
	        Scanner myReader = new Scanner(file);
	        while (myReader.hasNextLine()) {
	          String line = myReader.nextLine();
	          if (line.contains(nickname)) {
	        	  this.gamesPlayed = Integer.valueOf(myReader.nextLine());
	        	  this.gamesWon = Integer.valueOf(myReader.nextLine());
	        	  this.gamesLost = Integer.valueOf(myReader.nextLine());
	        	  this.level = Integer.valueOf(myReader.nextLine());
	        	  break;
	          }
	        }
	        myReader.close();
	    } 
	    catch (FileNotFoundException e) {
	        LoggerUtil.logError("An error occurred.");
	        e.printStackTrace();
	    }
	}
	
	
	public void updateUserStats () {
		Path path = Paths.get(this.usersFile);
		List<String> fileContent = new ArrayList<>();
		try {
			fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    ArrayList<Integer> newUserData = new ArrayList<>();
	    newUserData.add(this.gamesPlayed);
	    newUserData.add(this.gamesWon);
	    newUserData.add(this.gamesLost);
	    newUserData.add(this.level);
	    boolean userFound = false;
	    int index = 0;
		for (int i = 0; i < fileContent.size(); i++) {
			if (fileContent.get(i).equals(nickname)) {
				userFound = true;
			}
			if (userFound) {
				fileContent.set(i, newUserData.get(index).toString());
				index+=1;
				if (index==4) break;
			}
		}

		try {
			Files.write(path, fileContent, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

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

	@Override
	public String toString() {
		return "User [nickname=" + nickname + ", gamesPlayed=" + gamesPlayed + ", gamesWon=" + gamesWon + ", gamesLost="
				+ gamesLost + ", level=" + level + "]";
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
		gamesLost +=1;
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
