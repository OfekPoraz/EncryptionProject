package directoryencryptor;

import encryptionalgorithms.EncryptionAlgorithm;
import eventslogger.EncryptionLogEventsArgs;
import eventslogger.Events;
import keys.Key;
import utils.FileOperations;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

public abstract class DirectoryProcessor extends Observable implements IDirectoryProcessor {

    private FileOperations keyFile;
    private EncryptionAlgorithm encryptionAlgorithm;
    private FileOperations directoryOperations;
    private String pathToDir;
    private FileOperations encryptedDirectoryOperations;
    private FileOperations decryptedDirectoryOperations;


    protected DirectoryProcessor(FileOperations keyFile, EncryptionAlgorithm encryptionAlgorithm, String pathToDir) {
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

    public FileOperations getEncryptedDirectoryOperations() {
        return encryptedDirectoryOperations;
    }

    public void setEncryptedDirectoryOperations(FileOperations encryptedDirectoryOperations) {
        this.encryptedDirectoryOperations = encryptedDirectoryOperations;
    }

    public FileOperations getDecryptedDirectoryOperations() {
        return decryptedDirectoryOperations;
    }

    public void setDecryptedDirectoryOperations(FileOperations decryptedDirectoryOperations) {
        this.decryptedDirectoryOperations = decryptedDirectoryOperations;
    }

    public void setEvent(String originalPath, String outputFile, Events event, EncryptionAlgorithm algorithm) {
        long time = System.currentTimeMillis();
        EncryptionLogEventsArgs eventsArgs = new EncryptionLogEventsArgs(algorithm, originalPath, outputFile, time, event, "Directory");
        setChanged();
        notifyObservers(eventsArgs);
    }

    public void generateKey() {
        Key key = new Key();
        this.keyFile.writeToFile(Integer.toString(key.getRandomKey()));
    }

    public String[] getFileNames() {
        String[] filesToEncrypt = directoryOperations.getFilesFromDirectoryTxt();
        List<String> filesToEncryptList = new ArrayList<>(Arrays.asList(filesToEncrypt));
        filesToEncryptList.removeIf(file -> file.contains("Key"));
        filesToEncrypt = filesToEncryptList.toArray(new String[0]);
        return filesToEncrypt;
    }
}
