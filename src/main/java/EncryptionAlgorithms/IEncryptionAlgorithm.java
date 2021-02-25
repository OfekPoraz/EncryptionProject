package EncryptionAlgorithms;

import java.io.IOException;

public interface IEncryptionAlgorithm {
     String encryptString(String stringToEncrypt, int encryptionKey) throws IOException;
     String decryptString(String stringToEncrypt, int decryptionKey) throws IOException;
     String getName();
     int getKeyStrength(int encryptionKey) throws IOException;
}
