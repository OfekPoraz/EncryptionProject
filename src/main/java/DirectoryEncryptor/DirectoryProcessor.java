package DirectoryEncryptor;

import EncryptionAlgorithms.EncryptionAlgorithm;
import EventsLogger.EncryptionLogEventsArgs;
import EventsLogger.Events;
import org.apache.logging.log4j.Level;

import java.util.Observable;

public abstract class DirectoryProcessor extends Observable implements IDirectoryProcessor {

    public void setEvent(String massage, Events event, EncryptionAlgorithm algorithm){
        long time = System.currentTimeMillis();
        EncryptionLogEventsArgs eventsArgs = new EncryptionLogEventsArgs(massage, event, time, algorithm);
        setChanged();
        notifyObservers(eventsArgs);
    }

    public void setEvent(String originalPath, String outputFile, Events event, EncryptionAlgorithm algorithm) {
        long time = System.currentTimeMillis();
        EncryptionLogEventsArgs eventsArgs = new EncryptionLogEventsArgs(algorithm, originalPath, outputFile, time, event, "Directory");
        setChanged();
        notifyObservers(eventsArgs);
    }

}
