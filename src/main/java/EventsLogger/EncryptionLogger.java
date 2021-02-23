package EventsLogger;

import org.apache.logging.log4j.Level;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class EncryptionLogger implements Observer {
    private Observable observableEncryptor;
    private static Set<EncryptionLogEventsArgs> allEventsUntilNow = new HashSet<>();
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
        if (allEventsUntilNow.contains(eventsArgs)){
            for (EncryptionLogEventsArgs previousEvent : allEventsUntilNow){
                if (eventsArgs.equals(previousEvent)){
                    String loggerMsg = eventsArgs.getLoggerMassage(previousEvent);
                    if (o.equals(observableEncryptor)){
                        log4JLogger.writeToLogger(loggerMsg, Level.INFO);
                    }
                }
            }
            allEventsUntilNow.remove(eventsArgs);
        } else {
            allEventsUntilNow.add(eventsArgs);
        }

    }
}
