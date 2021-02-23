package EventsLogger;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EncryptionLog4JLogger {
        private static final Logger LOGGER = LogManager.getLogger(EncryptionLog4JLogger.class.getName());


        public void writeToLogger(String logMassage, Level level){
            LOGGER.log(level, logMassage);
        }
    }

