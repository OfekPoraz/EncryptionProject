package EncryptionAlgorithms;

import java.io.IOException;

public class XorEncryption extends EncryptionAlgorithm{

    public XorEncryption(){
        super("XorEncryption");
    }

    @Override
    public String encryptFile(String stringToEncrypt, int encryptionKey) throws IOException {
        StringBuffer encryptedData = new StringBuffer();
        for (int i = 0 ; i < stringToEncrypt.length() ; i ++){
            if (String.valueOf(stringToEncrypt.charAt(i)) == "\n\r"){
                encryptedData.append((char) stringToEncrypt.charAt(i));
            } else {
                encryptedData.append((char) (stringToEncrypt.charAt(i) ^ encryptionKey));
            }
        }
        return encryptedData.toString();
    }

    @Override
    public String decryptFile(String stringToDecrypt, int decryptionKey) throws IOException {
        return encryptFile(stringToDecrypt, decryptionKey);

    }

    @Override
    public String getName() {
        return getNameOfEncryption();
    }
}
