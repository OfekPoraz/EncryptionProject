package FileEncryptor;

import EncryptionAlgorithms.EncryptionAlgorithm;
import EventsLogger.EncryptionLog4JLogger;
import EventsLogger.EncryptionLogEventsArgs;
import EventsLogger.EncryptionLogger;
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
        log4JLogger.writeToLogger("Creating FileEncryptor Instance for " + nameOfAlgorithm, Level.DEBUG);
        this.fileToEncrypt = new FileOperations(pathToFile);
        this.encryptedFile = new FileOperations(fileToEncrypt.getParentPathToFile(), "Encrypted"+ nameOfAlgorithm);
        this.decryptedFile = new FileOperations(fileToEncrypt.getParentPathToFile(), "Decrypted"+ nameOfAlgorithm);
        this.keyFile = new FileOperations(fileToEncrypt.getParentPathToFile(), "Key" + nameOfAlgorithm);
        addAlgorithmObserver();
    }

    public FileEncryptor(EncryptionAlgorithm algorithm, FileOperations fileToEncrypt, FileOperations encryptedFile,
                         FileOperations decryptedFile, FileOperations keyFile){
        this.algorithm = algorithm;
        this.nameOfAlgorithm = algorithm.getNameOfEncryption();
        this.fileToEncrypt = fileToEncrypt;
        this.encryptedFile = encryptedFile;
        this.decryptedFile = decryptedFile;
        this.keyFile = keyFile;
        addAlgorithmObserver();
    }

    public void addAlgorithmObserver(){
        algorithm.addObserver(new EncryptionLogger(algorithm));
    }

    public int generateKey(FileOperations keyFile, boolean isDir) throws InvalidKeyException, IOException {
        int k;
        if (!isDir){
            Key key = new Key();
            try {
                k = (int) key.getKey();
            } catch (ClassCastException e){
                setEvent("Error in casting key", Events.Error);
                throw new InvalidKeyException("Invalid key, not an int");
            }
            keyFile.writeToFile(Integer.toString(key.getKey()));
        } else {
            k = Integer.parseInt(keyFile.readFromFile().strip());
        }
        return k;
    }


    public void encryptFile(Boolean isDir) throws IOException, InvalidKeyException {
        String textToEncrypt = fileToEncrypt.readFromFile();
        setEvent(fileToEncrypt.getPathToFile(), encryptedFile.getPathToFile(), Events.EncryptionStarted);
        String encryptedData = algorithm.encryptString(textToEncrypt, generateKey(keyFile, isDir));
        setEvent(fileToEncrypt.getPathToFile(), encryptedFile.getPathToFile(), Events.EncryptionEnded);
        encryptedFile.writeToFile(encryptedData);
        setEvent("Saving encryption results to file and key to key file for algorithm: " + nameOfAlgorithm, Events.Debug);
    }

    public void decryptFile() throws IOException, NullPointerException {
        int key = Integer.parseInt(keyFile.readFromFile().strip());
        String dataToDecrypt = encryptedFile.readFromFile();
        setEvent(encryptedFile.getPathToFile(), decryptedFile.getPathToFile(), Events.DecryptionStarted);
        String decryptedData = algorithm.decryptString(dataToDecrypt, key);
        setEvent(encryptedFile.getPathToFile(), decryptedFile.getPathToFile(), Events.DecryptionEnded);
        decryptedFile.writeToFile(decryptedData);
    }

    public String getPathToDecryptedFile(){
        return this.decryptedFile.getPathToFile();
    }


    public void setEvent(String originalPath, String outputFile, Events event){
        long time = System.currentTimeMillis();
        EncryptionLogEventsArgs eventsArgs = new EncryptionLogEventsArgs(this.algorithm, originalPath, outputFile, time, event, "file");
        setChanged();
        notifyObservers(eventsArgs);
    }

    public void setEvent(String massage, Events event){
        long time = System.currentTimeMillis();
        EncryptionLogEventsArgs eventsArgs = new EncryptionLogEventsArgs(massage, event, time, this.algorithm);
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
