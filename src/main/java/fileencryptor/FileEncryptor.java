package fileencryptor;

import encryptionalgorithms.EncryptionAlgorithm;
import eventslogger.EncryptionLogEventsArgs;
import eventslogger.EncryptionLogger;
import eventslogger.Events;
import keys.Key;
import utils.FileOperations;
import java.io.File;
import java.io.IOException;
import java.util.Observable;

public class FileEncryptor extends Observable implements Comparable<FileEncryptor> {

    private String nameOfAlgorithm;
    private EncryptionAlgorithm algorithm;
    private FileOperations fileToEncrypt;
    private FileOperations encryptedFile;
    private FileOperations decryptedFile;
    private FileOperations keyFile;

    public FileEncryptor(EncryptionAlgorithm algorithm, String pathToFile) throws IOException, NullPointerException {
        this.algorithm = algorithm;
        this.nameOfAlgorithm = algorithm.getNameOfEncryption();
        this.fileToEncrypt = new FileOperations(pathToFile);
        this.encryptedFile = new FileOperations(fileToEncrypt.getParentPathToFile(), "Encrypted"+ nameOfAlgorithm, false);
        this.decryptedFile = new FileOperations(fileToEncrypt.getParentPathToFile(), "Decrypted"+ nameOfAlgorithm, false);
        this.keyFile = new FileOperations(fileToEncrypt.getParentPathToFile(), "Key" + nameOfAlgorithm, false);
    }

    public FileEncryptor(EncryptionAlgorithm algorithm,
                         FileOperations encryptedDir,
                         FileOperations decryptedDir,
                         FileOperations keyFile,
                         String fileName) throws IOException {
        this.algorithm = algorithm;
        this.nameOfAlgorithm = algorithm.getNameOfEncryption();
        this.fileToEncrypt = new FileOperations(encryptedDir.getParentPathToFile() + File.separator + fileName);
        this.encryptedFile = new FileOperations(encryptedDir.getPathToFile(), getClearName(fileName), false);
        this.decryptedFile = new FileOperations(decryptedDir.getPathToFile(), getClearName(fileName), false);
        this.keyFile = keyFile;

    }


    private int generateKey(FileOperations keyFile, boolean isDir) throws IOException {
        int k;
        if (!isDir){
            Key key = new Key();
            k = key.getRandomKey();
            keyFile.writeToFile(Integer.toString(key.getRandomKey()));
        } else {
            k = Integer.parseInt(keyFile.readFromFile().strip());
        }
        return k;
    }


    public void encryptFile(Boolean isDir) throws IOException{
        String textToEncrypt = fileToEncrypt.readFromFile();
        setEvent(fileToEncrypt.getPathToFile(), encryptedFile.getPathToFile(), Events.ENCRYPTION_STARTED);
        String encryptedData = algorithm.encryptString(textToEncrypt, generateKey(keyFile, isDir));
        setEvent(fileToEncrypt.getPathToFile(), encryptedFile.getPathToFile(), Events.ENCRYPTION_ENDED);
        encryptedFile.writeToFile(encryptedData);
        setEvent("Saving encryption results to file and key to key file for algorithm: " + nameOfAlgorithm, Events.DEBUG);
    }

    public void decryptFile() throws IOException, NullPointerException {
        int key = Integer.parseInt(keyFile.readFromFile().strip());
        String dataToDecrypt = encryptedFile.readFromFile();
        setEvent(encryptedFile.getPathToFile(), decryptedFile.getPathToFile(), Events.DECRYPTION_STARTED);
        String decryptedData = algorithm.decryptString(dataToDecrypt, key);
        setEvent(encryptedFile.getPathToFile(), decryptedFile.getPathToFile(), Events.DECRYPTION_ENDED);
        decryptedFile.writeToFile(decryptedData);
    }

    public String getPathToDecryptedFile() {
        return this.decryptedFile.getPathToFile();
    }

    public String getClearName(String name) {
        return name.replaceFirst("[.][^.]+$", "");
    }

    public String getNameOfAlgorithm() {
        return nameOfAlgorithm;
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
        EncryptionLogger logger = new EncryptionLogger();
        try {
            return this.algorithm.getKeyStrength(Integer.parseInt(this.keyFile.readFromFile().strip())) -
                    o.algorithm.getKeyStrength(Integer.parseInt(o.keyFile.readFromFile().strip()));
        } catch (IOException e) {
            logger.writeErrorLog(e.getMessage());
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FileEncryptor fileEncryptor = (FileEncryptor) obj;
        return getNameOfAlgorithm().equals(fileEncryptor.nameOfAlgorithm);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
