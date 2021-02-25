package EncryptionAlgorithms;

import EventsLogger.EncryptionLog4JLogger;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.util.NoSuchElementException;

public class ShiftUpEncryption extends EncryptionAlgorithm{
    private final EncryptionLog4JLogger log4JLogger = new EncryptionLog4JLogger();

    public ShiftUpEncryption() {
        super("ShiftUp");
    }

    @Override
    public String encryptString(String stringToEncrypt, int encryptionKey) throws IOException, NoSuchElementException, ClassCastException {
        StringBuffer encryptedData = new StringBuffer();
        log4JLogger.writeToLogger("Starting to encrypt string", Level.DEBUG);
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
        log4JLogger.writeToLogger("finished to encrypt string", Level.DEBUG);
        return encryptedData.toString();

    }

    @Override
    public String decryptString(String stringToEncrypt, int decryptionKey) throws IOException, NoSuchElementException, ClassCastException {
        log4JLogger.writeToLogger("Starting to decrypt string", Level.DEBUG);
        int decryptionKeyInner = 26 - decryptionKey;
        return encryptString(stringToEncrypt, decryptionKeyInner);

    }

    @Override
    public String getName() {
        return getNameOfEncryption();
    }
}
