package encryptionalgorithms;

import java.io.IOException;
import java.util.NoSuchElementException;

public class ShiftUpEncryption extends EncryptionAlgorithm{

    public ShiftUpEncryption() {
        super("ShiftUp");
    }

    @Override
    public String encryptString(String stringToEncrypt, int encryptionKey) throws NoSuchElementException, ClassCastException {
        StringBuffer encryptedData = new StringBuffer();
        for (int i = 0 ; i < stringToEncrypt.length() ; i ++){
            if (Character.isAlphabetic(stringToEncrypt.charAt(i))) {
                char upperLower = Character.isUpperCase(stringToEncrypt.charAt(i)) ? 'A' : 'a';
                int dataToEncrypt = stringToEncrypt.charAt(i) - upperLower;
                dataToEncrypt = (dataToEncrypt + encryptionKey) % 26;
                encryptedData.append((char) (dataToEncrypt + upperLower));
            } else {
                encryptedData.append(stringToEncrypt.charAt(i));
            }
        }
        return encryptedData.toString();

    }

    @Override
    public String decryptString(String stringToEncrypt, int decryptionKey) throws IOException, NoSuchElementException, ClassCastException {
        int decryptionKeyInner = 26 - decryptionKey;
        return encryptString(stringToEncrypt, decryptionKeyInner);

    }

    @Override
    public String getName() {
        return getNameOfEncryption();
    }
}
