package FileEncryptor;

import EncryptionAlgorithms.EncryptionAlgorithm;
import Keys.Key;
import Utils.FileOperations;

import java.io.File;
import java.io.IOException;

public class FileEncryptor {

    private String nameOfAlgorithm;
    private EncryptionAlgorithm algorithm;
    private FileOperations fileToEncrypt;
    private FileOperations encryptedFile;
    private FileOperations decryptedFile;
    private FileOperations keyFile;
    private FileOperations keyFileForDouble = null;

    public FileEncryptor(EncryptionAlgorithm algorithm, String pathToFile) throws IOException {
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

    public void encryptFile() throws IOException {
        String textToEncrypt = fileToEncrypt.readFromFile();
        Key key = new Key(nameOfAlgorithm);
        String encryptedData = algorithm.encryptFile(textToEncrypt, key.getKey());
        encryptedFile.writeToFile(encryptedData);
        keyFile.writeToFile(Integer.toString(key.getKey()));
        if (nameOfAlgorithm == "DoubleEncryption"){
        }
    }

    public void decryptFile() throws IOException {
        int key = Integer.parseInt(keyFile.readFromFile().strip());
        String dataToDecrypt = encryptedFile.readFromFile();
        String decryptedData = algorithm.decryptFile(dataToDecrypt, key);
        decryptedFile.writeToFile(decryptedData);
    }
}
