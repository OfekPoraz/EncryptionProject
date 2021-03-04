package DirectoryEncryptor;

import EncryptionAlgorithms.EncryptionAlgorithm;
import EventsLogger.EncryptionLogEventsArgs;
import EventsLogger.Events;
import Exceptions.InvalidKeyException;
import Keys.Key;
import Utils.FileOperations;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

public abstract class DirectoryProcessor extends Observable implements IDirectoryProcessor {

    private FileOperations keyFile;
    private EncryptionAlgorithm encryptionAlgorithm;
    private FileOperations directoryOperations;
    private String pathToDir;

    public DirectoryProcessor(FileOperations keyFile, EncryptionAlgorithm encryptionAlgorithm, String pathToDir) {
        this.keyFile = keyFile;
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.directoryOperations = new FileOperations(pathToDir);
        this.pathToDir = pathToDir;
    }

    public FileOperations getDirectoryOperations() {
        return directoryOperations;
    }

    public String getPathToDir() {
        return pathToDir;
    }

    public EncryptionAlgorithm getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    public FileOperations getKeyFile() {
        return keyFile;
    }

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

    public void generateKey() throws IOException, InvalidKeyException {
        Key key = new Key();
        try {
            int k = (int)key.getKey();
        } catch (ClassCastException e){
            setEvent("Error in casting key", Events.Error, encryptionAlgorithm);
            throw new InvalidKeyException("Invalid key, not an int");
        }
        this.keyFile.writeToFile(Integer.toString(key.getKey()));
    }

    public String[] getFileNames() {
        String[] filesToEncrypt = directoryOperations.getFilesFromDirectoryTxt();
        List<String> filesToEncryptList = new ArrayList<>(Arrays.asList(filesToEncrypt));
        filesToEncryptList.removeIf(file -> file.contains("Key"));
        filesToEncrypt = filesToEncryptList.toArray(new String[0]);
        return filesToEncrypt;
    }
}
