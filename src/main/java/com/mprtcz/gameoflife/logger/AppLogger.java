package com.mprtcz.gameoflife.logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.*;

/**
 * @author Michal_Partacz
 */
public class AppLogger {
    private static final Logger logger = Logger.getLogger(AppLogger.class.getName());
    private static Handler handler = null;
    private static final Level DEFAULT_LEVEL = Level.CONFIG;
    private static boolean isInitialized = false;

    /**
     * This method will initialize the logger with the path and a name of the logfile, level of the logging info and
     * a formatter which will modify the logged information.
     */
    public static void initializeLogger() {
        if (isInitialized) {
            return;
        }
        try {
            handler = MyFileHandler.createLoggerInstance("gameoflife.log", false);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not create file", e);
        }
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
        logger.setLevel(Level.CONFIG);
        isInitialized = true;
    }

    public static void logDefault(String message) {
        logger.log(DEFAULT_LEVEL, message);
    }

    private AppLogger() {}

    private static class MyFileHandler extends FileHandler {

        static MyFileHandler createLoggerInstance(String filename, boolean append) throws IOException {
            String folderName = "logs";
            Path logFilePath = Paths.get(folderName,filename);
            if (createLogFolder(folderName)) {
                return new MyFileHandler(logFilePath.toString(), append);
            } else {
                return new MyFileHandler(filename, append);
            }
        }

        private MyFileHandler(String pattern, boolean append) throws IOException, SecurityException {
            super(pattern, append);
        }

        private static boolean createLogFolder(String folderName) {
            File dir = new File(folderName);
            return dir.exists()|| dir.mkdir();
        }
    }
}
