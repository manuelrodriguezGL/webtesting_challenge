package logger;

import java.util.Objects;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

public class EventLogger {

    private Logger logger;

    private String logPropertiesFile = "logging.properties";

    public EventLogger() {
        try {
            System.setProperty("java.util.logging.config.file",
                    Objects.requireNonNull(EventLogger.class.getClassLoader()
                            .getResource(logPropertiesFile)).getFile());
            logger = Logger.getGlobal();

        } catch (Exception e) {
            logger.log(SEVERE, "Error while creating logs: \n" + e.getMessage());
        }
    }

    public Logger getLogger() {
        return this.logger;
    }
}
