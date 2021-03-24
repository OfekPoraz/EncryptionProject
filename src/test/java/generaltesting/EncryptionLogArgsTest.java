package generaltesting;

import encryptionalgorithms.EncryptionAlgorithm;
import encryptionalgorithms.ShiftMultiplyEncryption;
import encryptionalgorithms.ShiftUpEncryption;
import eventslogger.EncryptionLogEventsArgs;
import eventslogger.Events;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class EncryptionLogArgsTest {

    private EncryptionAlgorithm algorithm = new ShiftUpEncryption();
    private String originalFilePath = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\" +
            "resources\\Hi.txt";
    private String outputFilePath = "C:\\Users\\ofeko\\IdeaProjects\\EncryptionProject\\src\\main\\resources";
    private long time = 0;
    private Events event;
    private String fileOrDir = "file";

    public void setEvent(Events event) {
        this.event = event;
    }

    @Test
    public void encryptionEndedMsg(){
        setEvent(Events.ENCRYPTION_ENDED);
        EncryptionLogEventsArgs eventsArgs =
                new EncryptionLogEventsArgs(algorithm, originalFilePath, outputFilePath, time, event, fileOrDir);

        String msg = eventsArgs.getLoggerMassage(eventsArgs, "Ended");
        String expectedMsg = "The encryption of file " + originalFilePath + " with algorithm " +
                algorithm.getName() + " took 0 ms. The output file is located in " +
                outputFilePath + " " + event;
        assertEquals(msg, expectedMsg);
    }

    @Test
    public void encryptionStartedMsg(){
        setEvent(Events.ENCRYPTION_ENDED);
        EncryptionLogEventsArgs eventsArgs =
                new EncryptionLogEventsArgs(algorithm, originalFilePath, outputFilePath, time, event, fileOrDir);

        String msg = eventsArgs.getLoggerMassage(eventsArgs, "Started");
        String expectedMsg = "The encryption of " + fileOrDir + " " + originalFilePath + " with algorithm " +
                algorithm.getName() + " is starting";
        assertEquals(msg, expectedMsg);
    }

    @Test
    public void decryptionEndedMsg(){
        setEvent(Events.DECRYPTION_ENDED);
        EncryptionLogEventsArgs eventsArgs =
                new EncryptionLogEventsArgs(algorithm, originalFilePath, outputFilePath, time, event, fileOrDir);

        String msg = eventsArgs.getLoggerMassage(eventsArgs, "Ended");
        String expectedMsg = "The decryption of file " + originalFilePath + " with algorithm " +
                algorithm.getName() + " took 0 ms. The output file is located in " +
                outputFilePath + " " + event;
        assertEquals(msg, expectedMsg);
    }

    @Test
    public void decryptionStartedMsg(){
        setEvent(Events.DECRYPTION_ENDED);
        EncryptionLogEventsArgs eventsArgs =
                new EncryptionLogEventsArgs(algorithm, originalFilePath, outputFilePath, time, event, fileOrDir);

        String msg = eventsArgs.getLoggerMassage(eventsArgs, "Started");
        String expectedMsg = "The decryption of " + fileOrDir + " " + originalFilePath + " with algorithm " +
                algorithm.getName() + " is starting";
        assertEquals(msg, expectedMsg);
    }

    @Test
    public void encryptionErrorMsg(){
        setEvent(Events.ERROR);
        EncryptionLogEventsArgs eventsArgs =
                new EncryptionLogEventsArgs(algorithm, originalFilePath, outputFilePath, time, event, fileOrDir);
        String msg = eventsArgs.getLoggerMassage(eventsArgs, "Ended");
        String expectedMsg = "An ERROR occurred at time: " + time + "while trying to run, when processing " + originalFilePath +
                "destination: " + outputFilePath + " in algorithm: " + algorithm;
        assertEquals(msg, expectedMsg);
    }


    @Test
    public void compareSameTest(){
        setEvent(Events.ENCRYPTION_ENDED);
        EncryptionLogEventsArgs eventsArgs =
                new EncryptionLogEventsArgs(algorithm, originalFilePath, outputFilePath, time, event, fileOrDir);
        setEvent(Events.ENCRYPTION_STARTED);
        EncryptionLogEventsArgs eventsArgs1 =
                new EncryptionLogEventsArgs(algorithm, originalFilePath, outputFilePath, time, event, fileOrDir);
        boolean equals = eventsArgs.equals(eventsArgs1);
        assertTrue(equals);
    }

    @Test
    public void compareIdenticalTest(){
        setEvent(Events.ENCRYPTION_ENDED);
        EncryptionLogEventsArgs eventsArgs =
                new EncryptionLogEventsArgs(algorithm, originalFilePath, outputFilePath, time, event, fileOrDir);
        boolean equals = eventsArgs.equals(eventsArgs);
        assertTrue(equals);
    }

    @Test
    public void compareDiffTest(){
        setEvent(Events.ENCRYPTION_ENDED);
        EncryptionLogEventsArgs eventsArgs =
                new EncryptionLogEventsArgs(algorithm, originalFilePath, outputFilePath, time, event, fileOrDir);
        setEvent(Events.ENCRYPTION_STARTED);
        EncryptionLogEventsArgs eventsArgs1 =
                new EncryptionLogEventsArgs(new ShiftMultiplyEncryption(),
                        originalFilePath, outputFilePath, time, event, fileOrDir);
        boolean equals = eventsArgs.equals(eventsArgs1);
        assertFalse(equals);
    }

    @Test
    public void compareNullTest(){
        setEvent(Events.ENCRYPTION_ENDED);
        EncryptionLogEventsArgs eventsArgs =
                new EncryptionLogEventsArgs(algorithm, originalFilePath, outputFilePath, time, event, fileOrDir);
        boolean equals = eventsArgs.equals(null);
        assertFalse(equals);
    }
    @Ignore("hash code changes sometimes")
    @Test
    public void hashCodeCheck(){
        setEvent(Events.ENCRYPTION_ENDED);
        EncryptionLogEventsArgs eventsArgs =
                new EncryptionLogEventsArgs(algorithm, originalFilePath, outputFilePath, time, event, fileOrDir);
        assertEquals(34439046, eventsArgs.hashCode());
    }
}
