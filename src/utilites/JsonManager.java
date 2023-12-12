package utilites;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import customExceptions.UnexistingUserException;
import model.Game;
import model.User;

public class JsonManager {
	private static JSONParser jsonParser ;
	private static String usersJsonFilePath = "users.json";
	
	public static User loadUser (String nickname) {
		loadParser();
        try (FileReader reader = new FileReader(usersJsonFilePath)) {
        	JSONArray jsonArray = (JSONArray)jsonParser.parse(reader);
        	//Usare streams
            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject) o;
                if (jsonObject.get("nickname").equals(nickname) ) {
                	return User.fromJSON(jsonObject);
                }
            }
        	throw new UnexistingUserException("Unexisting user!");
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	

	public static void addUser(User user) {
		loadParser();
		JSONArray jsonArray = new JSONArray();
        try (FileReader reader = new FileReader(usersJsonFilePath)) {
        	jsonArray = (JSONArray)jsonParser.parse(reader);
            jsonArray.add(user.toJSON());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try (FileWriter writer = new FileWriter(usersJsonFilePath)) {
            writer.write(jsonArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void updateUser(User user) {
		loadParser();
		JSONArray jsonArray = new JSONArray();
		
        try (FileReader reader = new FileReader(usersJsonFilePath)) {
        	jsonArray = (JSONArray)jsonParser.parse(reader);
        	jsonArray.removeIf( o -> ((JSONObject)o).get("nickname").equals(user.getNickname()) );
        	jsonArray.add(user.toJSON());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try (FileWriter writer = new FileWriter(usersJsonFilePath)) {
            writer.write(jsonArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static boolean userExists (String nicnkame) {
		loadParser();
		JSONArray jsonArray = new JSONArray();
        try (FileReader reader = new FileReader(usersJsonFilePath)) {
        	jsonArray = (JSONArray)jsonParser.parse(reader);
        	long count = jsonArray.stream()
        	.filter( o -> ((JSONObject)o).get("nickname").equals(nicnkame) )
        	.count();
        	return count == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
        
	}
	
	private static void loadParser() {
		if (jsonParser == null)
			jsonParser = new JSONParser();
	}

}
