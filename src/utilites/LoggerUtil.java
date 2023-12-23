package utilites;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The LoggerUtil class provides utility methods for logging information and errors.
 */
public class LoggerUtil {
    private static final Logger logger = Logger.getLogger(LoggerUtil.class.getName());

    /**
     * Logs an information message.
     * @param message The information message to be logged.
     */
    public static void logInfo(String message) {
        logger.info(message);
    }

    /**
     * Logs an error message with a warning level.
     * @param message The error message to be logged.
     */
    public static void logError(String message) {
        logger.log(Level.WARNING, message);
    }
}
