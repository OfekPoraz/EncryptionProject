package encryptionalgorithms;

import java.io.IOException;
import java.util.NoSuchElementException;

public class RepeatEncryption extends EncryptionAlgorithm{

    private int numberOfTimesToEncrypt;
    private EncryptionAlgorithm algorithm;
    public RepeatEncryption(EncryptionAlgorithm algorithm, int numberOfTimesToEncrypt) {
        super("RepeatEncryption");
        this.numberOfTimesToEncrypt = numberOfTimesToEncrypt;
        this.algorithm = algorithm;
    }

    @Override
    public String encryptString(String stringToEncrypt, int encryptionKey) throws IOException, NoSuchElementException, ClassCastException {
        for (int i = 0 ; i < numberOfTimesToEncrypt ; i++){
            stringToEncrypt = algorithm.encryptString(stringToEncrypt, encryptionKey);
        }
        return stringToEncrypt;
    }

    @Override
    public String decryptString(String stringToDecrypt, int decryptionKey) throws IOException, NoSuchElementException, ClassCastException {
        for (int i = 0 ; i < numberOfTimesToEncrypt ; i++){
            stringToDecrypt = algorithm.decryptString(stringToDecrypt, decryptionKey);
        }
        return stringToDecrypt;
    }

    @Override
    public String getName() {
        return getNameOfEncryption();
    }
}
