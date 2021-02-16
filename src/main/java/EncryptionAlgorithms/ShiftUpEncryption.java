package EncryptionAlgorithms;

import java.io.IOException;

public class ShiftUpEncryption extends EncryptionAlgorithm{

    public ShiftUpEncryption() {
        super("ShiftUp");
    }

    @Override
    public String encryptFile(String stringToEncrypt, int encryptionKey) throws IOException {
        StringBuffer encryptedData = new StringBuffer();
        for (int i = 0 ; i < stringToEncrypt.length() ; i ++){
            if (checkIfAlphabet(stringToEncrypt.charAt(i), false)){
                int dataToEncrypt = stringToEncrypt.charAt(i) - 'a';
                dataToEncrypt = (dataToEncrypt + encryptionKey) % 26;
                encryptedData.append((char) (dataToEncrypt + 'a'));
            } else if (checkIfAlphabet(stringToEncrypt.charAt(i), true)){
                int dataToEncrypt = stringToEncrypt.charAt(i) - 'A';
                dataToEncrypt = (dataToEncrypt + encryptionKey) % 26;
                encryptedData.append((char) (dataToEncrypt + 'A'));
            } else {
                encryptedData.append(stringToEncrypt.charAt(i));

            }
        }
        return encryptedData.toString();

    }

    @Override
    public String decryptFile(String stringToEncrypt, int decryptionKey) throws IOException {
        StringBuffer encryptedData = new StringBuffer();
        for (int i = 0 ; i < stringToEncrypt.length() ; i ++){
            if (checkIfAlphabet(stringToEncrypt.charAt(i), false)){
                int dataToEncrypt = stringToEncrypt.charAt(i) - 'a' + 26;
                dataToEncrypt = (dataToEncrypt - decryptionKey) % 26;
                encryptedData.append((char) (dataToEncrypt + 'a'));
            } else if (checkIfAlphabet(stringToEncrypt.charAt(i), true)){
                int dataToEncrypt = stringToEncrypt.charAt(i) - 'A' + 26;
                dataToEncrypt = (dataToEncrypt - decryptionKey) % 26;
                encryptedData.append((char) (dataToEncrypt + 'A'));
            } else {
                encryptedData.append(stringToEncrypt.charAt(i));

            }
        }
        return encryptedData.toString();
    }

    @Override
    public String getName() {
        return getNameOfEncryption();
    }
}
