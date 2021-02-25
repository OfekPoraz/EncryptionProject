package FileEncryptor;

import EncryptionAlgorithms.EncryptionAlgorithm;
import EventsLogger.EncryptionLog4JLogger;
import EventsLogger.EncryptionLogEventsArgs;
import EventsLogger.Events;
import Keys.Key;
import Utils.FileOperations;
import Exceptions.InvalidKeyException;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.util.Observable;

public class FileEncryptor extends Observable implements Comparable<FileEncryptor> {

    private String nameOfAlgorithm;
    private EncryptionAlgorithm algorithm;
    private FileOperations fileToEncrypt;
    private FileOperations encryptedFile;
    private FileOperations decryptedFile;
    private FileOperations keyFile;
    private final EncryptionLog4JLogger log4JLogger = new EncryptionLog4JLogger();


    public FileEncryptor(EncryptionAlgorithm algorithm, String pathToFile) throws IOException, NullPointerException {
        this.algorithm = algorithm;
        this.nameOfAlgorithm = algorithm.getNameOfEncryption();
        log4JLogger.writeToLogger("Creating FileEncryptor Instance for " + nameOfAlgorithm, Level.INFO);
        this.fileToEncrypt = new FileOperations(pathToFile);
        this.encryptedFile = new FileOperations(fileToEncrypt.getParentPathToFile(), "Encrypted"+ nameOfAlgorithm);
        this.decryptedFile = new FileOperations(fileToEncrypt.getParentPathToFile(), "Decrypted"+ nameOfAlgorithm);
        this.keyFile = new FileOperations(fileToEncrypt.getParentPathToFile(), "Key" + nameOfAlgorithm);
    }

    public FileEncryptor(EncryptionAlgorithm algorithm, FileOperations fileToEncrypt, FileOperations encryptedFile,
                         FileOperations decryptedFile, FileOperations keyFile){
        this.algorithm = algorithm;
        this.nameOfAlgorithm = algorithm.getNameOfEncryption();
        this.fileToEncrypt = fileToEncrypt;
        this.encryptedFile = encryptedFile;
        this.decryptedFile = decryptedFile;
        this.keyFile = keyFile;
    }

    public int generateKey(FileOperations keyFile, boolean isDir) throws InvalidKeyException, IOException {
        int k;
        if (!isDir){
            Key key = new Key();
            try {
                k = (int)key.getKey();
            } catch (ClassCastException e){
                log4JLogger.writeToLogger("Error in casting key", Level.ERROR);
                throw new InvalidKeyException("Invalid key, not an int", e);
            }
            keyFile.writeToFile(Integer.toString(key.getKey()));
        } else {
            k = Integer.parseInt(keyFile.readFromFile().strip());
        }
        return k;
    }


    public void encryptFile(Boolean isDir) throws IOException, NullPointerException, InvalidKeyException {
        setEvent(fileToEncrypt.getPathToFile(), encryptedFile.getPathToFile(), Events.EncryptionStarted);
        String textToEncrypt = fileToEncrypt.readFromFile();
        String encryptedData = algorithm.encryptString(textToEncrypt, generateKey(keyFile, isDir));
        encryptedFile.writeToFile(encryptedData);
        log4JLogger.writeToLogger("Saving encryption results to file and key to key file for algorithm: " + nameOfAlgorithm, Level.INFO);
        setEvent(fileToEncrypt.getPathToFile(), encryptedFile.getPathToFile(), Events.EncryptionEnded);
    }

    public void decryptFile() throws IOException, NullPointerException {
        setEvent(encryptedFile.getPathToFile(), decryptedFile.getPathToFile(), Events.DecryptionStarted);
        int key = Integer.parseInt(keyFile.readFromFile().strip());
        String dataToDecrypt = encryptedFile.readFromFile();
        String decryptedData = algorithm.decryptString(dataToDecrypt, key);
        decryptedFile.writeToFile(decryptedData);
        setEvent(encryptedFile.getPathToFile(), decryptedFile.getPathToFile(), Events.DecryptionEnded);
    }

    public String getPathToDecryptedFile(){
        return this.decryptedFile.getPathToFile();
    }


    public void setEvent(String originalPath, String outputFile, Events event){
        log4JLogger.writeToLogger("Setting a new event for algorithm: " + nameOfAlgorithm, Level.INFO);
        long time = System.currentTimeMillis();
        EncryptionLogEventsArgs eventsArgs = new EncryptionLogEventsArgs(algorithm, originalPath, outputFile, time, event, "file");
        setChanged();
        notifyObservers(eventsArgs);
    }

    @Override
    public int compareTo(FileEncryptor o) {
        try {
            return this.algorithm.getKeyStrength(Integer.parseInt(this.keyFile.readFromFile().strip())) -
                    o.algorithm.getKeyStrength(Integer.parseInt(o.keyFile.readFromFile().strip()));
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
