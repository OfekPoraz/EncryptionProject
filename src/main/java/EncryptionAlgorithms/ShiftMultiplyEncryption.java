package EncryptionAlgorithms;

import java.io.IOException;

public class ShiftMultiplyEncryption extends EncryptionAlgorithm{
    private static final int MODULO = 26;
    public ShiftMultiplyEncryption() {
        super("ShiftMultiply");
    }

    @Override
    public String encryptFile(String stringToEncrypt, int encryptionKey) throws IOException {
        StringBuffer encryptedData = new StringBuffer();
        for (int i = 0 ; i < stringToEncrypt.length() ; i ++){
            if (checkIfAlphabet(stringToEncrypt.charAt(i), false)){
                int dataToEncrypt = stringToEncrypt.charAt(i) - 'a';
                dataToEncrypt = (dataToEncrypt * encryptionKey) % MODULO;
                encryptedData.append((char) (dataToEncrypt + 'a'));
            } else if (checkIfAlphabet(stringToEncrypt.charAt(i), true)){
                int dataToEncrypt = stringToEncrypt.charAt(i) - 'A';
                dataToEncrypt = (dataToEncrypt * encryptionKey) % MODULO;
                encryptedData.append((char) (dataToEncrypt + 'A'));
            } else {
                encryptedData.append(stringToEncrypt.charAt(i));

            }
        }
        return encryptedData.toString();

    }

    @Override
    public String decryptFile(String stringToDecrypt, int decryptionKey) throws IOException {
        int decryptionKeyModuloInverse = getReverseModulo(decryptionKey);
        return encryptFile(stringToDecrypt , decryptionKeyModuloInverse);
    }

    @Override
    public String getName() {
        return getNameOfEncryption();
    }

    private int getReverseModulo(int key){
        int y = 0, x = 1 , innerModulo = MODULO;

            while (key > 1) {
                // q is quotient
                int q = key / innerModulo;
                int t = innerModulo;
                // m is remainder now, process
                // same as Euclid's algo
                innerModulo = key % innerModulo;
                key = t;
                t = y;

                // Update x and y
                y = x - q * y;
                x = t;
            }

            // Make x positive
            if (x < 0)
                x += MODULO;

            return x;
    }
}
