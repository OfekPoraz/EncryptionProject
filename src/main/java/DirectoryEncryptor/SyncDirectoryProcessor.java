package DirectoryEncryptor;

import EncryptionAlgorithms.EncryptionAlgorithm;
import EventsLogger.EncryptionLog4JLogger;
import EventsLogger.EncryptionLogEventsArgs;
import EventsLogger.EncryptionLogger;
import EventsLogger.Events;
import Exceptions.InvalidKeyException;
import FileEncryptor.FileEncryptor;
import Keys.Key;
import Utils.FileOperations;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class SyncDirectoryProcessor extends Observable implements IDirectoryProcessor{
    private final EncryptionLog4JLogger log4JLogger = new EncryptionLog4JLogger();

    private String pathToDir;
    private FileOperations directoryOperations;
    private FileOperations encryptedDirectoryOperations;
    private FileOperations decryptedDirectoryOperations;
    private FileOperations keyFile;
    private EncryptionAlgorithm encryptionAlgorithm;
    private List<FileEncryptor> fileEncryptorList = new LinkedList<>();

    public SyncDirectoryProcessor(String pathToFile, EncryptionAlgorithm encryptionAlgorithm) throws IOException, InvalidKeyException {
        this.pathToDir = pathToFile;
        this.keyFile = new FileOperations(pathToFile, "DirectoryKey" + encryptionAlgorithm.getNameOfEncryption());
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.directoryOperations = new FileOperations(pathToFile);
        generateKey();
        createFiles();
        setObserversForFiles();
    }

    public String getClearName(String name){
        return name.replaceFirst("[.][^.]+$", "");
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

    public void createFiles() throws IOException {
        this.encryptedDirectoryOperations = new FileOperations(pathToDir, encryptionAlgorithm.getNameOfEncryption() + "encrypted", true);
        this.decryptedDirectoryOperations = new FileOperations(pathToDir, encryptionAlgorithm.getNameOfEncryption() + "decrypted", true);
        String[] filesToEncrypt = directoryOperations.getFilesFromDirectoryTxt();
        for (String file : filesToEncrypt) {
            if (!file.contains("Key")) {
                FileOperations fileToEncrypt = new FileOperations(encryptedDirectoryOperations.getParentPathToFile() + File.separator + file);
                FileOperations encryptedFile = new FileOperations(encryptedDirectoryOperations.getPathToFile(), getClearName(file));
                FileOperations decryptedFile = new FileOperations(decryptedDirectoryOperations.getPathToFile(), getClearName(file));
                FileEncryptor fileEncryptor = new FileEncryptor(encryptionAlgorithm, fileToEncrypt, encryptedFile, decryptedFile, keyFile);
                fileEncryptorList.add(fileEncryptor);
            }
        }
    }

    public void setObserversForFiles(){
        for (FileEncryptor fileEncryptor : fileEncryptorList){
            fileEncryptor.addObserver(new EncryptionLogger(fileEncryptor));
        }
    }

    @Override
    public void encryptDirectory() throws InvalidKeyException, IOException {
        setEvent(directoryOperations.getPathToFile(), encryptedDirectoryOperations.getPathToFile(), Events.EncryptionStarted);
        for (FileEncryptor fileEncryptor : fileEncryptorList){
            fileEncryptor.encryptFile(true);
        }
        setEvent(directoryOperations.getPathToFile(), encryptedDirectoryOperations.getPathToFile(), Events.EncryptionEnded);
    }

    @Override
    public void decryptDirectory() throws IOException {
        setEvent(encryptedDirectoryOperations.getPathToFile(), decryptedDirectoryOperations.getPathToFile(), Events.DecryptionStarted);
        for (FileEncryptor fileEncryptor : fileEncryptorList){
            fileEncryptor.decryptFile();
        }
        setEvent(encryptedDirectoryOperations.getPathToFile(), decryptedDirectoryOperations.getPathToFile(), Events.DecryptionEnded);
    }

    public void setEvent(String originalPath, String outputFile, Events event){
        log4JLogger.writeToLogger("Setting a new event for algorithm: " + encryptionAlgorithm.getNameOfEncryption(), Level.INFO);
        long time = System.currentTimeMillis();
        EncryptionLogEventsArgs eventsArgs = new EncryptionLogEventsArgs(encryptionAlgorithm, originalPath, outputFile, time, event, "Directory");
        setChanged();
        notifyObservers(eventsArgs);
    }
}
