package eventslogger;

import encryptionalgorithms.EncryptionAlgorithm;

import java.util.Objects;

public class EncryptionLogEventsArgs {
    private EncryptionAlgorithm algorithm;
    private String originalFilePath;
    private String outputFilePath;
    private long time;
    private Events event;
    private String fileOrDir;
    private String msg;

    public EncryptionLogEventsArgs(EncryptionAlgorithm algorithm, String originalFilePath, String outputFilePath,
                                   long time, Events events, String fileOrDir) {
        this.algorithm = algorithm;
        this.originalFilePath = originalFilePath;
        this.outputFilePath = outputFilePath;
        this.time = time;
        this.event = events;
        this.fileOrDir = fileOrDir;
    }

    public EncryptionLogEventsArgs(String msg, Events events, long time, EncryptionAlgorithm algorithm){
        this.event = events;
        this.msg = msg;
        this.time = time;
        this.algorithm = algorithm;
    }

    public String getLoggerMassage(EncryptionLogEventsArgs startingEvent, String state) {
        String encryptionOrDecryption = null;
        if (event == Events.ENCRYPTION_ENDED || event == Events.ENCRYPTION_STARTED) {
            encryptionOrDecryption = "encryption";
        } else if (event == Events.DECRYPTION_ENDED || event == Events.DECRYPTION_STARTED) {
            encryptionOrDecryption = "decryption";
        }
        if (event == Events.DEBUG || event == Events.ERROR){
            return getLoggerMassage(startingEvent);
        }
        if (state.equals("Ended")) {
            return "The " + encryptionOrDecryption + " of " + fileOrDir + " " + originalFilePath + " with algorithm " +
                    algorithm.getName() + " took " + (time - startingEvent.time) + " ms. The output file is located in " +
                    outputFilePath + " " + event;
        }

        else{
            return "The " + encryptionOrDecryption + " of " + fileOrDir + " " + originalFilePath + " with algorithm " +
                    algorithm.getName() + " is starting";
        }
    }

    public String getLoggerMassage(EncryptionLogEventsArgs eventsArgs){
        String currentEvent = eventsArgs.getEvent().toString();
        if (currentEvent.equals("ERROR")){
            return "An ERROR occurred at time: " + time + "while trying to run, when processing " + originalFilePath +
                    "destination: " + outputFilePath + " in algorithm: " + algorithm;
        } else{
            return msg + " while in " + algorithm;
        }
    }

    public Events getEvent() {
        return event;
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

