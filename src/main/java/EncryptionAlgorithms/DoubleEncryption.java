package EncryptionAlgorithms;

import Keys.Key;

import java.io.IOException;
import java.util.NoSuchElementException;

public class DoubleEncryption extends EncryptionAlgorithm{

    private EncryptionAlgorithm algorithm1;
    private EncryptionAlgorithm algorithm2;
    private int secondKey;

    public DoubleEncryption(EncryptionAlgorithm firstAlgorithm, EncryptionAlgorithm secondAlgorithm) {
        super("DoubleEncryption");
        this.algorithm1 = firstAlgorithm;
        this.algorithm2 = secondAlgorithm;
        this.secondKey = (new Key()).getKey();
    }

    @Override
    public String encryptString(String stringToEncrypt, int encryptionKey) throws IOException, NoSuchElementException, ClassCastException {
        String middleEncryption = algorithm1.encryptString(stringToEncrypt, encryptionKey);
        return algorithm2.encryptString(middleEncryption, secondKey);
    }

    @Override
    public String decryptString(String stringToDecrypt, int decryptionKey) throws IOException, NoSuchElementException, ClassCastException {
        String middleDecryption = algorithm2.decryptString(stringToDecrypt, secondKey);
        return algorithm1.decryptString(middleDecryption, decryptionKey);
    }

    @Override
    public String getName() {
        return getNameOfEncryption();
    }
}
