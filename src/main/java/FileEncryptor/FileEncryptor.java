package FileEncryptor;

import EncryptionAlgorithms.EncryptionAlgorithm;
import EventsLogger.EncryptionLogEventsArgs;
import EventsLogger.Events;
import Keys.Key;
import Utils.FileOperations;
import Exceptions.InvalidKeyException;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Observable;

public class FileEncryptor extends Observable {

    private String nameOfAlgorithm;
    private EncryptionAlgorithm algorithm;
    private FileOperations fileToEncrypt;
    private FileOperations encryptedFile;
    private FileOperations decryptedFile;
    private FileOperations keyFile;
    private FileOperations keyFileForDouble = null;
    private Boolean isInDirectory = false;

    public FileEncryptor(EncryptionAlgorithm algorithm, String pathToFile) throws IOException, NullPointerException {
        this.algorithm = algorithm;
        this.nameOfAlgorithm = algorithm.getNameOfEncryption();
        this.fileToEncrypt = new FileOperations(pathToFile);
        this.encryptedFile = new FileOperations(fileToEncrypt.getParentPathToFile(), "Encrypted"+ nameOfAlgorithm);
        this.decryptedFile = new FileOperations(fileToEncrypt.getParentPathToFile(), "Decrypted"+ nameOfAlgorithm);
        this.keyFile = new FileOperations(fileToEncrypt.getParentPathToFile(), "Key" + nameOfAlgorithm);
        if (nameOfAlgorithm == "DoubleEncryption"){
            this.keyFileForDouble = new FileOperations(fileToEncrypt.getParentPathToFile(), "SecondKey" + nameOfAlgorithm);
        }
    }

    public FileEncryptor(EncryptionAlgorithm algorithm, String pathToFile, Boolean isInDirectory) throws IOException {
        this(algorithm, pathToFile);
        this.isInDirectory = true;

    }

    public void encryptFile() throws IOException, NullPointerException, InvalidKeyException {
        setEvent(fileToEncrypt.getPathToFile(), encryptedFile.getPathToFile(), Events.EncryptionStarted);
        String textToEncrypt = fileToEncrypt.readFromFile();
        Key key = new Key();
        try {
            int k = (int)key.getKey();
        } catch (ClassCastException e){
            throw new InvalidKeyException("Invalid key, not an int", e);
        }
        String encryptedData = algorithm.encryptFile(textToEncrypt, key.getKey());
        encryptedFile.writeToFile(encryptedData);
        keyFile.writeToFile(Integer.toString(key.getKey()));
        setEvent(fileToEncrypt.getPathToFile(), encryptedFile.getPathToFile(), Events.EncryptionEnded);
    }

    public void decryptFile() throws IOException, NullPointerException {
        setEvent(encryptedFile.getPathToFile(), decryptedFile.getPathToFile(), Events.DecryptionStarted);
        int key = Integer.parseInt(keyFile.readFromFile().strip());
        String dataToDecrypt = encryptedFile.readFromFile();
        String decryptedData = algorithm.decryptFile(dataToDecrypt, key);
        decryptedFile.writeToFile(decryptedData);
        setEvent(encryptedFile.getPathToFile(), decryptedFile.getPathToFile(), Events.DecryptionEnded);
    }

    public String getPathToDecryptedFile(){
        return this.decryptedFile.getPathToFile();
    }

    public static class Compare implements Comparator<FileEncryptor> {
        @Override
        public int compare(FileEncryptor o1, FileEncryptor o2) {
            try {
                return o1.algorithm.getKeyStrength(Integer.parseInt(o1.keyFile.readFromFile().strip())) -
                        o2.algorithm.getKeyStrength(Integer.parseInt(o2.keyFile.readFromFile().strip()));
            } catch (IOException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    public void setEvent(String originalPath, String outputFile, Events event){
        long time = System.currentTimeMillis();
        EncryptionLogEventsArgs eventsArgs = new EncryptionLogEventsArgs(algorithm, originalPath, outputFile, time, event);
        setChanged();
        notifyObservers(eventsArgs);
    }


}
