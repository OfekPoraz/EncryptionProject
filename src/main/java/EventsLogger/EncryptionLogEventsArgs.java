package EventsLogger;

import EncryptionAlgorithms.EncryptionAlgorithm;

import java.util.Objects;

public class EncryptionLogEventsArgs {
    private EncryptionAlgorithm algorithm;
    private String originalFilePath;
    private String outputFilePath;
    private long time;
    private final Events event;

    public EncryptionLogEventsArgs(EncryptionAlgorithm algorithm, String originalFilePath, String outputFilePath, long time, Events events) {
        this.algorithm = algorithm;
        this.originalFilePath = originalFilePath;
        this.outputFilePath = outputFilePath;
        this.time = time;
        this.event = events;
    }

    public String getLoggerMassage(EncryptionLogEventsArgs startingEvent) {
        String EncryptionOrDecryption = null;
        if (event == Events.EncryptionEnded) {
            EncryptionOrDecryption = "encryption" ;
        } else if (event == Events.DecryptionEnded) {
            EncryptionOrDecryption = "decryption" ;
        }
        return "The " + EncryptionOrDecryption + " of file " + originalFilePath + " with algorithm " +
                algorithm.getName() + " took " + (time - startingEvent.time) + " ms. The output file is located in " +
                outputFilePath + " " + event;
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalFilePath, outputFilePath, algorithm);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        EncryptionLogEventsArgs logEventsArgs = (EncryptionLogEventsArgs) obj;
        return originalFilePath.equals(logEventsArgs.originalFilePath) &&
                outputFilePath.equals(logEventsArgs.outputFilePath) &&
                algorithm.equals(logEventsArgs.algorithm);
    }
}

