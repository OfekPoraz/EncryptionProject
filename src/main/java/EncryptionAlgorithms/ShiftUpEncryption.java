package EncryptionAlgorithms;

import EventsLogger.Events;

import java.io.IOException;
import java.util.NoSuchElementException;

public class ShiftUpEncryption extends EncryptionAlgorithm{

    public ShiftUpEncryption() {
        super("ShiftUp");
    }

    @Override
    public String encryptString(String stringToEncrypt, int encryptionKey) throws NoSuchElementException, ClassCastException {
        StringBuffer encryptedData = new StringBuffer();
        setEvent("Starting to encrypt string", Events.Debug);
        for (int i = 0 ; i < stringToEncrypt.length() ; i ++){
            if (Character.isAlphabetic(stringToEncrypt.charAt(i))) {
                char upperLower = 0;
                if (Character.isUpperCase(stringToEncrypt.charAt(i))) {
                    upperLower = 'A';
                } else {
                    upperLower = 'a';
                }
                int dataToEncrypt = stringToEncrypt.charAt(i) - upperLower;
                dataToEncrypt = (dataToEncrypt + encryptionKey) % 26;
                encryptedData.append((char) (dataToEncrypt + upperLower));
            } else {
                encryptedData.append(stringToEncrypt.charAt(i));
            }
        }
        setEvent("finished to encrypt string", Events.Debug);
        return encryptedData.toString();

    }

    @Override
    public String decryptString(String stringToEncrypt, int decryptionKey) throws IOException, NoSuchElementException, ClassCastException {
        setEvent("Starting to decrypt string", Events.Debug);
        int decryptionKeyInner = 26 - decryptionKey;
        return encryptString(stringToEncrypt, decryptionKeyInner);

    }

    @Override
    public String getName() {
        return getNameOfEncryption();
    }
}
