package EncryptionAlgorithms;

import EventsLogger.Events;

import java.io.IOException;
import java.util.NoSuchElementException;

public class XorEncryption extends EncryptionAlgorithm{

    public XorEncryption(){
        super("XorEncryption");
    }

    @Override
    public String encryptString(String stringToEncrypt, int encryptionKey) throws IOException, NoSuchElementException, ClassCastException {
        setEvent("Starting to encrypt string", Events.Debug);
        StringBuffer encryptedData = new StringBuffer();
        for (int i = 0 ; i < stringToEncrypt.length() ; i ++){
            if (!String.valueOf(stringToEncrypt.charAt(i)).equals("\r\n\r")){
                encryptedData.append((char) (stringToEncrypt.charAt(i) ^ encryptionKey));
        } else {
                encryptedData.append( (stringToEncrypt.charAt(i)));
            }

        }
        setEvent("finished to encrypt string", Events.Debug);
        return encryptedData.toString();
    }

    @Override
    public String decryptString(String stringToDecrypt, int decryptionKey) throws IOException, NoSuchElementException, ClassCastException {
        setEvent("Starting to decrypt string", Events.Debug);
        return encryptString(stringToDecrypt , decryptionKey);

    }

    @Override
    public String getName() {
        return getNameOfEncryption();
    }
}
