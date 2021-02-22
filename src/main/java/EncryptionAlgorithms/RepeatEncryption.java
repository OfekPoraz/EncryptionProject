package EncryptionAlgorithms;

import java.io.IOException;
import java.util.NoSuchElementException;

public class RepeatEncryption extends EncryptionAlgorithm{

    private int numberOfTimesToEncrypt;
    EncryptionAlgorithm algorithm;
    public RepeatEncryption(EncryptionAlgorithm algorithm, int numberOfTimesToEncrypt) {
        super("RepeatEncryption");
        this.numberOfTimesToEncrypt = numberOfTimesToEncrypt;
        this.algorithm = algorithm;
    }

    @Override
    public String encryptFile(String stringToEncrypt, int encryptionKey) throws IOException, NoSuchElementException, ClassCastException {
        for (int i = 0 ; i < numberOfTimesToEncrypt ; i++){
            stringToEncrypt = algorithm.encryptFile(stringToEncrypt, encryptionKey);
        }
        return stringToEncrypt;
    }

    @Override
    public String decryptFile(String stringToDecrypt, int decryptionKey) throws IOException, NoSuchElementException, ClassCastException {
        for (int i = 0 ; i < numberOfTimesToEncrypt ; i++){
            stringToDecrypt = algorithm.decryptFile(stringToDecrypt, decryptionKey);
        }
        return stringToDecrypt;
    }

    @Override
    public String getName() {
        return null;
    }
}
