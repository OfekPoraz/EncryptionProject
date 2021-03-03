package EventsLogger;

import org.apache.logging.log4j.Level;

import java.util.*;

public class EncryptionLogger implements Observer {
    private Observable observableEncryptor;
    private static Map<EncryptionLogEventsArgs, EncryptionLogEventsArgs> allEventsUntilNow = new HashMap<>();
    private final EncryptionLog4JLogger log4JLogger;

    public EncryptionLogger(Observable observableEncryptor, EncryptionLog4JLogger log4JLogger) {
        this.observableEncryptor = observableEncryptor;
        this.log4JLogger = log4JLogger;
        observableEncryptor.addObserver(this);
    }

    public EncryptionLogger(Observable observableEncryptor) {
        this(observableEncryptor, new EncryptionLog4JLogger());
    }


    @Override
    public void update(Observable o, Object arg) {
        EncryptionLogEventsArgs eventsArgs = (EncryptionLogEventsArgs) arg;
        if (eventsArgs.getEvent().toString().contains("cryption")) {
            if (allEventsUntilNow.containsKey(eventsArgs)) {
                EncryptionLogEventsArgs previousEvent = allEventsUntilNow.get(eventsArgs);
                    if (eventsArgs.equals(previousEvent)) {
                        String loggerMsg = eventsArgs.getLoggerMassage(previousEvent, "Ended");
                        if (o.equals(observableEncryptor)) {
                            log4JLogger.writeToLogger(loggerMsg, Level.INFO);
                        }
                    }
                allEventsUntilNow.remove(eventsArgs);
            } else {
                String loggerMsg = eventsArgs.getLoggerMassage(eventsArgs, "Started");
                log4JLogger.writeToLogger(loggerMsg, Level.INFO);
                allEventsUntilNow.put(eventsArgs, eventsArgs);
            }
        } else{
            String loggerMsg = eventsArgs.getLoggerMassage(eventsArgs);
            if (eventsArgs.getEvent().toString().equals("Error")) {
                log4JLogger.writeToLogger(loggerMsg, Level.ERROR);
            } else {
                log4JLogger.writeToLogger(loggerMsg, Level.DEBUG);
            }
        }

    }

}
