package utilites;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import customExceptions.UnexistingUserException;
import model.User;

/**
 * Gestisce la lettura e la scrittura degli utenti in formato JSON.
 */
public class JsonManager {
    private static JSONParser jsonParser;
    private static String usersJsonFilePath = "users.json";

    /**
     * Carica un utente dal file JSON utilizzando il nickname fornito.
     *
     * @param nickname Il nickname dell'utente da caricare.
     * @return L'utente corrispondente al nickname fornito.
     * @throws UnexistingUserException Lanciato se l'utente non esiste nel file JSON.
     */
    public static User loadUser(String nickname) {
        loadParser();
        try (FileReader reader = new FileReader(usersJsonFilePath)) {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);

            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject) o;
                if (jsonObject.get("nickname").equals(nickname)) {
                    return User.fromJSON(jsonObject);
                }
            }
            throw new UnexistingUserException("Unexisting user!");
        } catch (Exception e) {
            LoggerUtil.logError(e.getMessage());
        }
        return null;
    }

    /**
     * Aggiunge un nuovo utente al file JSON.
     *
     * @param user L'utente da aggiungere.
     */
    public static void addUser(User user) {
        loadParser();
        JSONArray jsonArray = new JSONArray();
        try (FileReader reader = new FileReader(usersJsonFilePath)) {
            jsonArray = (JSONArray) jsonParser.parse(reader);
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

    /**
     * Aggiorna le informazioni dell'utente nel file JSON.
     *
     * @param user L'utente con le informazioni aggiornate.
     */
    public static void updateUser(User user) {
        loadParser();
        JSONArray jsonArray = new JSONArray();

        try (FileReader reader = new FileReader(usersJsonFilePath)) {
            jsonArray = (JSONArray) jsonParser.parse(reader);
            jsonArray.removeIf(o -> ((JSONObject) o).get("nickname").equals(user.getNickname()));
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

    /**
     * Verifica se un utente con il nickname fornito esiste nel file JSON.
     *
     * @param nickname Il nickname dell'utente da verificare.
     * @return true se l'utente esiste, false altrimenti.
     */
    public static boolean userExists(String nickname) {
        loadParser();
        JSONArray jsonArray = new JSONArray();
        try (FileReader reader = new FileReader(usersJsonFilePath)) {
            jsonArray = (JSONArray) jsonParser.parse(reader);
            long count = jsonArray.stream()
                    .filter(o -> ((JSONObject) o).get("nickname").equals(nickname))
                    .count();
            return count == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Carica il parser JSON se non è già stato caricato.
     */
    private static void loadParser() {
        if (jsonParser == null)
            jsonParser = new JSONParser();
    }
}
