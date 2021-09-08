package logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerFacade {
    protected static final Logger LOGGER = LoggerFactory.getLogger(LoggerFacade.class);

    public final void info(String message) {
        LOGGER.info("INFO - " + message);
    }
}
