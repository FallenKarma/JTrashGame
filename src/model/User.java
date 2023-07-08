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

public class User {
	static String usersFile = "Users.txt";
	private String nickname;
	private Integer gamesPlayed;
	private Integer gamesWon;
	private Integer gamesLost;
	private Integer level;
	
	
	//Costruttore per bot
	public User () {
		nickname = "";
		gamesPlayed = 0;
		gamesWon = 0;
		gamesLost = 0;
		level = 0;
	}
	
	//Costruttore per utenti reali
	public User(String nickname) {
		this.nickname = nickname;
		loginOrCreate ( this.nickname);
	}
	
	public void loginOrCreate (String nickname) {
		if (!checkIfUserExistsAndLoad(nickname)) {
			this.gamesPlayed = 0;
			this.gamesWon = 0;
			this.gamesLost = 0;
			this.level = 1;
			createUser (nickname);
		}
	}
	public void createUser (String nickname) {  
			 try {
			        FileWriter myWriter = new FileWriter("Users.txt",true);
			        myWriter.append(nickname+"\n");
			        myWriter.append(this.gamesPlayed.toString()+"\n");
			        myWriter.append(this.gamesWon.toString()+"\n");
			        myWriter.append(this.gamesLost.toString()+"\n");
			        myWriter.append(this.level.toString()+"\n");
			        myWriter.close();
			        System.out.println("Successfully wrote to the file.");
		     } 
			 catch (IOException e) {
			        System.out.println("An error occurred.");
			        e.printStackTrace();
		     }
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
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	    }
		return userExists;
	}
	
	
	public void updateUser () {
		Path path = Paths.get(this.usersFile);
		List<String> fileContent = new ArrayList<>();
		try {
			fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    ArrayList<Integer> newUserData = new ArrayList<Integer>();
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
	
	



	
}
