package DirectoryEncryptor;

import EncryptionAlgorithms.EncryptionAlgorithm;
import EventsLogger.EncryptionLog4JLogger;
import EventsLogger.EncryptionLogEventsArgs;
import EventsLogger.Events;
import Exceptions.InvalidKeyException;
import Keys.Key;
import Utils.FileOperations;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

public class ASyncDirectoryEncryptor extends Observable implements IDirectoryProcessor {
    private final EncryptionLog4JLogger log4JLogger = new EncryptionLog4JLogger();
    private String pathToDir;
    private FileOperations directoryOperations;

    private FileOperations keyFile;
    private EncryptionAlgorithm encryptionAlgorithm;
    private List<Thread> encryptThreadList = new ArrayList<>();
    private List<Thread> decryptThreadList = new ArrayList<>();

    private FileOperations encryptedDir;
    private FileOperations decryptedDir;

    public ASyncDirectoryEncryptor(String pathToDir, FileOperations keyFile,
                                   EncryptionAlgorithm encryptionAlgorithm) throws IOException, InvalidKeyException {
        this.pathToDir = pathToDir;
        this.directoryOperations = new FileOperations(pathToDir);
        this.keyFile = keyFile;
        this.encryptionAlgorithm = encryptionAlgorithm;

        this.encryptedDir = new FileOperations(pathToDir, encryptionAlgorithm.getNameOfEncryption() + "encryptedSynchronized", true);
        this.decryptedDir = new FileOperations(pathToDir, encryptionAlgorithm.getNameOfEncryption() + "decryptedSynchronized", true);
        generateKey();
    }

    public void generateKey() throws IOException, InvalidKeyException {
        Key key = new Key();
        try {
            int k = (int)key.getKey();
        } catch (ClassCastException e){
            throw new InvalidKeyException("Invalid key, not an int", e);
        }
        this.keyFile.writeToFile(Integer.toString(key.getKey()));
    }

    public synchronized void setEvent(String originalPath, String outputFile, Events event) {
        log4JLogger.writeToLogger("Setting a new event for algorithm: " + encryptionAlgorithm.getNameOfEncryption(), Level.INFO);
        long time = System.currentTimeMillis();
        EncryptionLogEventsArgs eventsArgs = new EncryptionLogEventsArgs(encryptionAlgorithm, originalPath, outputFile, time, event, "Directory");
        setChanged();
        notifyObservers(eventsArgs);
    }

    public String[] getFileNames() {
        String[] filesToEncrypt = directoryOperations.getFilesFromDirectoryTxt();
        List<String> filesToEncryptList = new ArrayList<>(Arrays.asList(filesToEncrypt));
        filesToEncryptList.removeIf(file -> file.contains("Key"));
        filesToEncrypt = filesToEncryptList.toArray(new String[0]);
        return filesToEncrypt;
    }


    @Override
    public void encryptDirectory() {
        setEvent(pathToDir, encryptedDir.getPathToFile() , Events.EncryptionStarted);
        for (String file : getFileNames()) {
            Thread t = new ASyncDirectoryProcessor(pathToDir + File.separator + file, encryptionAlgorithm, keyFile, encryptedDir, decryptedDir, true);
            encryptThreadList.add(t);
            t.start();
        }

        for (Thread t : encryptThreadList){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        setEvent(pathToDir, encryptedDir.getPathToFile() , Events.EncryptionEnded);
    }

    @Override
    public void decryptDirectory() throws IOException {
        setEvent(pathToDir, encryptedDir.getPathToFile() , Events.DecryptionStarted);
        for (String file : getFileNames()) {
            Thread t = new ASyncDirectoryProcessor(pathToDir + File.separator + file, encryptionAlgorithm, keyFile, encryptedDir, decryptedDir, false);
            decryptThreadList.add(t);
            t.start();
        }

        for (Thread t : decryptThreadList){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        setEvent(pathToDir, encryptedDir.getPathToFile() , Events.DecryptionEnded);

    }
}
