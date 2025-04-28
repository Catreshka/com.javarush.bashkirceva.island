package utils;

/**
 * Утильный класс для логирования
 */

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logs {

    private static final Logger logger = Logger.getLogger(Logs.class.getName());

    static {
        logger.setLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
    }

    /**
     * Логирование в местах, где есть исключения
     */
    public static void logError(String message, Throwable t) {
        logger.log(Level.SEVERE, message, t);
    }

    /**
     * Логирование для информации
     */
    public static void logInfo(String message) {
        logger.log(Level.INFO, message);
    }
}
