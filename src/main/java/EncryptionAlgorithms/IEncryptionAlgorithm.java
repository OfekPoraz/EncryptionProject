package EncryptionAlgorithms;

import java.io.IOException;

public interface IEncryptionAlgorithm {
     String encryptFile(String stringToEncrypt, int encryptionKey) throws IOException;
     String decryptFile(String stringToEncrypt, int decryptionKey) throws IOException;
     String getName();
     int getKeyStrength(int encryptionKey) throws IOException;
}
