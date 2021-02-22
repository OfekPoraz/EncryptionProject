package FileEncryptor;

import EncryptionAlgorithms.EncryptionAlgorithm;
import Keys.Key;
import Utils.FileOperations;

import java.io.IOException;
import java.util.Comparator;

public class FileEncryptor {

    private String nameOfAlgorithm;
    private EncryptionAlgorithm algorithm;
    private FileOperations fileToEncrypt;
    private FileOperations encryptedFile;
    private FileOperations decryptedFile;
    private FileOperations keyFile;
    private FileOperations keyFileForDouble = null;

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

    public void encryptFile() throws IOException, NullPointerException {
        String textToEncrypt = fileToEncrypt.readFromFile();
        Key key = new Key();
        String encryptedData = algorithm.encryptFile(textToEncrypt, key.getKey());
        encryptedFile.writeToFile(encryptedData);
        keyFile.writeToFile(Integer.toString(key.getKey()));
    }

    public void decryptFile() throws IOException, NullPointerException {
        int key = Integer.parseInt(keyFile.readFromFile().strip());
        String dataToDecrypt = encryptedFile.readFromFile();
        String decryptedData = algorithm.decryptFile(dataToDecrypt, key);
        decryptedFile.writeToFile(decryptedData);
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
}
