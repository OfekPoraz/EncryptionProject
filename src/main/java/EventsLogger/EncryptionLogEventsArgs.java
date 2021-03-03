package EventsLogger;

import EncryptionAlgorithms.EncryptionAlgorithm;

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
        String EncryptionOrDecryption = null;
        if (event == Events.EncryptionEnded || event == Events.EncryptionStarted) {
            EncryptionOrDecryption = "encryption";
        } else if (event == Events.DecryptionEnded || event == Events.DecryptionStarted) {
            EncryptionOrDecryption = "decryption";
        }
        if (state.equals("Ended")) {
            return "The " + EncryptionOrDecryption + " of " + fileOrDir + " " + originalFilePath + " with algorithm " +
                    algorithm.getName() + " took " + (time - startingEvent.time) + " ms. The output file is located in " +
                    outputFilePath + " " + event;
        }
        else{
            return "The " + EncryptionOrDecryption + " of " + fileOrDir + " " + originalFilePath + " with algorithm " +
                    algorithm.getName() + " is starting";
        }

    }

    public String getLoggerMassage(EncryptionLogEventsArgs eventsArgs){
        String event = eventsArgs.getEvent().toString();
        if (event.equals("Error")){
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

