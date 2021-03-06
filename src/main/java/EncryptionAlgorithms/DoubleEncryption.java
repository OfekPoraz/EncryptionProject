package EncryptionAlgorithms;

import Keys.Key;

import java.io.IOException;

public class DoubleEncryption extends EncryptionAlgorithm{

    private EncryptionAlgorithm algorithm1;
    private EncryptionAlgorithm algorithm2;
    private int secondKey;

    public DoubleEncryption(EncryptionAlgorithm firstAlgorithm, EncryptionAlgorithm secondAlgorithm) {
        super("DoubleEncryption");
        this.algorithm1 = firstAlgorithm;
        this.algorithm2 = secondAlgorithm;
        this.secondKey = (new Key("DoubleEncryption")).getKey();
    }

    @Override
    public String encryptFile(String stringToEncrypt, int encryptionKey) throws IOException {
        String middleEncryption = algorithm1.encryptFile(stringToEncrypt, encryptionKey);
        return algorithm2.encryptFile(middleEncryption, secondKey);
    }

    @Override
    public String decryptFile(String stringToDecrypt, int decryptionKey) throws IOException {
        String middleDecryption = algorithm2.decryptFile(stringToDecrypt, secondKey);
        return algorithm1.decryptFile(middleDecryption, decryptionKey);
    }

    @Override
    public String getName() {
        return getNameOfEncryption();
    }
}
