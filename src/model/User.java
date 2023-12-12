package model;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import customExceptions.IncorrectUserNameException;
import customExceptions.UnexistingUserException;
import customExceptions.UserAlreadyExistsException;
import utilites.JsonManager;

public class User {
	
	private String nickname;
	private Integer gamesPlayed;
	private Integer gamesWon;
	private Integer gamesLost;
	private Integer level;
	
	 /**
	 * File path to store user data.
	 */
    static String usersFile = "Users.txt";


    /**
     * List of games won required to level up.
     */
    private static final ArrayList<Integer> LEVELUPSTEPS = new ArrayList<>(List.of(1, 3, 6, 10, 15, 25, 30));
    
    private static final String USERNAME_NOT_VALID_MESSAGE = "Username not valid!";
    private static final String UNEXISTING_USER_MESSAGE = "Unexisting user!";
    private static final String ALREADY_EXISTING_USER_MESSAGE = "Username not valid!";

    /**
     * Constructs a new User with specified attributes.
     *
     * @param nickname   The username of the user.
     * @param gamesPlayed The number of games played by the user.
     * @param gamesWon    The number of games won by the user.
     * @param gamesLost   The number of games lost by the user.
     * @param level       The level of the user.
     */
    public User(String nickname, Integer gamesPlayed, Integer gamesWon, Integer gamesLost, Integer level) {
        this.nickname = nickname;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.gamesLost = gamesLost;
        this.level = level;
    }

    /**
     * Constructs a new User with default attributes.
     *
     * @param nickname The username of the user.
     */
    public User(String nickname) {
        this.nickname = nickname;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.gamesLost = 0;
        this.level = 1;
    }

    /**
     * Logs in a user with the given username fetching his data
     * from a json file.
     *
     * @param nickname The username of the user.
     * @return The logged-in User.
     * @throws IncorrectUserNameException If the username is not valid.
     * @throws UnexistingUserException    If the user does not exist.
     */
    public static User login(String nickname) {
        if (isUserNameValid(nickname)) {
            if (JsonManager.userExists(nickname))
                return JsonManager.loadUser(nickname);
            else
                throw new UnexistingUserException(UNEXISTING_USER_MESSAGE);
        } else {
            throw new IncorrectUserNameException(USERNAME_NOT_VALID_MESSAGE);
        }
    }

    /**
     * Registers a new user with the given username,
     * saving data in a json file.
     *
     * @param nickname The username of the user.
     * @return The registered User.
     * @throws IncorrectUserNameException If the username is not valid.
     * @throws UserAlreadyExistsException If the user already exists.
     */
    public static User register(String nickname) throws IncorrectUserNameException, UserAlreadyExistsException {
        User user = null;
        if (isUserNameValid(nickname)) {
            if (!JsonManager.userExists(nickname)) {
                user = new User(nickname);
                JsonManager.addUser(user);
                return user;
            } else
                throw new UserAlreadyExistsException(ALREADY_EXISTING_USER_MESSAGE);
        } else {
            throw new IncorrectUserNameException(USERNAME_NOT_VALID_MESSAGE);
        }
    }

    /**
     * Updates user statistics in the json file.
     */
    public void updateUserStats() {
        JsonManager.updateUser(this);
    }

    /**
     * Creates a User object from a JSON representation.
     *
     * @param jsonObject The JSON object representing the user.
     * @return The User object created from the JSON.
     */
    public static User fromJSON(JSONObject jsonObject) {
        String nickname = (String) jsonObject.get("nickname");
        Integer gamesPlayed = ((Number) jsonObject.get("gamesPlayed")).intValue();
        Integer gamesWon = ((Number) jsonObject.get("gamesWon")).intValue();
        Integer gamesLost = ((Number) jsonObject.get("gamesLost")).intValue();
        Integer level = ((Number) jsonObject.get("level")).intValue();

        return new User(nickname, gamesPlayed, gamesWon, gamesLost, level);
    }

    /**
     * Converts the User object to a JSON representation.
     *
     * @return The JSON object representing the user.
     */
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nickname", this.nickname);
        jsonObject.put("gamesPlayed", this.gamesPlayed);
        jsonObject.put("gamesWon", this.gamesWon);
        jsonObject.put("gamesLost", this.gamesLost);
        jsonObject.put("level", this.level);
        return jsonObject;
    }

    /**
     * Increments the games played and games lost, updating user statistics.
     */
    public void addGameLost() {
        gamesPlayed += 1;
        gamesLost += 1;
        updateUserStats();
    }
	
    /**
     * Calculates the user's level based on the number of games won and predefined level-up steps.
     *
     * @return The calculated level.
     */
    private Integer calculateLevel() {
        if (LEVELUPSTEPS.contains(gamesWon)) {
            return level + 1;
        } else {
            return level;
        }
    }

    /**
     * Adds a won game, increments games played and games won, updates the level, and updates user statistics.
     */
    public void addGameWon() {
        gamesPlayed += 1;
        gamesWon += 1;
        level = calculateLevel();
        updateUserStats();
    }

    /**
     * Checks if a given username is valid.
     *
     * @param username The username to validate.
     * @return True if the username is valid, false otherwise.
     */
    public static boolean isUserNameValid(String username) {
        if (username != null && username.length() != 0) {
            for (int i = 0; i < username.length(); i++) {
                if (!Character.isLetterOrDigit(username.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Gets the nickname of the user.
     *
     * @return The user's nickname.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Gets the number of games played by the user.
     *
     * @return The number of games played.
     */
    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * Gets the number of games won by the user.
     *
     * @return The number of games won.
     */
    public Integer getGamesWon() {
        return gamesWon;
    }

    /**
     * Gets the number of games lost by the user.
     *
     * @return The number of games lost.
     */
    public Integer getGamesLost() {
        return gamesLost;
    }

    /**
     * Gets the level of the user.
     *
     * @return The user's level.
     */
    public Integer getLevel() {
        return level;
    }
	
}
